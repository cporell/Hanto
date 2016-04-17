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
	private IHantoBoard board;
	private IHantoRuleSet rules;
	private IHantoPieceFactory pieceFactory;
	
	/**
	 * Builds a GammaHanto game and sets the starting player appropriately.
	 * @param startColor Starting player
	 */
	public CommonHantoGame(HantoPlayerColor startColor)
	{
		board = CreateBoard();
		rules = CreateRuleSet(startColor);
		pieceFactory = CreatePieceFactory();
	}
	
	/**
	 * Create a board for a particular version of Hanto.
	 * @return The Hanto board
	 */
	protected abstract IHantoBoard CreateBoard();
	
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
		rules.beginTurn(board);
		
		IHantoMover mover;
		if(from == null)
		{
			HantoCommonPiece piece = pieceFactory.createPiece(pieceType, rules.getCurrentTurn());
			mover = piece.createPlaceMover(to);
		}
		else
		{
			HantoCommonPiece[] pieces = board.getPieces(from);
			HantoCommonPiece selectedPiece = null;
			for(HantoCommonPiece piece : pieces)
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
			
			mover = selectedPiece.createWalkMover(to);
		}
		
		IHantoMoverValidator validator = rules.createMoverValidator(mover);
		
		boolean shouldContinue;
		do
		{
			validator.checkIteration(board);
			shouldContinue = mover.iterateMove(board);
			rules.checkBoard(board, pieceType);
		}
		while(shouldContinue);
		
		return rules.endTurn(board);
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) 
	{
		if(board.getPieces(where).length > 0)
		{
			return board.getPieces(where)[0];
		}
		else
		{
			return null;
		}
	}

	@Override
	public abstract String getPrintableBoard();

}