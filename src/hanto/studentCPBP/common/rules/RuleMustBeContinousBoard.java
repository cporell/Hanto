package hanto.studentCPBP.common.rules;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IRule;

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
}
