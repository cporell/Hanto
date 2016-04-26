package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public class EndConditionPlayerSurrounded implements GenericHantoRuleCollection.IEndCondition
{
	private HantoPlayerColor player;
	
	public EndConditionPlayerSurrounded(HantoPlayerColor player)
	{
		this.player = player;
	}
	
	@Override
	public MoveResult checkForResult(IHantoBoard board) 
	{
		CommonHantoPiece butterfly = getPlayerButterfly(board);
		if(butterfly == null)
			return MoveResult.OK;
		
		for(HantoCoordinate coord : board.getAdjacent(board.getPieceLocation(butterfly)))
		{
			if(board.getPieces(coord).length != 1)
				return MoveResult.OK;
		}
		
		return player == HantoPlayerColor.RED ? MoveResult.BLUE_WINS : MoveResult.RED_WINS;
	}

	private CommonHantoPiece getPlayerButterfly(IHantoBoard board)
	{
		for(CommonHantoPiece piece : board.getPieces())
		{
			if(piece.getColor() == player && piece.getType() == HantoPieceType.BUTTERFLY)
			{
				return piece;
			}
		}
		
		return null;
	}
}
