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

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoHand;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.WalkMover;

/**
 * Custom rules for Gamma Hanto
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class GammaHantoRuleSet implements IHantoRuleSet
{
	private CommonHantoHand currentTurnColor;
	private int moveCount = 0;
	private CommonHantoHand blueHand;
	private CommonHantoHand redHand;
	private boolean isGameOver = false;
	
	/**
	 * Builds a ruleset for Gamma Hanto, based on the starting color.
	 * @param startingColor THe starting player
	 */
	public GammaHantoRuleSet(HantoPlayerColor startingColor)
	{
		currentTurnColor = startingColor;
	}
	
	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new GammaHantoPlaceMoverValidator((PlaceMover) mover, this);
		}
		else if(mover instanceof WalkMover)
		{
			return new GammaHantoWalkMoverValidator((WalkMover) mover, this);
		}
		
		return null;
	}

	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
		checkAllValidPieces(board);
		checkStartAtOrigin(board);
		checkForDoubleStacked(board);
		checkForConnectivity(board);
		checkTooManyButterflys(board);
		checkTooManySparrows(board);
		checkButterflyPlacedByFourthRound(board);
	}

	@Override
	public HantoPlayerColor getCurrentTurn()
	{
		return currentTurnColor;
	}

	@Override
	public void beginTurn(IHantoBoard board) throws HantoException 
	{
		if(isGameOver)
		{
			throw new HantoException("You cannot place a piece after the game is over.");
		}
	}

	@Override
	public MoveResult endTurn(IHantoBoard board) throws HantoException 
	{
		MoveResult result = getTurnResult(board);
		
		moveCount++;
		currentTurnColor = currentTurnColor == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		return result;
	}

	@Override
	public void onNoInput() 
	{
	}

	private MoveResult getTurnResult(IHantoBoard board) throws HantoException
	{
		HantoCoordinate blueButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.BLUE, board);
		HantoCoordinate redButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.RED, board);
		
		boolean isBlueSurrounded;
		boolean isRedSurrounded;
		
		if(blueButterflyLocation != null)
		{
			isBlueSurrounded = checkLocationSurrounded(board, blueButterflyLocation);
		}
		else
		{
			isBlueSurrounded = false;
		}
		
		if(redButterflyLocation != null)
		{
			isRedSurrounded = checkLocationSurrounded(board, redButterflyLocation);
		}
		else
		{
			isRedSurrounded = false;
		}
		
		MoveResult result;
		if(isBlueSurrounded && isRedSurrounded)
		{
			result = MoveResult.DRAW;
			isGameOver = true;
		}
		else if(isBlueSurrounded)
		{
			result = MoveResult.RED_WINS;
			isGameOver = true;
		}
		else if(isRedSurrounded)
		{
			result = MoveResult.BLUE_WINS;
			isGameOver = true;
		}
		else if(moveCount == 39)
		{
			result = MoveResult.DRAW;
			isGameOver = true;
		}
		else
		{
			result = MoveResult.OK;
		}
		
		return result;
	}

	public int getTurnNumber()
	{
		return (moveCount / 2) + 1;
	}
	
	private void checkStartAtOrigin(IHantoBoard board) throws HantoException {
		if(moveCount == 0 && board.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
	}

	private HantoCoordinate getButterflyOfColorLocation(HantoPlayerColor color, IHantoBoard board)
	{
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
			{
				return coord;
			}
		}
		
		return null;
	}

	private void checkTooManyButterflys(IHantoBoard board) throws HantoException {
		int numOfBlueButterflys = 0;
		int numOfRedButterflys = 0;
		HantoCoordinate[] takenLocations = board.getAllTakenLocations();
		for(HantoCoordinate coord : takenLocations)
		{
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY)
			{
				if(piece.getColor() == HantoPlayerColor.BLUE)
				{
					numOfBlueButterflys++;
				}
				else
				{
					numOfRedButterflys++;
				}
			}
		}
		
		if(numOfBlueButterflys > 1)
		{
			throw new HantoException("Blue has too many butterflys.");
		}
		else if(numOfRedButterflys > 1)
		{
			throw new HantoException("Red has too many butterflys.");
		}
	}

	private void checkTooManySparrows(IHantoBoard board) throws HantoException {
		int numOfBlueSparrows = 0;
		int numOfRedSparrows = 0;
		HantoCoordinate[] takenLocations = board.getAllTakenLocations();
		for(HantoCoordinate coord : takenLocations)
		{
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.SPARROW)
			{
				if(piece.getColor() == HantoPlayerColor.BLUE)
				{
					numOfBlueSparrows++;
				}
				else
				{
					numOfRedSparrows++;
				}
			}
		}
		
		if(numOfBlueSparrows > 5)
		{
			throw new HantoException("Blue has too many sparrows.");
		}
		else if(numOfRedSparrows > 5)
		{
			throw new HantoException("Red has too many sparrows.");
		}
	}

	private void checkForConnectivity(IHantoBoard board) throws HantoException {
		HantoCoordinate[] takenLocations = board.getAllTakenLocations();
		if(takenLocations.length > 0)
		{
			Set<HantoCoordinate> visited = new HashSet<>();
			buildConnectivity(takenLocations[0], visited, board);
			
			if(visited.size() != takenLocations.length)
			{
				throw new HantoException("Not all pieces are connected.");
			}
		}
	}

	private void checkForDoubleStacked(IHantoBoard board) throws HantoException {
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			if(board.getPieces(coord).length > 1)
			{
				throw new HantoException("Cannot have multiple pieces at one location.");
			}
		}
	}
	
	private void checkButterflyPlacedByFourthRound(IHantoBoard board) throws HantoException
	{
		if(getTurnNumber() == 4)
		{
			if(currentTurnColor == HantoPlayerColor.BLUE)
			{
				if(!isButterflyOfColor(HantoPlayerColor.BLUE, board))
				{
					throw new HantoException("Blue did not place butterfly by 4th turn.");	
				}
			}
			else
			{
				if(!isButterflyOfColor(HantoPlayerColor.RED, board))
				{
					throw new HantoException("Blue did not place butterfly by 4th turn.");
				}
			}
		}
	}

	private void checkAllValidPieces(IHantoBoard board) throws HantoException {
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() != HantoPieceType.BUTTERFLY && piece.getType() != HantoPieceType.SPARROW)
			{
				throw new HantoException("Can only place butterfly or sparrow.");
			}
		}
	}
 
	private boolean checkLocationSurrounded(IHantoBoard board, HantoCoordinate blueButterflyLocation) 
	{
		HantoCoordinate[] adjacent = board.getAdjacent(blueButterflyLocation);
		int numPieces = 0;
		for(HantoCoordinate coord : adjacent)
		{
			if(board.getPieces(coord).length > 0)
			{
				numPieces++;
			}
		}
		
		return numPieces == 6;
	}

	private void buildConnectivity(HantoCoordinate at, Set<HantoCoordinate> visited, IHantoBoard board)
	{
		visited.add(at);
		
		HantoCoordinate[] adjacent = board.getAdjacent(at);
		for(HantoCoordinate coord : adjacent)
		{
			if(visited.contains(coord))
			{
				continue;
			}
			
			if(board.getPieces(coord).length == 0)
			{
				continue;
			}
			
			buildConnectivity(coord, visited, board);
		}
	}

	
	private boolean isButterflyOfColor(HantoPlayerColor color, IHantoBoard board)
	{
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
			{
				return true;
			}
		}
		
		return false;
	}
}
