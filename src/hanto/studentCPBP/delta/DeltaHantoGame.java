package hanto.studentCPBP.delta;

import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoBoard;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.IHantoRuleSet;

public class DeltaHantoGame extends CommonHantoGame {

	public DeltaHantoGame(HantoPlayerColor startColor)
	{
		super(startColor);
	}

	@Override
	protected IHantoBoard CreateBoard() 
	{
		return new CommonHantoBoard();
	}

	@Override
	protected IHantoRuleSet CreateRuleSet(HantoPlayerColor startingColor) 
	{
		return new DeltaHantoRuleSet(startingColor);
	}

	@Override
	protected IHantoPieceFactory CreatePieceFactory() 
	{
		return new DeltaHantoPieceFactory();
	}

	@Override
	public String getPrintableBoard() {
		return null;
	}

}
