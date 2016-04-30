/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studentCPBP.alpha;

import hanto.common.*;
import hanto.studentCPBP.common.*;
import hanto.studentCPBP.common.pieces.PlaceholderPiece;

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
/**
 * The implementation of Alpha Hanto.
 * @version Mar 2, 2016
 */
public class AlphaHantoGame extends CommonHantoGame
{

	public AlphaHantoGame(HantoPlayerColor startColor) 
	{
		super(startColor);
	}

	@Override
	protected IHantoGameState CreateGameState(IHantoPieceFactory pieceFactory) 
	{
		CommonHantoGameState state = new CommonHantoGameState();
		state.addPiece(pieceFactory.createPiece(BUTTERFLY, BLUE));
		state.addPiece(pieceFactory.createPiece(BUTTERFLY, RED));
		
		return state;
	}

	@Override
	protected IHantoRuleSet CreateRuleSet(HantoPlayerColor startingColor) 
	{
		return new AlphaHantoRuleSet(startingColor);
	}

	@Override
	protected IHantoPieceFactory CreatePieceFactory() 
	{
		return new IHantoPieceFactory() 
		{
			@Override
			public CommonHantoPiece createPiece(HantoPieceType type, HantoPlayerColor color) 
			{
				return new PlaceholderPiece(color, type);
			}
		};
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}
}
