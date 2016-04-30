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

import hanto.common.*;
import hanto.studentCPBP.HantoGameFactory;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.tournament.*;

/**
 * Description
 * @version Oct 13, 2014
 */
public class HantoPlayer implements HantoGamePlayer
{
	private CommonHantoGame ourGame;
	private IHantoPlayerThinker thinker;
	
	/**
	 * HantoPlayer constructor takes in a game state.
	 * Allows us to initialize a bot part of the way through a game.
	 * @param game The game state
	 */
	public HantoPlayer(CommonHantoGame game)
	{
		ourGame = game;
	}
	
	/**
	 * Empty constructor for the HantoPlayer.
	 */
	public HantoPlayer(){}
	
	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
		HantoPlayerColor otherColor = myColor == HantoPlayerColor.RED ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
		HantoGame game = HantoGameFactory.getInstance().makeHantoGame(version, doIMoveFirst ? myColor : otherColor);
		
		if(game != null && game instanceof CommonHantoGame)
		{
			ourGame = (CommonHantoGame)game;
			thinker = new HantoPlayerAStarThinker();
		}
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		if(ourGame == null)
		{
			return new HantoMoveRecord(null, null, null);
		}
		
		if(opponentsMove != null && !applyMove(opponentsMove))
		{
			return new HantoMoveRecord(null, null, null);
		}
		
		HantoMoveRecord move = thinker.selectMove(ourGame);
		
		if(move == null || !applyMove(move))
		{
			return new HantoMoveRecord(null, null, null);
		}
		
		return move;
	}

	private boolean applyMove(HantoMoveRecord move) 
	{
		try 
		{
			ourGame.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} 
		catch (HantoException e) 
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets the game of this bot.
	 * @param game The game state
	 */
	public void setGame(CommonHantoGame game)
	{
		ourGame = game;
	}
	
	/**
	 * Gets the game of this bot
	 * @return the game state
	 */
	public CommonHantoGame getGame()
	{
		return ourGame;
	}

}
