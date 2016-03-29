/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.beta;

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
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
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
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
		
	}
	
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
	public void testBlueMustPlaceButterflyOnFourthMoveIfNotPlacedAlready() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1,-1)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(1,2)); // R4
		assertEquals(MoveResult.RED_WINS, mr);
	}
	
	/**
	 * Tests that not placing the Butterfly (for Red) on the 4th turn is a game over
	 * @throws HantoException
	 */
	@Test // 9 
	public void testRedMustPlaceButterflyOnFourthMoveIfNotPlacedAlready() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(SPARROW, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,1)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(-1,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(0,-1)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(1,1)); // R3
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1)); // B4
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1,2)); // R4
		assertEquals(MoveResult.BLUE_WINS, mr);
	}
	
	/**
	 * Test that a game ends in a draw after 6 turns if there is no winner.
	 * @throws HantoException
	 */
	@Test // 10
	public void testGameEndsInDrawAfterSixTurnsIfNoWinner() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(2,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(3,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(4,0)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(5,0)); // R3
		game.makeMove(SPARROW, null, makeCoordinate(6,0)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(7,0)); // R4
		game.makeMove(SPARROW, null, makeCoordinate(8,0)); // B5		
		game.makeMove(SPARROW, null, makeCoordinate(9,0)); // R5
		game.makeMove(SPARROW, null, makeCoordinate(10,0)); // B6		
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(11,0)); // R6
		assertEquals(MoveResult.DRAW, mr);
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
