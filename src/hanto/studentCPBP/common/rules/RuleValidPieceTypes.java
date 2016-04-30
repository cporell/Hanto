package hanto.studentCPBP.common.rules;

import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;

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
	public void check(IHantoGameState state) throws HantoException
	{
		for(CommonHantoPiece piece : state.getPieces())
		{
			if(!allowedTypes.contains(piece.getType()))
			{
				throw new HantoException("Not a valid piece type.");
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

}
