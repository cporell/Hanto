/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common.rules;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

/**
 * Rule that checks if all pieces are contiguous.
 * @author cgporell
 * @author bpeake
 *
 */
public class RuleMustBeContinousBoard implements GenericHantoRuleCollection.IRule
{
	private void buildConnectivity(HantoCoordinate at, Set<HantoCoordinate> visited, IHantoGameState state)
	{
		visited.add(at);
		
		HantoCoordinate[] adjacent = state.getAdjacent(at);
		for(HantoCoordinate coord : adjacent)
		{
			if(visited.contains(coord))
			{
				continue;
			}
			
			if(state.getPieces(coord).length == 0)
			{
				continue;
			}
			
			buildConnectivity(coord, visited, state);
		}
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException 
	{
		HantoCoordinate[] takenLocations = state.getAllTakenLocations();
		if(takenLocations.length > 0)
		{
			Set<HantoCoordinate> visited = new HashSet<>();
			buildConnectivity(takenLocations[0], visited, state);
			
			if(visited.size() != takenLocations.length)
			{
				throw new HantoException("Not all pieces are connected.");
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		if(state.getPieces().length == 0)
		{
			return true;
		}
		
		if(state.getPieces(location).length > 0)
		{
			return true;
		}
		
		HantoCoordinate[] adj = state.getAdjacent(location);
		for(HantoCoordinate coord : adj)
		{
			if(state.getPieces(coord).length > 0)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		if(state.getPieces().length == 0)
		{
			return true;
		}
		
		if(state.getPieces(location).length > 0)
		{
			return true;
		}
		
		HantoCoordinate[] adj = state.getAdjacent(location);
		for(HantoCoordinate coord : adj)
		{
			if(state.getPieces(coord).length > 0)
			{
				return true;
			}
		}
		
		return false;
	}
}
