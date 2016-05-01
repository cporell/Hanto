/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.tournament;

import java.util.Comparator;
import java.util.PriorityQueue;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.tournament.HantoMoveRecord;

/**
 * HantoPlayerAStarThinker uses A* search to find the most optimal move for a round of Hanto.
 * @author bpeake
 * @author cgporell
 *
 */
public class HantoPlayerAStarThinker implements IHantoPlayerThinker 
{
	private class PathNodeSort implements Comparator<PathNode>
	{

		@Override
		public int compare(PathNode o1, PathNode o2) 
		{
			float o1Score = o1.calculatedScore();
			float o2Score = o2.calculatedScore();
			
			if(o1.isClosedNode())
			{
				o1Score += (Float.MAX_VALUE / 2.0);
			}
			
			if(o2.isClosedNode())
			{
				o2Score += (Float.MAX_VALUE / 2.0);
			}
			
			return Float.compare(o1Score, o2Score);
		}
		
	}
	
	private class PathNode
	{
		private IHantoGameState state;
		private HantoMoveRecord moveToThisState;
		private MoveResult result;
		private PathNode parent;
		private HantoPlayerColor ourColor;
		
		/**
		 * Builds a Path Node.
		 * @param state Current game state
		 * @param move Current move in the game
		 * @param result Move result of that move
		 * @param ourColor Player color
		 * @param parent Parent node in the A* tree
		 */
		PathNode(IHantoGameState state, HantoMoveRecord move, MoveResult result, HantoPlayerColor ourColor, PathNode parent)
		{
			this.state = state;
			moveToThisState = move;
			this.result = result;
			this.ourColor = ourColor;
			this.parent = parent;
		}
		
		private float calculatedScore()
		{
			float weighedScore = (float) (0.9 * rawScore());
			return weighedScore + ((parent != null) ? parent.rawScore() : 0);
		}
		
		private boolean isClosedNode()
		{
			return result != MoveResult.OK;
		}
		
		private float rawScore()
		{
			switch(result)
			{
			case BLUE_WINS:
				return ourColor == HantoPlayerColor.BLUE ? 0 : 1000;
			case RED_WINS:
				return ourColor == HantoPlayerColor.RED ? 0 : 1000;
			case DRAW:
				return 100;
			case OK:
				return 10;
			default:
				return 0;
			}
		}
		
		private boolean isRoot()
		{
			return parent == null;
		}
	}
	
	private class AStarThread extends Thread
	{
		private CommonHantoGame game;
		private PriorityQueue<PathNode> edge = new PriorityQueue<>(new PathNodeSort());
		
		/**
		 * Builds an AStarThread
		 * @param game The game we are using
		 */
		AStarThread(CommonHantoGame game)
		{
			this.game = game;
		}
		
		@Override
		public void run() 
		{			
			IHantoGameState originalState = game.getState();
			
			IHantoGameState rootState = game.getState().copy();
			IHantoRuleSet rules = game.getRules();
			HantoPlayerColor ourColor = rules.getCurrentPlayer(rootState);
			
			PathNode rootNode = new PathNode(rootState, null, MoveResult.OK, ourColor, null);
			edge.add(rootNode);
			
			int i = 0;
			
			while(!Thread.interrupted() && edge.size() > 0 && !edge.peek().isClosedNode())
			{
				PathNode node = edge.poll();
				IHantoGameState state = node.state;
				
				HantoMoveRecord[] moves = rules.getAllValidMoves(rules.getCurrentPlayer(state), state);
				
				for(HantoMoveRecord move : moves)
				{
					IHantoGameState cpy = state.copy();					
					game.setState(cpy);
					
					try
					{
						MoveResult result = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
						PathNode newNode = new PathNode(cpy, move, result, ourColor, node);
						edge.add(newNode);
					} 
					catch (HantoException e)
					{
						i++;
					}
				}
			}
			
			game.setState(originalState);
		}
	}
	
	@Override
	public HantoMoveRecord selectMove(CommonHantoGame game) 
	{		
		AStarThread thread = new AStarThread(game);
		thread.start();
		
		int i = 0;
		
		try 
		{
			thread.join(700);
			
			thread.interrupt();
			thread.join();
		} 
		catch (InterruptedException e) 
		{
			i++;
		}
		
		PriorityQueue<PathNode> edge = thread.edge;
		
		if(edge.size() == 0)
		{
			return new HantoMoveRecord(null, null, null);
		}
		
		PathNode front = edge.poll();
		PathNode bestClosed = null;
		
		if(front.isClosedNode())
		{
			bestClosed = front;
		}
		else
		{
			while(edge.size() > 0 && !(bestClosed = edge.poll()).isClosedNode())
			{
				i++;
			}
			
			if(bestClosed != null && !bestClosed.isClosedNode())
			{
				bestClosed = null;
			}
		}
		
		PathNode selection;
		if(bestClosed != null && bestClosed.calculatedScore() < front.calculatedScore())
		{
			selection = bestClosed;
		}
		else
		{
			selection = front;
		}
		
		while(!selection.parent.isRoot())
		{
			selection = selection.parent;
		}
		
		return selection.moveToThisState;
	}

}
