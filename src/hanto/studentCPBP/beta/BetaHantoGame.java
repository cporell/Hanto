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
import java.util.Map;

import hanto.common.*;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.HantoPieceImpl;
import hanto.studentCPBP.common.MoveException;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private boolean blueButterflyPlaced = false;
	private HantoCoordinateImpl blueButterflyLocation;
	private boolean redButterflyPlaced = false;
	private HantoCoordinateImpl redButterflyLocation;

	private int moveNum = 0;
	private HantoPlayerColor currentTurn = HantoPlayerColor.BLUE;
	
	private boolean isGameOver = false;

	private Map<HantoCoordinateImpl, HantoPieceImpl> map = new Hashtable<>();

	public BetaHantoGame(HantoPlayerColor movesFirst) 
	{
		currentTurn = movesFirst;
	}

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		HantoCoordinateImpl implTo = new HantoCoordinateImpl(to);
		
		try
		{
			validateMove(pieceType, implTo);
		}
		catch (MoveException e)
		{
			switch(currentTurn)
			{
			case BLUE:
				isGameOver = true;
				return MoveResult.RED_WINS;
			case RED:
				isGameOver = true;
				return MoveResult.BLUE_WINS;
			}
		}

		placePieceOnBoard(buildHantoPiece(pieceType), implTo);

		boolean hasRedWon = testForBlueSurrounded();
		boolean hasBlueWon = testForRedSurrounded();
		
		if(hasRedWon && hasBlueWon)
		{
			isGameOver = true;
			return MoveResult.DRAW;
		}
		else if(hasRedWon)
		{
			isGameOver = true;
			return MoveResult.RED_WINS;
		}
		else if(hasBlueWon)
		{
			isGameOver = true;
			return MoveResult.BLUE_WINS;
		}

		if(moveNum >= 11)
		{
			isGameOver = true;
			return MoveResult.DRAW;
		}

		currentTurn = currentTurn == HantoPlayerColor.BLUE ? 
				HantoPlayerColor.RED : HantoPlayerColor.BLUE;

		moveNum++;		

		return MoveResult.OK;
	}

	private boolean testForBlueSurrounded()
	{
		if(!blueButterflyPlaced)
			return false;
		
		Map<HantoCoordinateImpl, HantoPieceImpl> adjacent = getAdjacentPieces(blueButterflyLocation);
		return adjacent.size() >= 6;
	}
	
	private boolean testForRedSurrounded()
	{
		if(!redButterflyPlaced)
			return false;
		
		Map<HantoCoordinateImpl, HantoPieceImpl> adjacent = getAdjacentPieces(redButterflyLocation);
		return adjacent.size() >= 6;
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
	 * Validates a move given a piece type (and the current player)
	 * @param type The type of Hanto piece
	 * @return Whether the move is valid or not
	 */
	private void validateMove(HantoPieceType type, HantoCoordinateImpl where) throws MoveException, HantoException
	{
		if(isGameOver)
		{
			throw new HantoException("You cannot move when the game is over.");
		}
		else if(type != HantoPieceType.SPARROW && type != HantoPieceType.BUTTERFLY)
		{
			throw new MoveException("In beta you can only place a sparrow or a butterfly.");
		}
		else if(moveNum == 0 && !where.equals(new HantoCoordinateImpl(0, 0)))
		{
			throw new MoveException("First move must be at (0, 0).");
		}
		
		switch(currentTurn)
		{
		case BLUE:
			if((moveNum >= 6) && (!blueButterflyPlaced) && (type != HantoPieceType.BUTTERFLY))
			{
				throw new MoveException("Illegal move: Blue has no Butterfly and must place it at this point.");
			}
			else if(type == HantoPieceType.BUTTERFLY && blueButterflyPlaced)
			{
				throw new MoveException("Illegal move: Blue attempted to place a second Butterfly.");
			}
			break;
		case RED:
			if((moveNum >= 7) && (!redButterflyPlaced) && (type != HantoPieceType.BUTTERFLY))
			{
				throw new MoveException("Illegal move: Red has no Butterfly and must place it at this point.");
			}
			else if(type == HantoPieceType.BUTTERFLY && redButterflyPlaced)
			{
				throw new MoveException("Illegal move: Red attempted to place a second Butterfly.");
			}
			break;
		}
	}

	/**
	 * Builds a Hanto based given a type (and the current player)
	 * @param type The type of Hanto piece to build
	 * @return A Hanto piece with based on the type and the current player
	 * @throws HantoException
	 */
	private HantoPieceImpl buildHantoPiece(HantoPieceType type) throws HantoException
	{
		HantoPieceImpl piece = new HantoPieceImpl(currentTurn, type);

		return piece;
	}

	private void placePieceOnBoard(HantoPieceImpl piece, HantoCoordinateImpl where) throws HantoException
	{
		if(map.get(where) == null)
		{
			Map<HantoCoordinateImpl, HantoPieceImpl> adjacency = getAdjacentPieces(where);
			if (adjacency.size() == 0 && moveNum > 0)	
			{
				throw new HantoException("Cannot place piece - no adjacent pieces.");
			}
			else
			{
				if(piece.getType() == HantoPieceType.BUTTERFLY)
				{
					switch(currentTurn)
					{
					case BLUE:
						blueButterflyPlaced = true;
						blueButterflyLocation = where;
						break;
					case RED:
						redButterflyPlaced = true;
						redButterflyLocation = where;
						break;					
					}
				}
				
				map.put(where, piece);	
			}
		}
		else
		{
			throw new HantoException("Cannot place piece on another piece");
		}
	}

	private Map<HantoCoordinateImpl, HantoPieceImpl> getAdjacentPieces(HantoCoordinateImpl where)
	{

		Map<HantoCoordinateImpl, HantoPieceImpl> table = new Hashtable<>();

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
