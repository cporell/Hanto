package hanto.studentCPBP.common.rules;

import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IEndCondition;

public class EndConditionStaticWinner implements GenericHantoRuleCollection.IEndCondition
{
	private HantoPlayerColor winner;
	
	public void setWinner(HantoPlayerColor winner)
	{
		this.winner = winner;
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState state) 
	{
		if(winner != null)
			return winner == HantoPlayerColor.BLUE ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
		
		return MoveResult.OK;
	}	
}
