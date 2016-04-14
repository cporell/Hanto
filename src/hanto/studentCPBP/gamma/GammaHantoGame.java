/**
 * Gamma Hanto Game
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoBoard;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.IHantoRuleSet;

/**
 * Gamma version of hanto game.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 *
 */
public class GammaHantoGame extends CommonHantoGame
{

	public GammaHantoGame(HantoPlayerColor startColor)
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
		return new GammaHantoRuleSet(startingColor);
	}

	@Override
	protected IHantoPieceFactory CreatePieceFactory() 
	{
		return new GammaHantoPieceFactory();
	}

	@Override
	public String getPrintableBoard() 
	{
		return null;
	}
}
