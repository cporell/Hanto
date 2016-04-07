package hanto.studentCPBP.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
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
public class GammaHantoGame implements HantoGame
{
	private IHantoBoard board;
	private IHantoRuleSet rules;
	private IHantoPieceFactory pieceFactory;
	
	public GammaHantoGame(HantoPlayerColor startColor)
	{
		board = new GammaHantoBoard();
		rules = new GammaHantoRuleSet(startColor);
		pieceFactory = new GammaHantoPieceFactory();
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException 
	{
		rules.beginTurn();
		
		HantoCommonPiece piece = pieceFactory.createPiece(pieceType, rules.getCurrentTurn());
		IHantoMover mover = piece.createPlaceMover(to);
		
		boolean shouldContinue;
		MoveResult result;
		do
		{
			shouldContinue = mover.iterateMove(board);
			result = rules.checkBoard(board);
			if(result != MoveResult.OK)
				break;
		}
		while(shouldContinue);
		
		rules.endTurn();
		
		return result;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) 
	{
		if(board.getPieces(where).length > 0)
		{
			return board.getPieces(where)[0];
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getPrintableBoard() 
	{
		return null;
	}

}
