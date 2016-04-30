package hanto.studentCPBP.tournament;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.common.CommonHantoGame;

public class TournamentPlayerTest 
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		/**
		 * Builds a Hanto coord for testing
		 * @param x X-coord
		 * @param y Y-coord
		 */
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}

	@Before
	public void setUp() throws Exception 
	{
	}

	@Test
	public void test() 
	{
		TournamentRunner runner = new TournamentRunner(HantoGameID.ALPHA_HANTO, HantoPlayerColor.BLUE);
		
		runner.step();
		MoveResult result = runner.step();
		
		HantoPiece pieceAtOrigin = runner.getGame().getPieceAt(makeCoordinate(0, 0));
		
		assertEquals(MoveResult.DRAW, result);
		assertEquals(HantoPlayerColor.BLUE, pieceAtOrigin.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, pieceAtOrigin.getType());
		
		CommonHantoGame game = (CommonHantoGame) runner.getGame();
		HantoCoordinate[] adj = game.getState().getAdjacent(makeCoordinate(0, 0));
		
		HantoPiece otherPiece = null;
		for(HantoCoordinate coord : adj)
		{
			HantoPiece[] piecesAt  = game.getState().getPieces(coord);
			if(piecesAt.length > 0)
			{
				otherPiece = piecesAt[0];
				break;
			}
		}
		
		assertNotEquals(null, otherPiece);
		assertEquals(HantoPlayerColor.RED, otherPiece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, otherPiece.getType());
	}

	
	// Helper methods
	/**
	 * Make a test coord
	 * @param x X-coord
	 * @param y Y-coord
	 * @return The test coord
	 */
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
