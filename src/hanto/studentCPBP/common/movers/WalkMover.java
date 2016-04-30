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

package hanto.studentCPBP.common.movers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.IHantoMover;

/**
 * Mover for the "walk" behavior.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public class WalkMover implements IHantoMover
{
	private CommonHantoPiece piece;
	private HantoCoordinate to;
	private HantoCoordinate from;
	
	private ArrayList<HantoCoordinate> travledTo = new ArrayList<>();
	
	private Set<HantoCoordinate> blacklist = new HashSet<>();
	
	private boolean doNotHandleError;
	
	public HantoCoordinate getTargetLocation()
	{
		return new HantoCoordinateImpl(to);
	}
	
	public CommonHantoPiece getPiece()
	{
		return piece;
	}
	
	public HantoCoordinate getOriginLocation()
	{
		return new HantoCoordinateImpl(from);
	}
	
	/**
	 * Builds a WalkMover for the given piece and location
	 * @param piece The piece we are placing
	 * @param to The location we are placing at
	 * @param to 
	 */
	public WalkMover(CommonHantoPiece piece, HantoCoordinate from, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
		this.from = from;
	}
	
	@Override
	public boolean iterateMove(IHantoGameState state) throws HantoException 
	{
		//TODO: Remove
		if(from == null)
		{
			from = state.getPieceLocation(piece);
		}
		
		travledTo.add(state.getPieceLocation(piece));
		
		HantoCoordinate[] adj = state.getAdjacent(state.getPieceLocation(piece));
		
		HantoCoordinate closest = null;
		int closestDist = Integer.MAX_VALUE;
		for(HantoCoordinate coord : adj)
		{
			if(blacklist.contains(coord) || travledTo.contains(coord))
			{
				continue;
			}
			
			int deltaX = to.getX() - coord.getX();
			int deltaY = to.getY() - coord.getY();
			int delta = deltaY - deltaX;
			
			int dist = Math.abs(Math.max(deltaX, Math.max(deltaY, delta)));
			
			if(dist < closestDist)
			{
				closestDist = dist;
				closest = coord;
			}
		}
		
		if(closest == null)
		{
			if(travledTo.size() <= 1)
			{
				doNotHandleError = true;
				throw new HantoException("No valid paths for walk.");
			}
			else
			{
				throw new HantoException("Bad path.");
			}
		}
		
		state.movePiece(piece, closest);
		
		return !(closest.getX() == to.getX() && closest.getY() == to.getY());
	}

	@Override
	public boolean handleInvalidIteration(IHantoGameState state) 
	{
		if(doNotHandleError)
			return false;
		
		blacklist.add(state.getPieceLocation(piece));
		
		travledTo.clear();
		
		state.movePiece(piece, from);
		
		return true;
	}

}
