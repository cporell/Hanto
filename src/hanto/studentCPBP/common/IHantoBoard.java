/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;

/**
 * IHantoBoard - data structure to hold the game board and get pieces, locations, and adjacency.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoBoard
{	
	/**
	 * Adds a piece to the board
	 * @param piece
	 * @param at
	 */
	void addPiece(CommonHantoPiece piece, HantoCoordinate at);
	
	/**
	 * Moves a piece on the board
	 * @param piece
	 * @param to
	 */
	void movePiece(CommonHantoPiece piece, HantoCoordinate to);
	
	/**
	 * Get all pieces at a location. 
	 * @param at The desired location.
	 * @return All pieces at a location on the board. We return an array since our plan is moving
	 * a piece first, then seeing if it is an acceptable move.
	 */
	CommonHantoPiece[] getPieces(HantoCoordinate at);
	
	/**
	 * Get all pieces on the board.
	 * @return An array of all pieces on the board.
	 */
	CommonHantoPiece[] getPieces();
	
	/**
	 * Given a piece, look up its location.
	 * @param piece The desired piece
	 * @return Its location
	 */
	HantoCoordinate getPieceLocation(CommonHantoPiece piece);
	
	/**
	 * Returns all occupied locations on the board
	 * @return An array of all taken locations on the board.
	 */
	HantoCoordinate[] getAllTakenLocations();
	
	/**
	 * Given a location, returns all pieces adjacent to that spot.
	 * @param at The desired location
	 * @return Array of adjacent locations holding a piece.
	 */
	HantoCoordinate[] getAdjacent(HantoCoordinate at);
}
