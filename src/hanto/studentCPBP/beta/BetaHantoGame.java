/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentCPBP.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.Hashtable;
import java.util.Map;

import hanto.common.*;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.CommonHantoGameState;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.HantoPieceImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.PlaceholderPiece;

/**
 * Beta version of Hanto
 * @author Connor Porell cgporell
 * @author Benny Peake bpeake
 * @version Mar 16, 2016
 */
public class BetaHantoGame extends CommonHantoGame
{
	public BetaHantoGame(HantoPlayerColor startColor) 
	{
		super(startColor);
	}

	@Override
	protected IHantoGameState CreateGameState(IHantoPieceFactory pieceFactory) {
		CommonHantoGameState state = new CommonHantoGameState();
		
		state.addPiece(pieceFactory.createPiece(BUTTERFLY, BLUE));
		state.addPiece(pieceFactory.createPiece(SPARROW, BLUE));
		state.addPiece(pieceFactory.createPiece(SPARROW, BLUE));
		state.addPiece(pieceFactory.createPiece(SPARROW, BLUE));
		state.addPiece(pieceFactory.createPiece(SPARROW, BLUE));
		state.addPiece(pieceFactory.createPiece(SPARROW, BLUE));
		
		state.addPiece(pieceFactory.createPiece(BUTTERFLY, RED));
		state.addPiece(pieceFactory.createPiece(SPARROW, RED));
		state.addPiece(pieceFactory.createPiece(SPARROW, RED));
		state.addPiece(pieceFactory.createPiece(SPARROW, RED));
		state.addPiece(pieceFactory.createPiece(SPARROW, RED));
		state.addPiece(pieceFactory.createPiece(SPARROW, RED));
		
		return state;
	}

	@Override
	protected IHantoRuleSet CreateRuleSet(HantoPlayerColor startingColor)
	{
		return new BetaHantoRuleSet(startingColor);
	}

	@Override
	protected IHantoPieceFactory CreatePieceFactory() 
	{
		// TODO Auto-generated method stub
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
