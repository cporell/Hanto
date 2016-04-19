/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.delta;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoHand;
import hanto.studentCPBP.common.DefaultHantoMoverValidator;
import hanto.studentCPBP.common.FlyMover;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.HantoHandFactory;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoMoverValidator;
import hanto.studentCPBP.common.IHantoRuleSet;
import hanto.studentCPBP.common.PlaceMover;
import hanto.studentCPBP.common.WalkMover;

/**
 * Custom rules for Delta Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoRuleSet implements IHantoRuleSet
{
	private CommonHantoHand currentTurn;
	private int moveCount = 0;
	private boolean isGameOver = false;
	private CommonHantoHand blueHand;
	private CommonHantoHand redHand;
	private HantoPieceType currentPiece = null;
	
	/**
	 * Construct a DeltaHanto rule set
	 * @param startingColor The player who goes first
	 */
	public DeltaHantoRuleSet(HantoPlayerColor startingColor)
	{
		HantoHandFactory playerFactory = HantoHandFactory.getInstance();
		blueHand = playerFactory.makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.BLUE);
		redHand = playerFactory.makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.RED);
		currentTurn = startingColor == HantoPlayerColor.BLUE ? blueHand : redHand;
	}
	
	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new DeltaHantoPlaceMoveValidator((PlaceMover) mover, this);
		}
		else if(mover instanceof WalkMover)
		{
			switch(((WalkMover) mover).getPiece().getType())
			{
			case BUTTERFLY:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 1);
			case CRAB:
				return new DeltaHantoWalkMoverValidator((WalkMover) mover, this, 3);
			}
		}
		else if(mover instanceof FlyMover)
		{
			return new DeltaHantoFlyMoverValidator((FlyMover) mover, this);
		}
		
		return new DefaultHantoMoverValidator();
	}
	
	@Override
	public void checkBoard(IHantoBoard board, HantoPieceType piece) throws HantoException 
	{
		currentPiece = piece;
		checkAllValidPieces(board);
		checkStartAtOrigin(board);
		checkForDoubleStacked(board);
		checkForConnectivity(board);
		checkTooManyButterflys(board);
		checkTooManySparrows(board);
		checkTooManyCrabs(board);
		checkButterflyPlacedByFourthRound(board);
	}

	@Override
	public HantoPlayerColor getCurrentTurn()
	{
		return currentTurn == blueHand ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
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
		resolveHands();
		MoveResult result = getTurnResult(board);
		
		moveCount++;
		//currentTurn = currentTurn == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		currentTurn = currentTurn == blueHand ? redHand : blueHand;
		
		return result;
	}

	private void resolveHands() 
	{
		currentTurn.takePieceFromHand(currentPiece);
	}

	private MoveResult getTurnResult(IHantoBoard board) 
	{
		HantoCoordinate blueButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.BLUE, board);
		HantoCoordinate redButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.RED, board);
		
		boolean isBlueSurrounded;
		boolean isRedSurrounded;
		
		//if(blueButterflyLocation != null)
		if(blueHand.getButterflyPlaced())
		{
			isBlueSurrounded = checkLocationSurrounded(board, blueButterflyLocation);
		}
		else
		{
			isBlueSurrounded = false;
		}
		
		//if(redButterflyLocation != null)
		if(redHand.getButterflyPlaced())
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
		/*
		else if(moveCount == 39)
		{
			result = MoveResult.DRAW;
			isGameOver = true;
		}
		*/
		else
		{
			result = MoveResult.OK;
		}
		
		return result;
	}

	@Override
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
			HantoCommonPiece piece = board.getPieces(coord)[0];
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
			HantoCommonPiece piece = board.getPieces(coord)[0];
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
			HantoCommonPiece piece = board.getPieces(coord)[0];
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
		
		if(numOfBlueSparrows > 4)
		{
			throw new HantoException("Blue has too many sparrows.");
		}
		else if(numOfRedSparrows > 4)
		{
			throw new HantoException("Red has too many sparrows.");
		}
	}
	
	private void checkTooManyCrabs(IHantoBoard board) throws HantoException {
		int numOfBlueCrabs = 0;
		int numOfRedCrabs = 0;
		HantoCoordinate[] takenLocations = board.getAllTakenLocations();
		for(HantoCoordinate coord : takenLocations)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.CRAB)
			{
				if(piece.getColor() == HantoPlayerColor.BLUE)
				{
					numOfBlueCrabs++;
				}
				else
				{
					numOfRedCrabs++;
				}
			}
		}
		
		if(numOfBlueCrabs > 4)
		{
			throw new HantoException("Blue has too many crabs.");
		}
		else if(numOfRedCrabs > 4)
		{
			throw new HantoException("Red has too many crabs.");
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
			//if(currentTurn == HantoPlayerColor.BLUE)
			if(currentTurn == blueHand)
			{
				//if(!isButterflyOfColor(HantoPlayerColor.BLUE, board))
				if(!blueHand.getButterflyPlaced())
				{
					throw new HantoException("Blue did not place butterfly by 4th turn.");	
				}
			}
			else
			{
				//if(!isButterflyOfColor(HantoPlayerColor.RED, board))
				if(!redHand.getButterflyPlaced())
				{
					throw new HantoException("Red did not place butterfly by 4th turn.");
				}
			}
		}
	}

	private void checkAllValidPieces(IHantoBoard board) throws HantoException {
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
			if(piece.getType() != HantoPieceType.BUTTERFLY && 
					piece.getType() != HantoPieceType.CRAB && 
					piece.getType() != HantoPieceType.SPARROW)
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

	/*
	 * TODO: Delete if we really don't need this
	private boolean isButterflyOfColor(HantoPlayerColor color, IHantoBoard board)
	{
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
			{
				return true;
			}
		}
		
		return false;
	}
	*/
	

}
