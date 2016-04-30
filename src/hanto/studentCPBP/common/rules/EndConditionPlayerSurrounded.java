package hanto.studentCPBP.common.rules;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.common.GenericHantoRuleCollection.IEndCondition;

public class EndConditionPlayerSurrounded implements GenericHantoRuleCollection.IEndCondition
{
	private HantoPlayerColor player;
	
	public EndConditionPlayerSurrounded(HantoPlayerColor player)
	{
		this.player = player;
	}
	
	@Override
	public MoveResult checkForResult(IHantoGameState state) 
	{
		CommonHantoPiece butterfly = getPlayerButterfly(state);
		if(butterfly == null)
			return MoveResult.OK;
		
		for(HantoCoordinate coord : state.getAdjacent(state.getPieceLocation(butterfly)))
		{
			if(state.getPieces(coord).length != 1)
				return MoveResult.OK;
		}
		
		return player == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
	}

	private CommonHantoPiece getPlayerButterfly(IHantoGameState state)
	{
		CommonHantoPiece[] butterflies = state.getPieces(player, HantoPieceType.BUTTERFLY);
		
		return butterflies.length == 0 ? null : butterflies[0];
	}
}
