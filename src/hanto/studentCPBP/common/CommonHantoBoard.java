/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;

/**
 * Template for HantoBoards.
 * @author cgporell
 * @author bpeake
 *
 */
public class CommonHantoBoard implements IHantoBoard
{
	private Map<HantoCoordinateImpl, List<HantoCommonPiece>> boardLookup = new HashMap<>();
	private Map<HantoCommonPiece, HantoCoordinateImpl> pieceLookup = new HashMap<>();
	
	@Override
	public void addPiece(HantoCommonPiece piece, HantoCoordinate at) 
	{
		List<HantoCommonPiece> current = boardLookup.get(convertToLocalCoordImpl(at));
		if(current == null)
		{
			current = new ArrayList<>();
			boardLookup.put(convertToLocalCoordImpl(at), current);
		}
		
		current.add(piece);
		pieceLookup.put(piece, convertToLocalCoordImpl(at));
	}

	@Override
	public HantoCommonPiece[] getPieces(HantoCoordinate at) 
	{
		List<HantoCommonPiece> pieces = boardLookup.get(convertToLocalCoordImpl(at));
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
	public HantoCommonPiece[] getPieces() 
	{
		HantoCommonPiece[] pieces = new HantoCommonPiece[pieceLookup.size()];
		return pieceLookup.keySet().toArray(pieces);
	}

	@Override
	public HantoCoordinate[] getAllTakenLocations() 
	{
		HantoCoordinate[] coords = new HantoCoordinate[boardLookup.size()];
		boardLookup.keySet().toArray(coords);
		return coords;
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
 
	@Override
	public void movePiece(HantoCommonPiece piece, HantoCoordinate to)
	{
		List<HantoCommonPiece> fromPieces = boardLookup.get(pieceLookup.get(piece));
		fromPieces.remove(piece);
		
		if(fromPieces.size() == 0)
		{
			boardLookup.remove(pieceLookup.get(piece));
		}
		
		addPiece(piece, to);
	}

	private HantoCoordinateImpl convertToLocalCoordImpl(HantoCoordinate coord)
	{
		return new HantoCoordinateImpl(coord);
	}

	@Override
	public HantoCoordinate getPieceLocation(HantoCommonPiece piece) 
	{
		return pieceLookup.get(piece);
	}
}