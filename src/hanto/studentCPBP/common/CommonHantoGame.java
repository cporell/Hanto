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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

/**
 * Template for HantoGames.
 * @author cgporell
 * @author bpeake
 *
 */
public abstract class CommonHantoGame implements HantoGame
{
	private IHantoGameState state;
	private IHantoRuleSet rules;
	private IHantoPieceFactory pieceFactory;
	
	/**
	 * Builds a GammaHanto game and sets the starting player appropriately.
	 * @param startColor Starting player
	 */
	public CommonHantoGame(HantoPlayerColor startColor)
	{
		rules = CreateRuleSet(startColor);
		pieceFactory = CreatePieceFactory();
		state = CreateGameState(pieceFactory);
	}
	
	/**
	 * Create a game state to use for the game.
	 * @param pieceFactory the factory used to create initial pieces.
	 * @return The hanto game state.
	 */
	protected abstract IHantoGameState CreateGameState(IHantoPieceFactory pieceFactory);
	
	/**
	 * Create a ruleset for a particular version of Hanto.
	 * @param startingColor The player who goes first.
	 * @return The Hanto rules
	 */
	protected abstract IHantoRuleSet CreateRuleSet(HantoPlayerColor startingColor);
	
	/**
	 * Create a Hanto Piece Factory for a particular version of Hanto.
	 * @return A HantoPieceFactory
	 */
	protected abstract IHantoPieceFactory CreatePieceFactory();
	
	@Override 
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException 
	{
		rules.beginTurn(state);
		
		IHantoMover mover;
		if(from == null && to == null && pieceType == null)
		{
			rules.onNoInput(state);
			return rules.endTurn(state);
		}
		else if(from == null)
		{
			CommonHantoPiece[] hand = state.getPiecesInHand(rules.getCurrentPlayer(state), pieceType);
			if(hand.length == 0)
			{
				throw new HantoException("Cannot place piece, none in hand.");
			}
			
			CommonHantoPiece piece = hand[0];
			
			mover = piece.createPlaceMover(to);
		}
		else
		{
			CommonHantoPiece[] pieces = state.getPieces(from);
			CommonHantoPiece selectedPiece = null;
			for(CommonHantoPiece piece : pieces)
			{ 
				if(piece.getType() == pieceType)
				{
					selectedPiece = piece;
					break;
				}
			}
			
			if(selectedPiece == null)
			{
				throw new HantoException("The piece does not exist.");
			}
			
			mover = selectedPiece.createWalkMover(from, to);
		}
		
		IHantoMoverValidator validator = rules.createMoverValidator(mover);
		
		boolean shouldContinue;
		do
		{
			shouldContinue = false;
			
			try
			{
				validator.preIteration(state);
				shouldContinue = mover.iterateMove(state);
				validator.checkIteration(state);
				rules.check(state);
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
		
		return rules.endTurn(state);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) 
	{
		if(state.getPieces(where).length > 0)
		{
			return state.getPieces(where)[0];
		}
		else
		{
			return null;
		}
	}
	
	public IHantoRuleSet getRules()
	{
		return rules;
	}
	
	public IHantoGameState getState()
	{
		return state;
	}
	
	public void setState(IHantoGameState newState)
	{
		state = newState;
	}

	@Override
	public abstract String getPrintableBoard();

}