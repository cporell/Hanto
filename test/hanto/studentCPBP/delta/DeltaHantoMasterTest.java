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

package hanto.studentCPBP.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPlayerColor.*;
import hanto.common.*;
import hanto.studentCPBP.HantoGameFactory;
import static org.junit.Assert.*;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 * @author Connor Porell (cgporell)
 * @author Benny Peake (bpeake)
 */
public class DeltaHantoMasterTest
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
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
		
	}

	//===============DELTA HANTO TESTS================================================
	/**
	 *  Tests that a player cannot place more than 4 sparrows
	 * @throws HantoException
	 */
	@Test //1
	(expected = HantoException.class)
	public void TestPlayerCannotPlaceMoreThanFourSparrows() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -1)); //b1 sparrow
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -2)); // b2 sparrow
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, 2), makeCoordinate(1,1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -3)); // b3 sparrow
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(1, 1), makeCoordinate(0,2));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -4)); // b4 sparrow
		game.makeMove(HantoPieceType.SPARROW, makeCoordinate(0, 2), makeCoordinate(1,1));		
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -5)); // Blue attempts 5th sparrow
	}
	
	/**
	 *  Tests that a player cannot place more than 4 crabs
	 * @throws HantoException
	 */
	@Test //2
	(expected = HantoException.class)
	public void TestPlayerCannotPlaceMoreThanFourCrabs() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -1)); //b1 crab
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, 2));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -2)); // b2 crab
		game.makeMove(HantoPieceType.CRAB, makeCoordinate(0, 2), makeCoordinate(1,1));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -3)); // b3 crab
		game.makeMove(HantoPieceType.CRAB, makeCoordinate(1, 1), makeCoordinate(0,2));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -4)); // b4 crab
		game.makeMove(HantoPieceType.CRAB, makeCoordinate(0, 2), makeCoordinate(1,1));		
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -5)); // Blue attempts 5th crab	
	}
	
	/**
	 * Tests that a player cannot place more than one butterfly
	 * @throws HantoException
	 */
	@Test //3
	(expected = HantoException.class)
	public void TestPlayerCannotPlaceMoreThanOneButterfly() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	/**
	 * Test that placing a Crab is valid
	 * @throws HantoException
	 */
	@Test //4
	public void testCanPlaceCrab() throws HantoException
	{
		MoveResult result = game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, 0));
		assertEquals(HantoPieceType.CRAB, game.getPieceAt(makeCoordinate(0, 0)).getType());	
		assertEquals(MoveResult.OK, result);
	}
	
	/**
	 * Test a Crab can move 1 space
	 * @throws HantoException
	 */
	@Test //5
	public void testCanMoveCrabOneSpace() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(1, 0));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -2));
		MoveResult result = game.makeMove(HantoPieceType.CRAB, makeCoordinate(1, 0), makeCoordinate(0, 1));
		
		assertEquals(HantoPieceType.CRAB, game.getPieceAt(makeCoordinate(0, 1)).getType());	
		assertEquals(MoveResult.OK, result);
	}
	
	/**
	 * Test a Crab can move 2 spaces
	 * @throws HantoException
	 */
	@Test // 6
	public void testCanMoveCrabTwoSpaces() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(1, 0));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -2));
		MoveResult result = game.makeMove(HantoPieceType.CRAB, makeCoordinate(1, 0), makeCoordinate(1, -2));
		
		assertEquals(HantoPieceType.CRAB, game.getPieceAt(makeCoordinate(1, -2)).getType());	
		assertEquals(MoveResult.OK, result);
	}
	
	/**
	 * Test a Crab cannot move more than 3 spaces
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //7
	public void testCannotMoveFourSpaces() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(HantoPieceType.SPARROW, null, makeCoordinate(0, -2));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(1, 1));
		game.makeMove(HantoPieceType.CRAB, null, makeCoordinate(0, -3));
		game.makeMove(HantoPieceType.CRAB, makeCoordinate(1, 1), makeCoordinate(1, -3));
	}
	
	/**
	 * Test cannot make an invalid piece
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //8
	public void testCreatingInvalidPiece() throws HantoException
	{
		game.makeMove(HantoPieceType.HORSE, null, makeCoordinate(0, 0));
	}
	
	/**
	 * Test that Blue can resign successfully
	 * @throws HantoException
	 */
	@Test //9
	public void testBlueCanResign() throws HantoException
	{
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
		MoveResult result = game.makeMove(null, null, null);
		
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	/**
	 * Test that Red can resign successfully
	 * @throws HantoException
	 */
	@Test //10
	public void testRedCanResign() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, HantoPlayerColor.RED);
		
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(HantoPieceType.BUTTERFLY, null, makeCoordinate(0, -1));
		MoveResult result = game.makeMove(null, null, null);
		
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	/**
	 * Test that Sparrows can Fly over pieces
	 * @throws HantoException
	 */
	@Test //11
	public void testSparrowCanFlyThroughPieces() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(CRAB, null, makeCoordinate(1, -1)); //b3
		game.makeMove(CRAB, null, makeCoordinate(-2, 2)); //r3
		game.makeMove(SPARROW, makeCoordinate(-1, 0), makeCoordinate(-1, 1)); //b4
		game.makeMove(SPARROW, null, makeCoordinate(-3, 2)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); //b5
		game.makeMove(SPARROW, makeCoordinate(-3,  2), makeCoordinate(-2, 1)); //r5
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(-1, 1), makeCoordinate(-1, 0)); //b6
		
		assertEquals(MoveResult.OK, result);
		assertEquals(SPARROW, game.getPieceAt(makeCoordinate(-1, 0)).getType());
		assertEquals(BLUE, game.getPieceAt(makeCoordinate(-1, 0)).getColor());
	}
	
	/**
	 * Test that a Sparrow cannot land on an occupied spot
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //12
	public void testSparrowCannotFlyOccupiedSpot() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(SPARROW,  makeCoordinate(-1, 0), makeCoordinate(0, 0));
	}
	
	/**
	 * Test that a Fly move is only legal if it doesn't break continuity of pieces
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //13
	public void testSparrowCannotBreakContinuity() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(SPARROW,  makeCoordinate(-1, 0), makeCoordinate(Integer.MAX_VALUE, 0));
	}
	
	/**
	 * Test that a Sparrow cannot fly to the same spot it started from
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //14
	public void testSparrowCannotFlyToSameLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(SPARROW,  makeCoordinate(-1, 0), makeCoordinate(-1, 0));
	}

	/**
	 * Test that a Sparrow can fly any amount of spaces (i.e. more than 1)
	 * @throws HantoException
	 */
	@Test //15
	public void testSparrowCanFlyAnyAmountOfSpaces() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1)); //r1
		game.makeMove(CRAB, null, makeCoordinate(0, 1)); //b2
		game.makeMove(CRAB, null, makeCoordinate(0, -2)); //r2
		game.makeMove(CRAB, null, makeCoordinate(0, 2)); //b3
		game.makeMove(CRAB, null, makeCoordinate(0, -3)); //r3
		game.makeMove(CRAB, null, makeCoordinate(0, 3)); //b4
		game.makeMove(CRAB, null, makeCoordinate(0, -4)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(0, 4)); //b5
		game.makeMove(SPARROW, null, makeCoordinate(0, -5)); //r5
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(0, 4), makeCoordinate(0, -6)); //b6
		
		assertEquals(MoveResult.OK, result);
		assertEquals(SPARROW, game.getPieceAt(makeCoordinate(0, -6)).getType());
		assertEquals(BLUE, game.getPieceAt(makeCoordinate(0, -6)).getColor());
	}
	
	/**
	 * Test that a Sparrow can Fly over gaps
	 * @throws HantoException
	 */
	@Test //16
	public void testSparrowCanFlyOverGaps() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(CRAB, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(CRAB, null, makeCoordinate(0, 2)); //r2
		game.makeMove(CRAB, null, makeCoordinate(-2, 0)); //b3
		game.makeMove(CRAB, null, makeCoordinate(0, 3)); //r3
		game.makeMove(CRAB, null, makeCoordinate(-3, 0)); //b4
		game.makeMove(CRAB, null, makeCoordinate(0, 4)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(-4, 0)); //b5
		game.makeMove(SPARROW, null, makeCoordinate(0, 5)); //r5
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(-4, 0), makeCoordinate(0, 6)); //b6
		
		assertEquals(MoveResult.OK, result);
		assertEquals(SPARROW, game.getPieceAt(makeCoordinate(0, 6)).getType());
		assertEquals(BLUE, game.getPieceAt(makeCoordinate(0, 6)).getColor());
	}
	
	//=================================LEGACY TESTS==============================================
	
	/**
	 * Test that the first piece MUST be placed at origin
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class) //17
	public void pieceMustStartAtOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(10, 80));
	}
	
	/**
	 * Test that we can move a piece by walking.
	 * @throws HantoException
	 */
	@Test // 18
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
	@Test(expected = HantoException.class) //19
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
	@Test(expected = HantoException.class) //20
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
	@Test(expected = HantoException.class) //21
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
	@Test(expected = HantoException.class) //22
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
	@Test(expected = HantoException.class) //23
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
	@Test(expected = HantoException.class) //24
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
	@Test(expected = HantoException.class) //25
	public void piecesMustBePlacedNextToSameColor() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	}
	
	/**
	 * Test that we cannot place pieces next to the opponent
	 */
	@Test(expected = HantoException.class) //26
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
	@Test(expected = HantoException.class) //27
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
	@Test(expected = HantoException.class) //28
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
	@Test(expected = HantoException.class) //29
	public void cannotMoveThroughPieces() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); //b1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); //r1
		game.makeMove(CRAB, null, makeCoordinate(-1, 0)); //b2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); //r2
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); //b3
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); //r3
		game.makeMove(CRAB, makeCoordinate(-1, 0), makeCoordinate(-1, 1)); //b4
		game.makeMove(CRAB, null, makeCoordinate(-3, 2)); //r4
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); //b5
		game.makeMove(CRAB, makeCoordinate(-3,  2), makeCoordinate(-2, 1)); //r5
		game.makeMove(CRAB, makeCoordinate(-1, 1), makeCoordinate(-1, 0)); //b6
	}
	
	/**
	 * Test that we cannot play after someone wins
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 30
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
	 * Test that the Red player wins if the Blue Butterfly is surrounded
	 * @throws HantoException
	 */
	@Test //31
	public void testGameEndsIfBlueButterflySurrounded() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,2)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(1,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1, 1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));// R4
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
		
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	/**
	 * Test that Blue wins if the Red Butterfly is surrounded
	 * @throws HantoException
	 */
	@Test //32
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
		MoveResult result = game.makeMove(SPARROW, makeCoordinate(1, -1), makeCoordinate(1, 0));
		
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	/**
	 * Test that the game ends with a Draw if both butterflies are surrounded at once
	 * @throws HantoException
	 */
	@Test //33
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
	
	//=============================================================================================
	
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
