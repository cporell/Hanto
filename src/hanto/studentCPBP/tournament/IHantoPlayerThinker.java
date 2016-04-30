package hanto.studentCPBP.tournament;

import hanto.studentCPBP.common.CommonHantoGame;
import hanto.tournament.HantoMoveRecord;

public interface IHantoPlayerThinker 
{
	HantoMoveRecord selectMove(CommonHantoGame game);
}
