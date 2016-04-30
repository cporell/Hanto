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

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.HantoGameFactory;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.tournament.HantoMoveRecord;

/**
 * Tournament Runner handles playing a match between two Hanto bots
 * @author cgporell
 * @author bpeake
 *
 */
public class TournamentRunner
{
	private HantoPlayer blue, red;
	private HantoGame game;
	
	private HantoPlayerColor playerTurn;
	private HantoMoveRecord lastMove;
	private MoveResult lastResult = MoveResult.OK;
	
	/**
	 * Builds a new tournament runner using a fresh copy of a Hanto game.
	 * @param gameId The version of Hanto we are running
	 * @param startingColor The starting player's color
	 */
	public TournamentRunner(HantoGameID gameId, HantoPlayerColor startingColor)
	{
		playerTurn = startingColor;
		
		blue = new HantoPlayer();
		red = new HantoPlayer();
		
		game = HantoGameFactory.getInstance().makeHantoGame(gameId);
		
		blue.startGame(gameId, HantoPlayerColor.BLUE, startingColor == HantoPlayerColor.BLUE);
		red.startGame(gameId, HantoPlayerColor.RED, startingColor == HantoPlayerColor.RED);
	}
	
	/**
	 * Builds a tournament runner taking in an existing game state
	 * @param game The current game state
	 * @param gameId The version of Hanto
	 * @param startingColor The starting player's color
	 */
	public TournamentRunner(HantoGame game, HantoGameID gameId, HantoPlayerColor startingColor)
	{
		playerTurn = startingColor;
		
		blue = new HantoPlayer();
		red = new HantoPlayer();
		
		this.game = game;
		
		blue.startGame(gameId, HantoPlayerColor.BLUE, startingColor == HantoPlayerColor.BLUE);
		blue.getGame().setState(((CommonHantoGame) game).getState().copy());
		red.startGame(gameId, HantoPlayerColor.RED, startingColor == HantoPlayerColor.RED);
		red.getGame().setState(((CommonHantoGame) game).getState().copy());
	}
	
	/**
	 * Plays one step in a game between two bots.
	 * @return The MoveResult of a turn.
	 */
	public MoveResult step()
	{
		if(lastResult != MoveResult.OK)
		{
			return lastResult;
		}
		
		HantoMoveRecord move;
		if(playerTurn == HantoPlayerColor.BLUE)
		{
			move = blue.makeMove(lastMove);
		}
		else
		{
			move = red.makeMove(lastMove);
		}
		
		lastMove = move;
		
		MoveResult result;
		try 
		{
			result = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} 
		catch (HantoException e) 
		{
			result = playerTurn == HantoPlayerColor.BLUE ? MoveResult.RED_WINS : MoveResult.BLUE_WINS;
		}
		
		lastResult = result;
		
		playerTurn = playerTurn == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		return result;
	}
	
	/**
	 * Gets the result of the last move
	 * @return The most recent move result
	 */
	public MoveResult getLastResult()
	{
		return lastResult;
	}
	
	/**
	 * Get the current game staet
	 * @return The current game state
	 */
	public HantoGame getGame()
	{
		return game;
	}
	
	/**
	 * Sets the game state
	 * @param game The game state
	 */
	public void setGame(HantoGame game)
	{
		this.game = game;
	}
}
