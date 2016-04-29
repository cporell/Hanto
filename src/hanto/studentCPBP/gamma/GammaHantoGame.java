/**
 * Gamma Hanto Game
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
package hanto.studentCPBP.gamma;

import hanto.common.HantoPieceType;
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
	protected IHantoGameState CreateGameState(IHantoPieceFactory pieceFactory)
	{
		CommonHantoGameState state = new CommonHantoGameState();
		
		state.addPiece(pieceFactory.createPiece(HantoPieceType.BUTTERFLY, HantoPlayerColor.BLUE));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.BLUE));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.BLUE));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.BLUE));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.BLUE));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.BLUE));
		
		state.addPiece(pieceFactory.createPiece(HantoPieceType.BUTTERFLY, HantoPlayerColor.RED));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.RED));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.RED));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.RED));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.RED));
		state.addPiece(pieceFactory.createPiece(HantoPieceType.SPARROW, HantoPlayerColor.RED));
		
		return state;
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
