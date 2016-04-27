// $codepro.audit.disable
/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.gamma;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentCPBP.HantoGameFactory;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 * @author Connor Porell (cgporell)
 * @author Benny Peake (bpeake)
 */
public class GammaHantoMasterTest
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
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
		
	}

	//===============GAMMA HANTO TESTS================================================
	
	/**
	 * Test that we can move a piece by walking.
	 * @throws HantoException
	 */
	@Test // 1 
	public void pieceCanWalk() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, -1));
		
		assertEquals(null, game.getPieceAt(makeCoordinate(0, 0)));
		
		HantoPiece piece = game.getPieceAt(makeCoordinate(0, -1));
		assertEquals(BUTTERFLY, piece.getType());
		assertEquals(BLUE, piece.getColor());
	}
	
	/**
	 * Test that we can't attempt to move a piece that doesn't exist
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //2
	public void cannotMovePieceThatDoesntExist() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(2, -1));
	}
	
	/**
	 * Test that when we want to move a piece, we must refer to it by the appropriate
	 * type.
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //3
	public void cannotMovePieceOfWrongType() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(0, -1));
	}
	
	/**
	 * Test that a piece cannot walk to the spot from which it started. (aka walk in place)
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //4
	public void piecesCannotMoveToTheSameSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(0, 0));
	}
	
	/**
	 * Test that in Gamma, we can't walk more than one space.
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //5
	public void piecesCannotMoveMoreThanOneSpace() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(2, 0));
	}
	
	/**
	 * Test that all pieces are connected after a Walk.
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //6
	public void piecesMustBeContinousWhenMoving() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 0));
	}
	
	/**
	 * Place that we cannot move onto an already occupied space
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) // 7
	public void piecesCannotMoveOnTopOfEachother() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(1, -1));
	}
	
	/**
	 * Test that a piece can only be placed next pieces of the same color
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) // 8
	public void piecesMustBePlacedNextToSameColor() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	}
	
	/**
	 * Test that we cannot place pieces next to the opponent
	 */
	@Test(expected = HantoException.class) // 9
	public void piecesCannotBePlacedNextToOpponent() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
	}
	
	/**
	 * Test that we cannot move an opponent's piece
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) // 10
	public void cannotMoveOpponentsPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(1, -1), makeCoordinate(0, 1));
	}
	
	/**
	 * Test that we cannot move without a butterfly on the board
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) // 11
	public void cannotMoveIfNoButterfly() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, 0));
	}
	
	/**
	 * Test cannot Walk if there is not enough space.
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) // 12
	public void cannotMoveThroughPieces() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); //b3
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); //r3
		game.makeMove(SPARROW, makeCoordinate(-1, 0), makeCoordinate(-1, 1)); //b4
		game.makeMove(SPARROW, null, makeCoordinate(-3, 2)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); //b5
		game.makeMove(SPARROW, makeCoordinate(-3,  2), makeCoordinate(-2, 1)); //r5
		game.makeMove(SPARROW, makeCoordinate(-1, 1), makeCoordinate(-1, 0)); //b6
	}

	/**
	 * Test that a game ends in a draw after 20 turns if there is no winner.
	 */
	@Test // 13
	public void testGameEndsInDrawAfterTwentyTurnsIfNoWinner() throws HantoException
	{
		MoveResult mr = MoveResult.OK;
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R2
		for(int i = 4; i < 40; i +=4)
		{
			game.makeMove(SPARROW, makeCoordinate(0,-1), makeCoordinate(1,-1)); // B
			game.makeMove(SPARROW, makeCoordinate(1,1), makeCoordinate(0,2)); //R
			game.makeMove(SPARROW, makeCoordinate(1,-1), makeCoordinate(0,-1)); //B 
			mr = game.makeMove(SPARROW, makeCoordinate(0,2), makeCoordinate(1,1)); //R 	
		}
		assertEquals(MoveResult.DRAW, mr);
	}
	
	/**
	 * Test that we cannot play after someone wins
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 14
	public void testCannotPlayAfterWin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));// R4
		MoveResult result =game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
		
		assertEquals(MoveResult.BLUE_WINS, result);
		game.makeMove(SPARROW, null, makeCoordinate(0,3));
	}
	
	/**
	 * Test cannot be played after the time limit is reached.
	 */
	@Test // 15
	(expected = HantoException.class)
	public void testCannotPlayGameAfterTimeLimitReached() throws HantoException
	{
		MoveResult mr = MoveResult.OK;
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R2
		for(int i = 4; i < 40; i +=4)
		{
			game.makeMove(SPARROW, makeCoordinate(0,-1), makeCoordinate(1,-1)); // B3
			game.makeMove(SPARROW, makeCoordinate(1,1), makeCoordinate(0,2)); //
			game.makeMove(SPARROW, makeCoordinate(1,-1), makeCoordinate(0,-1)); // 
			mr = game.makeMove(SPARROW, makeCoordinate(0,2), makeCoordinate(1,1)); // 	
		}
		assertEquals(MoveResult.DRAW, mr);
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
	}
	
	/**
	 * Test cannot place more than 5 sparrows
	 */
	@Test // 16
	(expected = HantoException.class)
	public void testCannotPlaceMoreThanFiveSparrows() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(0, 2)); //r2
		game.makeMove(SPARROW, null, makeCoordinate(0, -2)); //b3
		game.makeMove(SPARROW, null, makeCoordinate(0, 3)); //r3
		game.makeMove(SPARROW, null, makeCoordinate(0, -3)); //b4
		game.makeMove(SPARROW, null, makeCoordinate(0, 4)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(0, -4)); //b5
		game.makeMove(SPARROW, null, makeCoordinate(0, 5)); //r5
		game.makeMove(SPARROW, null, makeCoordinate(0, -5)); //b6
		game.makeMove(SPARROW, null, makeCoordinate(0, 6)); //r6
		game.makeMove(SPARROW, null, makeCoordinate(0, -6)); //b7
		
	}
	
	//===============BETA HANTO TESTS================================================
	/**
	 * Test that Blue can place their Butterfly at origin
	 * @throws HantoException
	 */
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Test that Red can place their Butterly after Blue
	 * @throws HantoException
	 */
	@Test	// 2
	public void redPlacesButterflyNext() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult secondMove = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(OK, secondMove);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	/**
	 * Test that a piece can't be placed on another piece
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)	// 3
	public void cantPlacePieceOnOtherPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	/**
	 * Test that pieces, when placed, must be adjacent to another pieces
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)	// 4
	public void pieceMustBeAdjacentToAnotherPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
	}
	
	/**
	 * Test that Blue can place a Sparrow on the first turn
	 * @throws HantoException
	 */
	@Test //5
	public void bluePlacesSparrowOnFirstTurn() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0,0));
		assertEquals(MoveResult.OK, mr);
	}
	
	/**
	 * Test that Blue cannot place 2 Butterflies
	 * @throws HantoException
	 */
	@Test // 6
	(expected = HantoException.class)
	public void testBLUECannotPlaceTwoButterfliesPerPlayer() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // B2
		assertEquals(MoveResult.RED_WINS, mr);
	}
	
	/**
	 * Test that Red cannt place 2 Butterflies
	 * @throws HantoException
	 */
	@Test // 7
	(expected = HantoException.class)
	public void testREDCannotPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // B2
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(1,1)); // R2
		assertEquals(MoveResult.BLUE_WINS, mr);
	}
	
	/**
	 * Test that not placing the Butterfly (for Blue) on the 4th turn is a game over
	 * @throws HantoException
	 */
	@Test // 8 
	(expected = HantoException.class)
	public void testBlueMustPlaceButterflyOnFourthMoveIfNotPlacedAlready() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1)); // B4
	}
	
	/**
	 * Tests that not placing the Butterfly (for Red) on the 4th turn is a game over
	 * @throws HantoException
	 */
	@Test // 9 
	(expected = HantoException.class)
	public void testRedMustPlaceButterflyOnFourthMoveIfNotPlacedAlready() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(SPARROW, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(1,2)); // R4
	}
	
	
	// Test 10 (testGameEndsInDrawAfterSixTurnsInNoWinner) was moved to Gamma Hanto set of tests
	// to use as a base for the more fleshed out testGameEndsInDrawAfterTwentyTurnsIfNoWinner
	
	// Test 11 (testGameDoesNotAllowMovesAfterEnd) was moved tot he Gamma Hanto set of tests to use as a base
	// for a 20-turn game (as opposed to the 6-turn game of Beta)
	
	
	/**
	 * Test that we can only place Butterflies/Sparrows in Beta Hanto
	 * @throws HantoException
	 */
	@Test //12
	(expected = HantoException.class)
	public void testCannotPlaceInvalidPiece() throws HantoException
	{
		MoveResult result = game.makeMove(HORSE, null, makeCoordinate(0,0)); // B1
	}
	
	/**
	 * Test that the Red player wins if the Blue Butterfly is surrounded
	 * @throws HantoException
	 */
	@Test //13
	public void testGameEndsIfBlueButterflySurrounded() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.GAMMA_HANTO, RED);
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));// R4
		MoveResult result =game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
		
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	/**
	 * Test that Blue wins if the Red Butterfly is surrounded
	 * @throws HantoException
	 */
	@Test //14
	public void testGameEndsIfRedButterflySurrounded() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));// R4
		MoveResult result =game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
		
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	/**
	 * Test that the first piece can only be placed at 0,0
	 * @throws HantoException
	 */
	@Test //15
	(expected = HantoException.class)
	public void testMustStartAtOrigin() throws HantoException
	{
		MoveResult result = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	/**
	 * Test that the game ends with a Draw if both butterflies are surrounded at once
	 * @throws HantoException
	 */
	@Test //16
	public void testGameEndsOnDraw() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // r1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1,2)); //r2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); //b3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); //r3
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); //b4
		game.makeMove(SPARROW, null, makeCoordinate(0,2)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(2,-1)); //b5
		game.makeMove(SPARROW, null, makeCoordinate(-2,2)); //r5
		game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 0));
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(-2, 2), makeCoordinate(-1, 1)); //r6
		
		assertEquals(MoveResult.DRAW, result);
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
