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

package hanto.studentCPBP.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.BLUE;

import java.util.Hashtable;
import java.util.Map;

import hanto.common.*;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.HantoPieceImpl;
import hanto.studentCPBP.common.MoveException;

/**
 * Beta version of Hanto
 * @author Connor Porell cgporell
 * @author Benny Peake bpeake
 * @version Mar 16, 2016
 */
public class GammaHantoGame implements HantoGame
{
	private boolean blueButterflyPlaced = false;
	private HantoCoordinateImpl blueButterflyLocation;
	private boolean redButterflyPlaced = false;
	private HantoCoordinateImpl redButterflyLocation;

	private int turn = 0;
	private int moveNum = 0;
	private HantoPlayerColor currentTurn;
	private final HantoPlayerColor movesFirst;
	
	private boolean isGameOver = false;

	private Map<HantoCoordinateImpl, HantoPieceImpl> map = new Hashtable<>();

	/**
	 * Builds a BetaHantoGame with the specified player making the first move.
	 * @param movesFirst The player that will move first.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst) 
	{
		currentTurn = this.movesFirst = movesFirst;
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
		if (endOfTurn()) {
			turn++;
		}

		return MoveResult.OK;
	}

	private boolean testForBlueSurrounded()
	{
		if(!blueButterflyPlaced)
		{
			return false;
		}
		
		Map<HantoCoordinateImpl, HantoPieceImpl> adjacent = getAdjacentPieces(blueButterflyLocation);
		return adjacent.size() >= 6;
	}
	
	private boolean testForRedSurrounded()
	{
		if(!redButterflyPlaced)
		{
			return false;			
		}
		
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
		// Check for end of game.
		if(isGameOver)
		{
			throw new HantoException("You cannot move when the game is over.");
		}
		checkForLegalPieceTypes(type);
		checkForStartAtOrigin(where);
		checkButterflyPlacement(type);
		checkIfButterflyPlacedByFourthTurn();
	}
	
	private void checkButterflyPlacement(HantoPieceType type) throws HantoException
	{
		if (type == BUTTERFLY) {
			final boolean used = currentTurn == BLUE ? blueButterflyPlaced : redButterflyPlaced;
			if (used) {
				throw new HantoException("You have already played your BUTTERFLY");
			}
			if (currentTurn == BLUE) {
				blueButterflyPlaced = true;
			} else {
				redButterflyPlaced = true;
			}
		}	
	}
	
	// This method adapted from Prof. Pollice's solution to Beta Hanto.
	private void checkIfButterflyPlacedByFourthTurn() throws HantoException
	{		
		if (turn == 3) {
			final boolean butterflyUsed = currentTurn == BLUE ? blueButterflyPlaced
					: redButterflyPlaced;
			if (!butterflyUsed) {
				throw new HantoException("You must place Butterfly by the fourth turn. You lose");
			}
		}
	}
	
	private void checkForStartAtOrigin(HantoCoordinateImpl where) throws HantoException
	{
		if(moveNum == 0 && !where.equals(new HantoCoordinateImpl(0, 0)))
		{
			throw new HantoException("First move must be at origin.");
		}
	}

	private void checkForLegalPieceTypes(HantoPieceType type) throws HantoException
	{
		if(type != HantoPieceType.SPARROW && type != HantoPieceType.BUTTERFLY)
		{
			throw new HantoException("In gamma you can only place a sparrow or a butterfly.");
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
	
	private boolean endOfTurn()
	{
		return currentTurn == movesFirst;
	}
}
