package hanto.studentCPBP.common;

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public class EndConditionGetWinner implements GenericHantoRuleCollection.IEndCondition
{
	private EndConditionPlayerSurrounded blueSurrounded, redSurrounded;
	
	public EndConditionGetWinner()
	{
		blueSurrounded = new EndConditionPlayerSurrounded(HantoPlayerColor.BLUE);
		redSurrounded = new EndConditionPlayerSurrounded(HantoPlayerColor.RED);
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState board) 
	{
		MoveResult blueResult = blueSurrounded.checkForResult(board);
		MoveResult redResult = redSurrounded.checkForResult(board);
		
		if(blueResult != MoveResult.OK && redResult != MoveResult.OK)
		{
			return MoveResult.DRAW;
		}
		else if(blueResult != MoveResult.OK)
		{
			return blueResult;
		}
		else if(redResult != MoveResult.OK)
		{
			return redResult;
		}
		
		return MoveResult.OK;
	}

}
