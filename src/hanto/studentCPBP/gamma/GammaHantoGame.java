/**
 * Gamma Hanto Game
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
package hanto.studentCPBP.gamma;

import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoGameState;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.IHantoGameState;
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
	protected IHantoGameState CreateGameState()
	{
		return new CommonHantoGameState();
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
