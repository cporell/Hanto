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

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
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
import hanto.studentCPBP.common.CommonHantoGameState;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.IHantoGameState;
import hanto.studentCPBP.epsilon.EpsilonHantoPieceFactory;
import hanto.tournament.HantoMoveRecord;

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
	public void testCanPlaySimpleGame() 
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
		
		TournamentRunner runner = new TournamentRunner(game, HantoGameID.EPSILON_HANTO);
		
		MoveResult mr = runner.step();
		
		CommonHantoGame boardState = (CommonHantoGame) runner.getGame();
		CommonHantoPiece[] BlueButterflies = boardState.getState().getPieces(BLUE, HantoPieceType.BUTTERFLY);
		assertEquals(1, BlueButterflies.length);
		
		assertEquals(MoveResult.OK, mr);		
	}

	/**
	 * Tests that the bot will resign when there are no moves.
	 * @throws HantoException
	 */
	@Test
	public void testForfitWhenNoTurns() throws HantoException
	{
		CommonHantoGameState testerGameState = new CommonHantoGameState();
		EpsilonHantoPieceFactory factory = new EpsilonHantoPieceFactory();
		
		//Inject a testing game state into the game with less pieces to allow for a more compact test
		testerGameState.addPiece(factory.createPiece(HantoPieceType.BUTTERFLY, BLUE));
		testerGameState.addPiece(factory.createPiece(HantoPieceType.BUTTERFLY, RED));
		testerGameState.addPiece(factory.createPiece(HantoPieceType.SPARROW, RED));
		
		CommonHantoGame cgame = (CommonHantoGame)game;
		cgame.setState(testerGameState);
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));//B1
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1));//R1
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 0));//B
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(1, -1));//R
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(-1, 0), makeCoordinate(0, 0));//B
		game.makeMove(HantoPieceType.BUTTERFLY, makeCoordinate(1, -1), makeCoordinate(1, 0));//R
		
		TournamentRunner runner = new TournamentRunner(game, HantoGameID.EPSILON_HANTO);
		
		MoveResult mr = runner.step();
		
		assertEquals(MoveResult.RED_WINS, mr);
	}
	
	/**
	 * Tests that there is no movement before the butterfly is placed.
	 * @throws HantoException
	 */
	@Test
	public void testNoMoveBeforeButterly() throws HantoException
	{
		TournamentRunner runner = new TournamentRunner(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE);
		
		for(int i = 0; i < 4; i++)
		{
			runner.step();
			
			HantoMoveRecord lastMove = runner.getLastMove();
			
			if(lastMove.getPiece() == HantoPieceType.BUTTERFLY)
			{
				break;
			}
			else
			{
				assertEquals(null, lastMove.getFrom());
			}
		}
	}
	
	/**
	 * Tests that the average time to compute a move is under a second.
	 * @throws HantoException
	 */
	@Test
	public void testMoveIsLessThanOneSecond() throws HantoException
	{
		TournamentRunner runner = new TournamentRunner(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE);
		
		long timePassedSum = 0;
		int i = 0;
		for(; i < 20; i++)
		{
			long start = System.currentTimeMillis();
			
			MoveResult result = runner.step();
			
			long end = System.currentTimeMillis();
			
			timePassedSum += (end - start);
			
			if(result != MoveResult.OK)
				break;
		}
		
		long avg = timePassedSum / (i + 1);
		
		assertTrue(avg < 1000);
	}
	
	/**
	 * Tests that if there is a move that can be taken that will win it will be taken.
	 * @throws HantoException
	 */
	@Test
	public void testTakeWin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(CRAB, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(CRAB, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		game.makeMove(CRAB, null, makeCoordinate(0, 2));// R4
		
		TournamentRunner runner = new TournamentRunner(game, HantoGameID.EPSILON_HANTO);
		MoveResult result = runner.step();
		
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	/**
	 * Tests that given the same situation with a lose nearby we prevent the a loss.
	 * @throws HantoException
	 */
	@Test
	public void testStopOtherWinning() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(CRAB, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(CRAB, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		
		TournamentRunner runner = new TournamentRunner(game, HantoGameID.EPSILON_HANTO);
		runner.step();
		MoveResult result = runner.step();
		
		assertNotEquals(MoveResult.BLUE_WINS, result);
	}
	
	@Test
	public void testGiveUpInInvalidGame() throws HantoException
	{
		HantoPlayer player = new HantoPlayer();
		player.startGame(HantoGameID.ZETA_HANTO, RED, false);
		
		HantoMoveRecord result = player.makeMove(null);
		
		assertEquals(null, result.getFrom());
		assertEquals(null, result.getTo());
		assertEquals(null, result.getPiece());
	}
	
	@Test
	public void testGiveUpOnInvalidEnemyMove() throws HantoException
	{
		HantoMoveRecord invalidMove = new HantoMoveRecord(CRAB, null, makeCoordinate(0, -1000));
		
		HantoPlayer player = new HantoPlayer();
		player.startGame(HantoGameID.ALPHA_HANTO, BLUE, false);
		HantoMoveRecord result = player.makeMove(invalidMove);
		
		assertEquals(null, result.getFrom());
		assertEquals(null, result.getTo());
		assertEquals(null, result.getPiece());
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
