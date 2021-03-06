/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.tournament.HantoMoveRecord;

/**
 * Superclass for rule collections. Rulesets for different versions of Hanto can
 * extend this for common functionality.
 * @author cgporell
 * @author bpeake
 *
 */
public abstract class GenericHantoRuleCollection implements IHantoRuleSet 
{
	private HantoPlayerColor startingPlayerColor;
	
	/**
	 * This represents a generic rule inside a collection of rules
	 * @author Benny Peake
	 * @author Connor Porell
	 *
	 */
	public interface IRule
	{
		/**
		 * Checks to see if the game state is a valid game state.
		 * @param state The game state.
		 * @throws HantoException When an invalid game state is found.
		 */
		void check(IHantoGameState state) throws HantoException;
		
		/**
		 * Checks if a location is a valid location for pieces to operate on.
		 * @param state The state of the game.
		 * @param location The location to operate on.
		 * @return True if this is a valid operation location.
		 */
		boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location);
		
		/**
		 * Checks to see if this space can be searched for more operation locations.
		 * @param state The state of the game.
		 * @param location The location to search from.
		 * @return True if this location should be used to search.
		 */
		boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location);
	}
	
	/**
	 * Start Condition interface.
	 * Rules that are checked at the start of a turn implement this.
	 * @author cgporell
	 * @author bpeake
	 *
	 */
	public interface IStartCondition
	{
		/**
		 * Checks the state of the board at the start of a turn
		 * @param state The current board state
		 * @throws HantoException If any illegal state is found
		 */
		void check(IHantoGameState state) throws HantoException;
	}
	
	/**
	 * End Condition interface.
	 * Rules that are checked at the end of a turn implement this.
	 * @author cgporell
	 * @author bpeake
	 *
	 */
	public interface IEndCondition
	{
		/**
		 * Checks the state of the board at the end of a turn.
		 * Specifically looks for end conditions.
		 * @param state The current state of the board
		 * @return The result of the move that was last made.
		 */
		MoveResult checkForResult(IHantoGameState state);
	}
	
	private List<IRule> rules = new ArrayList<>();
	private List<IStartCondition> startConditions = new ArrayList<>();
	private List<IEndCondition> endConditions = new ArrayList<>();
	
	/**
	 * Builds a Generic Hanto Rule Collection.
	 * Superclass (generic) version of this constructor is only concerned with 
	 * the starting player. Subclasses handle adding individual rules.
	 * @param startingPlayer The starting player
	 */
	public GenericHantoRuleCollection(HantoPlayerColor startingPlayer)
	{
		startingPlayerColor = startingPlayer;
	}
	
	/**
	 * Add a rule to this rule collection
	 * @param rule The rule we are adding
	 */
	public void addRule(IRule rule)
	{
		rules.add(rule);
	}
	
	/**
	 * Add a start condition to this ruleset
	 * @param condition The start condition we are adding
	 */
	public void addStartCondition(IStartCondition condition)
	{
		startConditions.add(condition);
	}
	
	/**
	 * Add an end condition to this ruleset
	 * @param condition The end condition we are adding
	 */
	public void addEndCondition(IEndCondition condition)
	{
		endConditions.add(condition);
	}
	
	@Override
	public void beginTurn(IHantoGameState state) throws HantoException 
	{
		for(IStartCondition condition : startConditions)
		{
			condition.check(state);
		}
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		for(IRule rule : rules)
		{
			rule.check(state);
		}
	}

	@Override
	public HantoPlayerColor getCurrentPlayer(IHantoGameState state) 
	{
		HantoPlayerColor oppositeStarting = startingPlayerColor == HantoPlayerColor.RED ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
		return state.getMoveNumber() % 2 == 0 ? startingPlayerColor : oppositeStarting;
	}

	@Override
	public MoveResult endTurn(IHantoGameState state) throws HantoException 
	{
		MoveResult result = MoveResult.OK;
		for(IEndCondition condition : endConditions)
		{
			result = condition.checkForResult(state);
			if(result != MoveResult.OK)
			{
				state.triggerGameOver();
				break;
			}
		}
		
		return result;
	}

	@Override
	public int getTurnNumber(IHantoGameState state) 
	{
		return (state.getMoveNumber() / 2) + 1;
	}
	
	@Override
	public HantoMoveRecord[] getAllValidMoves(HantoPlayerColor player, IHantoGameState state) 
	{
		Set<HantoCoordinate> allValidSpaces = new HashSet<>();
		
		CommonHantoPiece[] allPiecesOnBoard = state.getPieces();
		CommonHantoPiece[] piecesOnBoard = state.getPieces(player);
		CommonHantoPiece[] piecesInHand = state.getPiecesInHand(player);
		HantoCoordinate startLocation;
		if(allPiecesOnBoard.length > 0)
		{
			startLocation = state.getPieceLocation(allPiecesOnBoard[0]);
		}
		else
		{
			startLocation = new HantoCoordinateImpl(0, 0);
		}
		
		Set<HantoCoordinate> blackList = new HashSet<>();
		List<HantoCoordinate> edge = new ArrayList<>();
		edge.add(startLocation);
		
		while(edge.size() > 0)
		{
			HantoCoordinate next = edge.remove(0);
			if(blackList.contains(next))
			{
				continue;
			}
			
			boolean isBlackList = false;
			boolean isKeep = true;
			for(IRule rule : rules)
			{
				if(!rule.isValidSearchLocation(state, next))
				{
					isBlackList = true;
					isKeep = false;
					break;
				}
				
				if(!rule.isValidMoveLocation(state, next))
				{
					isKeep = false;
				}
			}
			
			if(isBlackList)
			{
				blackList.add(next);
				continue;
			}
			
			if(isKeep)
			{
				allValidSpaces.add(next);
			}
			
			HantoCoordinate[] adj = state.getAdjacent(next);
			edge.addAll(Arrays.asList(adj));
			
			blackList.add(next);
		}
		
		HantoCoordinate[] spaces = new HantoCoordinate[allValidSpaces.size()];
		allValidSpaces.toArray(spaces);
		
		ArrayList<HantoMoveRecord> results = new ArrayList<>();
		
		for(CommonHantoPiece piece : piecesInHand)
		{
			findPlacementMoves(piece, spaces, state, results);
		}
		
		for(CommonHantoPiece piece : piecesOnBoard)
		{
			findWalkMoves(piece, spaces, state, results);
		}
		
		HantoMoveRecord[] arr = new HantoMoveRecord[results.size()];
		results.toArray(arr);
		
		return arr;
	}
	
	private void findPlacementMoves(CommonHantoPiece piece, HantoCoordinate[] spaces, IHantoGameState state, List<HantoMoveRecord> foundMoves)
	{
		for(HantoCoordinate coord : spaces)
		{
			IHantoMover mover = piece.createPlaceMover(coord);
			IHantoMoverValidator validator = createMoverValidator(mover);
			
			if(simulateMover(state, mover, validator))
			{
				foundMoves.add(new HantoMoveRecord(piece.getType(), null, coord));
			}
		}
	}
	private void findWalkMoves(CommonHantoPiece piece, HantoCoordinate[] spaces, IHantoGameState state, List<HantoMoveRecord> foundMoves)
	{
		for(HantoCoordinate coord : spaces)
		{
			HantoCoordinate from = state.getPieceLocation(piece);
			IHantoMover mover = piece.createWalkMover(from, coord);
			IHantoMoverValidator validator = createMoverValidator(mover);
			
			if(simulateMover(state, mover, validator))
			{
				foundMoves.add(new HantoMoveRecord(piece.getType(), from, coord));
			}
		}
	}
	private boolean simulateMover(IHantoGameState state, IHantoMover mover, IHantoMoverValidator validator) 
	{
		boolean goodResult;
		
		try
		{
		
			boolean shouldContinue;
			do
			{
				shouldContinue = false;
				
				try
				{
					validator.preIteration(state);
					shouldContinue = mover.iterateMove(state);
					validator.checkIteration(state);
					check(state);
				}
				catch(HantoException e)
				{
					if(mover.handleInvalidIteration(state))
					{
						validator.onInvalidMoveHandled(state);
						shouldContinue = true;
					}
					else
					{
						throw e;
					}
				}
			}
			while(shouldContinue);
		
			goodResult = true;
		}
		catch(HantoException e)
		{
			goodResult = false;
		}
		
		mover.reset(state);
		
		return goodResult;
	}
}
