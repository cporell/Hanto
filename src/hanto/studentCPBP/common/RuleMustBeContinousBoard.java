package hanto.studentCPBP.common;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

public class RuleMustBeContinousBoard implements GenericHantoRuleCollection.IRule
{
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
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
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
}
