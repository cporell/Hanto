/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.tournament;

import static hanto.common.HantoPlayerColor.BLUE;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.HantoGameFactory;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.CommonHantoPiece;

/**
 * Tournament Player test runs tests on the Hanto Bot
 * @author bpeake
 * @author cgporell
 *
 */
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

	private static HantoGameFactory factory;
	private HantoGame game;
	
	/**
	 * Initialize a HantoGameFactory
	 */
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	/**
	 * 
	 */
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
		
	}

	/**
	 * Simple test to make sure the tourney runner works
	 */
	@Test //1
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
	
	/**
	 * Test to make sure the bot places a butterfly by the 4th move
	 * @throws HantoException 
	 */
	@Test //2
	public void TestPlayerPlacesButterflyByFourthMove() throws HantoException
	{
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0,0)); 
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(1,0));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(-1,0)); 
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(2,0));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(-2,0)); 
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(3,0));
		
		TournamentRunner runner = new TournamentRunner(game, HantoGameID.EPSILON_HANTO, BLUE);
		
		MoveResult mr = runner.step();
		
		CommonHantoGame boardState = (CommonHantoGame) runner.getGame();
		CommonHantoPiece[] BlueButterflies = boardState.getState().getPieces(BLUE, HantoPieceType.BUTTERFLY);
		assertEquals(1, BlueButterflies.length);
		
		assertEquals(MoveResult.OK, mr);		
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
