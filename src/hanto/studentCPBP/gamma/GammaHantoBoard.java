package hanto.studentCPBP.gamma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;

public class GammaHantoBoard implements IHantoBoard
{
	private Map<HantoCoordinate, ArrayList<HantoCommonPiece>> boardLookup = new HashMap<>();
	
	@Override
	public void addPiece(HantoCommonPiece piece, HantoCoordinate at) 
	{
		ArrayList<HantoCommonPiece> current = boardLookup.get(convertToLocalCoordImpl(at));
		if(current == null)
		{
			current = new ArrayList<>();
			boardLookup.put(convertToLocalCoordImpl(at), current);
		}
		
		current.add(piece);
	}

	@Override
	public HantoCommonPiece[] getPieces(HantoCoordinate at) 
	{
		ArrayList<HantoCommonPiece> pieces = boardLookup.get(convertToLocalCoordImpl(at));
		if(pieces == null)
		{
			return new HantoCommonPiece[0];
		}
		else
		{
			HantoCommonPiece[] pieceArray = new HantoCommonPiece[pieces.size()];
			return pieces.toArray(pieceArray);
		}
	}
	
	@Override
	public HantoCoordinate[] getAllTakenLocations() 
	{
		HantoCoordinate[] coords = new HantoCoordinate[boardLookup.size()];
		boardLookup.keySet().toArray(coords);
		return coords;
	}
	
	private HantoCoordinateImpl convertToLocalCoordImpl(HantoCoordinate coord)
	{
		return new HantoCoordinateImpl(coord);
	}

	@Override
	public HantoCoordinate[] getAdjacent(HantoCoordinate at)
	{
		HantoCoordinate[] coords = new HantoCoordinate[6];
		
		coords[0] = new HantoCoordinateImpl(at.getX() + 1, at.getY());
		coords[1] = new HantoCoordinateImpl(at.getX() + 1, at.getY() - 1);
		coords[2] = new HantoCoordinateImpl(at.getX(), at.getY() - 1);
		coords[3] = new HantoCoordinateImpl(at.getX() - 1, at.getY());
		coords[4] = new HantoCoordinateImpl(at.getX() - 1, at.getY() + 1);
		coords[5] = new HantoCoordinateImpl(at.getX(), at.getY() + 1);
		
		return coords;
	}
}
