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
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.CommonHantoPiece;
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
public class DeltaHantoRuleSet extends GenericHantoRuleCollection
{
	private boolean triggerSurrender;
	
	/**
	 * Construct a DeltaHanto rule set
	 * @param startingColor The player who goes first
	 */
	public DeltaHantoRuleSet(HantoPlayerColor startingColor)
	{
		HantoHandFactory playerFactory = HantoHandFactory.getInstance();
		setBlueHand(playerFactory.makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.BLUE));
		setRedHand(playerFactory.makeHantoHand(HantoGameID.DELTA_HANTO, HantoPlayerColor.RED));
		currentTurn = startingColor == HantoPlayerColor.BLUE ? getBlueHand() : getRedHand();
	}
	
	@Override
	public IHantoMoverValidator createMoverValidator(IHantoMover mover) 
	{
		if(mover instanceof PlaceMover)
		{
			return new DeltaHantoPlaceMoverValidator((PlaceMover) mover, this);
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
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
		checkAllValidPieces(board);
		checkStartAtOrigin(board);
		checkForDoubleStacked(board);
		checkForConnectivity(board);
		checkButterflyPlacedByFourthRound(board);
	}

	@Override
	public CommonHantoHand getCurrentPlayer()
	{
		return currentTurn == getBlueHand() ? getBlueHand() : getRedHand();
	}
	
	/**
	 * Get the hand of the current player
	 * @return The hand of the current player
	 */
	public CommonHantoHand getCurrentHand()
	{
		return currentTurn;
	}

	@Override
	public void beginTurn(IHantoBoard board) throws HantoException 
	{
		if(getIsGameOver())
		{
			throw new HantoException("You cannot place a piece after the game is over.");
		}
	}

	@Override
	public MoveResult endTurn(IHantoBoard board) throws HantoException 
	{
		MoveResult result = getTurnResult(board);
		
		moveCount++;
		currentTurn = currentTurn == getBlueHand() ? getRedHand() : getBlueHand();
		
		return result;
	}

	@Override
	public void onNoInput() 
	{
		triggerSurrender = true;
	}

	@Override
	public int getTurnNumber()
	{
		return (moveCount / 2) + 1;
	}

	@Override
	public int getMoveNumber() 
	{
		return moveCount;
	}

	private MoveResult getTurnResult(IHantoBoard board) 
	{
		HantoCoordinate blueButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.BLUE, board);
		HantoCoordinate redButterflyLocation = getButterflyOfColorLocation(HantoPlayerColor.RED, board);
		
		boolean isBlueSurrounded;
		boolean isRedSurrounded;
		
		if(getBlueHand().getButterflyPlaced())
		{
			isBlueSurrounded = checkLocationSurrounded(board, blueButterflyLocation);
		}
		else
		{
			isBlueSurrounded = false;
		}
		
		if(getRedHand().getButterflyPlaced())
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
			setIsGameOver(true);
		}
		else if(isBlueSurrounded)
		{
			result = MoveResult.RED_WINS;
			setIsGameOver(true);
		}
		else if(isRedSurrounded)
		{
			result = MoveResult.BLUE_WINS;
			setIsGameOver(true);
		}
		else if(triggerSurrender)
		{
			result = getCurrentPlayer() == getRedHand() ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
			setIsGameOver(true);
		}
		else
		{
			result = MoveResult.OK;
		}
		
		return result;
	}

	private void checkStartAtOrigin(IHantoBoard board) throws HantoException {
		if(moveCount == 0 && board.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
	}

	private HantoCoordinate getButterflyOfColorLocation(HantoPlayerColor color, IHantoBoard board)
	{
		CommonHantoPiece[] pieces = board.getPieces();
		for(CommonHantoPiece piece : pieces)
		{
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
			{
				return board.getPieceLocation(piece);
			}
		}
		
		return null;
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
			if(currentTurn == getBlueHand())
			{
				if(!getBlueHand().getButterflyPlaced())
				{
					throw new HantoException("Blue did not place butterfly by 4th turn.");	
				}
			}
			else
			{
				if(!getRedHand().getButterflyPlaced())
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
			CommonHantoPiece piece = board.getPieces(coord)[0];
			if(piece.getType() != HantoPieceType.BUTTERFLY && 
					piece.getType() != HantoPieceType.CRAB && 
					piece.getType() != HantoPieceType.SPARROW)
			{
				throw new HantoException("Can only place butterfly or sparrow or crab.");
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
}
