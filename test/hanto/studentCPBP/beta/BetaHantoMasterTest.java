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
	
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
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
	
	@Test(expected = HantoException.class)	// 3
	public void cantPlacePieceOnOtherPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
	}
	
	@Test(expected = HantoException.class)	// 4
	public void pieceMustBeAdjacentToAnotherPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 2));
	}
	
	@Test //5
	public void bluePlacesSparrowOnFirstTurn() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0,0));
		assertEquals(MoveResult.OK, mr);
	}
	
	@Test // 6
	public void testBLUECannotPlaceTwoButterfliesPerPlayer() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1)); // R1
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // B2
		assertEquals(MoveResult.RED_WINS, mr);
	}
	
	@Test // 7
	public void testREDCannotPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0)); // B1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // B2
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(1,1)); // R2
	}
	
	//@Test // 8 
	public void testMustPlaceButterflyOnSixthMoveIfNotPlacedAlready() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B1
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // R1
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B2
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // R2
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B3
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // R3
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // B4
		game.makeMove(SPARROW, null, makeCoordinate(0,0)); // R4
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
