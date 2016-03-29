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

import java.util.Hashtable;

import hanto.common.*;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.HantoPieceImpl;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{

	private int moveNum = 0;
	private HantoPlayerColor currentTurn = HantoPlayerColor.BLUE;
	private HantoPieceImpl blueButterFly, redButterFly;
	
	private Hashtable<HantoCoordinateImpl, HantoPieceImpl> map = new Hashtable<>();
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		HantoCoordinateImpl implTo = new HantoCoordinateImpl(to);
		
		placePieceOnBoard(buildHantoPiece(pieceType), implTo);
		
		currentTurn = currentTurn == HantoPlayerColor.BLUE ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		moveNum++;
		
		return MoveResult.OK;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoCoordinateImpl implWhere = new HantoCoordinateImpl(where);
		return map.get(implWhere);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @return
	 * @throws HantoException
	 */
	private HantoPieceImpl buildHantoPiece(HantoPieceType type) throws HantoException
	{
		HantoPieceImpl piece = new HantoPieceImpl(currentTurn, type);
		return piece;
	}
	
	//@Deprecated
	private HantoPieceImpl createButterflyForPlayer() throws HantoException 
	{
		HantoPieceImpl createdPiece;
		
		switch(currentTurn)
		{
		case BLUE:
			blueButterFly = new HantoPieceImpl(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY);
			createdPiece = blueButterFly;
			break;
		case RED:
			redButterFly = new HantoPieceImpl(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY);
			createdPiece = redButterFly;
			break;
		default:
			throw new HantoException("Unexpected color");
		}
		
		return createdPiece;
	}

	private void placePieceOnBoard(HantoPieceImpl piece, HantoCoordinateImpl where) throws HantoException
	{
		if(map.get(where) == null)
		{
			Hashtable<HantoCoordinateImpl, HantoPieceImpl> adjacency = getAdjacentPieces(where);
			if (adjacency.size() == 0 && moveNum > 0)	
			{
				throw new HantoException("Cannot place piece - no adjacent pieces.");
			}
			else
			{
				map.put(where, piece);	
			}
		}
		else
		{
			throw new HantoException("Cannot place piece on another piece");
		}
	}
	
	private Hashtable<HantoCoordinateImpl, HantoPieceImpl> getAdjacentPieces(HantoCoordinateImpl where)
	{

		Hashtable<HantoCoordinateImpl, HantoPieceImpl> table = new Hashtable<>();
		
		HantoCoordinateImpl northEast = new HantoCoordinateImpl(where.getX() + 1, where.getY());
		HantoCoordinateImpl southEast = new HantoCoordinateImpl(where.getX() + 1, where.getY() - 1);
		HantoCoordinateImpl south = new HantoCoordinateImpl(where.getX(), where.getY() - 1);
		HantoCoordinateImpl southWest = new HantoCoordinateImpl(where.getX() - 1, where.getY());
		HantoCoordinateImpl northWest = new HantoCoordinateImpl(where.getX() - 1, where.getY() + 1);
		HantoCoordinateImpl north = new HantoCoordinateImpl(where.getX(), where.getY() + 1);
		
		HantoPieceImpl northPiece = map.get(north);
		HantoPieceImpl northEastPiece = map.get(northEast);
		HantoPieceImpl northWestPiece = map.get(northWest);
		HantoPieceImpl southPiece = map.get(south);
		HantoPieceImpl southEastPiece = map.get(southEast);
		HantoPieceImpl southWestPiece = map.get(southWest);
		
		if(northPiece != null)
		{
			table.put(north, map.get(north));
		}
		if(northEastPiece != null) 
		{
			table.put(northEast, map.get(northEast));
		}
		if(northWestPiece != null) 
		{
			table.put(northWest, map.get(northWest));
		}
		if(southPiece != null) 
		{
			table.put(south, map.get(south));
		}
		if(southEastPiece != null) 
		{
			table.put(southEast, map.get(southEast));
		}
		if(southWestPiece != null) 
		{
			table.put(southWest, map.get(southWest));
		}
		
		return table;
	}
}
