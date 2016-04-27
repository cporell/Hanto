package hanto.studentCPBP.common;

import java.util.HashSet;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;

public class RuleValidPieceTypes implements GenericHantoRuleCollection.IRule
{
	private HashSet<HantoPieceType> allowedTypes = new HashSet<>();
	
	public RuleValidPieceTypes(HantoPieceType... allowedTypes)
	{
		for(HantoPieceType type : allowedTypes)
		{
			this.allowedTypes.add(type);
		}
	}
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException
	{
		for(CommonHantoPiece piece : board.getPieces())
		{
			if(!allowedTypes.contains(piece.getType()))
			{
				throw new HantoException("Not a valid piece type.");
			}
		}
	}

}
