package hanto.studentCPBP.gamma;

import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoRuleSet;

public class GammaHantoRuleSet implements IHantoRuleSet
{
	private HantoPlayerColor currentTurnColor;
	private int moveCount = 0;
	
	public GammaHantoRuleSet(HantoPlayerColor startingColor)
	{
		currentTurnColor = startingColor;
	}
	
	@Override
	public MoveResult checkBoard(IHantoBoard board) throws HantoException 
	{
		checkAllValidPieces(board);
		checkStartAtOrigin(board);
		checkForDoubleStacked(board);
		checkForConnectivity(board);
		checkTooManyButterflys(board);
		checkButterflyPlacedByFourthRound(board);
		
		return getTurnResult(board);
	}

	@Override
	public HantoPlayerColor getCurrentTurn()
	{
		return currentTurnColor;
	}

	@Override
	public void beginTurn() 
	{
		
	}

	@Override
	public void endTurn() 
	{
		moveCount++;
		currentTurnColor = currentTurnColor == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
	}

	private MoveResult getTurnResult(IHantoBoard board) 
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
		}
		else if(isBlueSurrounded)
		{
			result = MoveResult.RED_WINS;
		}
		else if(isRedSurrounded)
		{
			result = MoveResult.BLUE_WINS;
		}
		else
		{
			result = MoveResult.OK;
		}
		
		return result;
	}

	private int getTurnNumber()
	{
		return (moveCount / 2) + 1;
	}
	
	private void checkStartAtOrigin(IHantoBoard board) throws HantoException {
		if(moveCount == 0 && board.getPieces(new HantoCoordinateImpl(0, 0)).length == 0)
		{
			throw new HantoException("Must start at origin.");
		}
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

	private void checkForConnectivity(IHantoBoard board) throws HantoException {
		HantoCoordinate[] takenLocations = board.getAllTakenLocations();
		if(takenLocations.length > 0)
		{
			HashSet<HantoCoordinate> visited = new HashSet<>();
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
					throw new HantoException("Blue did not place butterfly by 4th turn.");
			}
			else
			{
				if(!isButterflyOfColor(HantoPlayerColor.RED, board))
					throw new HantoException("Blue did not place butterfly by 4th turn.");
			}
		}
	}

	private void checkAllValidPieces(IHantoBoard board) throws HantoException {
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
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

	private void buildConnectivity(HantoCoordinate at, HashSet<HantoCoordinate> visited, IHantoBoard board)
	{
		visited.add(at);
		
		HantoCoordinate[] adjacent = board.getAdjacent(at);
		for(HantoCoordinate coord : adjacent)
		{
			if(visited.contains(coord))
				continue;
			
			if(board.getPieces(coord).length == 0)
				continue;
			
			buildConnectivity(coord, visited, board);
		}
	}
	
	private boolean isButterflyOfColor(HantoPlayerColor color, IHantoBoard board)
	{
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
				return true;
		}
		
		return false;
	}
	
	private HantoCoordinate getButterflyOfColorLocation(HantoPlayerColor color, IHantoBoard board)
	{
		HantoCoordinate[] allCoords = board.getAllTakenLocations();
		for(HantoCoordinate coord : allCoords)
		{
			HantoCommonPiece piece = board.getPieces(coord)[0];
			if(piece.getType() == HantoPieceType.BUTTERFLY && piece.getColor() == color)
				return coord;
		}
		
		return null;
	}
}
