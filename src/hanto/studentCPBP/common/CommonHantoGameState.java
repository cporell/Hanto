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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Template for HantoBoards.
 * @author cgporell
 * @author bpeake
 *
 */
public class CommonHantoGameState implements IHantoGameState
{
	private Map<HantoCoordinateImpl, Set<CommonHantoPiece>> boardLookup = new HashMap<>();
	private Map<CommonHantoPiece, HantoCoordinateImpl> pieceLookup = new HashMap<>();
	
	private Map<HantoPlayerColor, Set<CommonHantoPiece>> playerHands = new HashMap<>();
	
	private boolean isGameOver;
	private int moveCount;
	
	public void addPiece(CommonHantoPiece piece)
	{
		Set<CommonHantoPiece> piecesInHand = playerHands.get(piece.getColor());
		if(piecesInHand == null)
		{
			piecesInHand = new HashSet<>();
			playerHands.put(piece.getColor(), piecesInHand);
		}
		
		piecesInHand.add(piece);
	}
	
	@Override
	public void placePiece(CommonHantoPiece piece, HantoCoordinate at) 
	{
		Set<CommonHantoPiece> hand = playerHands.get(piece.getColor());
		if(hand != null)
		{
			hand.remove(piece);
		}
		
		Set<CommonHantoPiece> current = boardLookup.get(convertToLocalCoordImpl(at));
		if(current == null)
		{
			current = new HashSet<>();
			boardLookup.put(convertToLocalCoordImpl(at), current);
		}
		
		current.add(piece);
		pieceLookup.put(piece, convertToLocalCoordImpl(at));
	}

	@Override
	public CommonHantoPiece[] getPieces(HantoCoordinate at) 
	{
		Set<CommonHantoPiece> pieces = boardLookup.get(convertToLocalCoordImpl(at));
		if(pieces == null)
		{
			return new CommonHantoPiece[0];
		}
		else
		{
			CommonHantoPiece[] pieceArray = new CommonHantoPiece[pieces.size()];
			return pieces.toArray(pieceArray);
		}
	}
	
	@Override
	public CommonHantoPiece[] getPieces() 
	{
		CommonHantoPiece[] pieces = new CommonHantoPiece[pieceLookup.size()];
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
	public void movePiece(CommonHantoPiece piece, HantoCoordinate to)
	{
		HantoCoordinate location = pieceLookup.get(piece);
		Set<CommonHantoPiece> fromPieces = boardLookup.get(location);
		fromPieces.remove(piece);
		pieceLookup.remove(piece);
		
		if(fromPieces.size() == 0)
		{
			boardLookup.remove(location);
		}
		
		if(to != null)
		{
			placePiece(piece, to);
		}
		else
		{
			addPiece(piece);
		}
	}

	private HantoCoordinateImpl convertToLocalCoordImpl(HantoCoordinate coord)
	{
		return new HantoCoordinateImpl(coord);
	}

	@Override
	public HantoCoordinate getPieceLocation(CommonHantoPiece piece) 
	{
		return pieceLookup.get(piece);
	}

	@Override
	public CommonHantoPiece[] getPieces(HantoPlayerColor color) 
	{
		ArrayList<CommonHantoPiece> piecesOfColor = new ArrayList<>();
		for(CommonHantoPiece piece : getPieces())
		{
			if(piece.getColor().equals(color))
			{
				piecesOfColor.add(piece);
			}
		}
		
		CommonHantoPiece[] pieces = new CommonHantoPiece[piecesOfColor.size()];
		piecesOfColor.toArray(pieces);
		
		return pieces;
	}

	@Override
	public CommonHantoPiece[] getPieces(HantoPieceType pieceType) 
	{
		ArrayList<CommonHantoPiece> piecesOfType = new ArrayList<>();
		for(CommonHantoPiece piece : getPieces())
		{
			if(piece.getType().equals(pieceType))
			{
				piecesOfType.add(piece);
			}
		}
		
		CommonHantoPiece[] pieces = new CommonHantoPiece[piecesOfType.size()];
		piecesOfType.toArray(pieces);
		
		return pieces;
	}

	@Override
	public CommonHantoPiece[] getPieces(HantoPlayerColor color, HantoPieceType pieceType) 
	{
		ArrayList<CommonHantoPiece> matchingPieces = new ArrayList<>();
		for(CommonHantoPiece piece : getPieces())
		{
			if(piece.getType().equals(pieceType) && piece.getColor().equals(color))
			{
				matchingPieces.add(piece);
			}
		}
		
		CommonHantoPiece[] pieces = new CommonHantoPiece[matchingPieces.size()];
		matchingPieces.toArray(pieces);
		
		return pieces;
	}

	@Override
	public boolean isGameOver() 
	{
		return isGameOver;
	}

	@Override
	public void triggerGameOver() 
	{
		isGameOver = true;
	}

	@Override
	public int getMoveNumber() {
		// TODO Auto-generated method stub
		return moveCount;
	}

	@Override
	public void endMove() 
	{
		moveCount++;
	}

	@Override
	public IHantoGameState copy() 
	{
		CommonHantoGameState cpyState = new CommonHantoGameState();
		cpyState.isGameOver = isGameOver;
		cpyState.moveCount = moveCount;
		
		for(CommonHantoPiece piece : getPieces())
		{
			HantoCoordinate location = getPieceLocation(piece);	
			cpyState.addPiece(piece);
			cpyState.placePiece(piece, location);
		}
		
		for(Map.Entry<HantoPlayerColor, Set<CommonHantoPiece>> entry : playerHands.entrySet())
		{
			for(CommonHantoPiece piece : entry.getValue())
			{
				cpyState.addPiece(piece);
			}
		}
		
		return cpyState;
	}

	@Override
	public CommonHantoPiece[] getPiecesInHand(HantoPlayerColor color)
	{
		CommonHantoPiece[] pieces;
		Set<CommonHantoPiece> piecesSet = playerHands.get(color);
		if(piecesSet == null)
		{
			pieces = new CommonHantoPiece[0];
		}
		else
		{
			pieces = new CommonHantoPiece[piecesSet.size()];
			piecesSet.toArray(pieces);
		}
		
		return pieces;
	}

	@Override
	public CommonHantoPiece[] getPiecesInHand(HantoPlayerColor color, HantoPieceType pieceType)
	{
		ArrayList<CommonHantoPiece> piecesOfType = new ArrayList<>();
		for(CommonHantoPiece piece : getPiecesInHand(color))
		{
			if(piece.getType().equals(pieceType))
			{
				piecesOfType.add(piece);
			}
		}
		
		CommonHantoPiece[] pieces = new CommonHantoPiece[piecesOfType.size()];
		piecesOfType.toArray(pieces);
		
		return pieces;
	}

	@Override
	public boolean isPieceInHand(CommonHantoPiece piece) 
	{
		Set<CommonHantoPiece> piecesInHand = playerHands.get(piece.getColor());
		if(piecesInHand == null)
		{
			return true;
		}
		
		return !piecesInHand.contains(piece);
	}

	@Override
	public boolean isPieceOnBoard(CommonHantoPiece piece) 
	{
		return pieceLookup.containsKey(piece);
	}

	@Override
	public void pickupPiece(CommonHantoPiece piece) 
	{
		movePiece(piece, null);
	}
}