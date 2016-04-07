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
	
	/**
	 * Builds a GammaHanto game and sets the starting player appropriately.
	 * @param startColor Starting player
	 */
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
		
		IHantoMover mover;
		if(from == null)
		{
			HantoCommonPiece piece = pieceFactory.createPiece(pieceType, rules.getCurrentTurn());
			mover = piece.createPlaceMover(to);
		}
		else
		{
			HantoCommonPiece[] pieces = board.getPieces(from);
			HantoCommonPiece selectedPiece = null;
			for(HantoCommonPiece piece : pieces)
			{
				if(piece.getType() == pieceType)
				{
					selectedPiece = piece;
					break;
				}
			}
			
			if(selectedPiece == null)
			{
				throw new HantoException("The piece does not exist.");
			}
			
			mover = selectedPiece.createWalkMover(to);
		}
		
		rules.createMoverValidator(mover).check(board);
		
		boolean shouldContinue;
		MoveResult result;
		do
		{
			shouldContinue = mover.iterateMove(board);
			result = rules.checkBoard(board);
			if(result != MoveResult.OK)
			{
				break;
			}
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
