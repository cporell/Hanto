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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * IHantoGameState - data structure to hold the state of the current game.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public interface IHantoGameState
{	
	/**
	 * Adds a piece to be used in the game.
	 * @param piece The piece to add to the board
	 */
	void addPiece(CommonHantoPiece piece);
	
	/**
	 * Places a piece on the board at a location.
	 * @param piece The piece to place on the board.
	 * @param to The location to place the piece at.
	 */
	void placePiece(CommonHantoPiece piece, HantoCoordinate to);
	
	/**
	 * Picks up a piece and puts it back in the players hand.
	 * @param piece The piece to pickup.
	 */
	void pickupPiece(CommonHantoPiece piece);
	
	/**
	 * Moves a piece on the board
	 * @param piece The piece to move
	 * @param to The location to send the piece to.
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
	 * Get all pieces on the board that belong to a player.
	 * @param color The player to get the pieces for.
	 * @return An array of all pieces on the board that belong to that player.
	 */
	CommonHantoPiece[] getPieces(HantoPlayerColor color);
	
	/**
	 * Get all pieces on the board of a type.
	 * @param pieceType The type of piece.
	 * @return An array of all pieces on the board that are of a type.
	 */
	CommonHantoPiece[] getPieces(HantoPieceType pieceType);
	
	/**
	 * Get all pieces on the board of a type that belong to a player.
	 * @param color The player color to get pieces for.
	 * @param pieceType The type of piece.
	 * @return An array of pieces on the board that are of a type that belong to a player.
	 */
	CommonHantoPiece[] getPieces(HantoPlayerColor color, HantoPieceType pieceType);
	
	/**
	 * Gets all pieces in a players hand.
	 * @param color The players color.
	 * @return All pieces inside the players hand.
	 */
	CommonHantoPiece[] getPiecesInHand(HantoPlayerColor color);
	
	/**
	 * Gets all pieces of a type in the players hand.
	 * @param color The color of the player.
	 * @param pieceType The piece type.
	 * @return An array of all pieces of a type in the players hand.
	 */
	CommonHantoPiece[] getPiecesInHand(HantoPlayerColor color, HantoPieceType pieceType);
	
	/**
	 * Checks to see if a piece is on the board.
	 * @param piece The piece to check if its on the board.
	 * @return True if the piece is on the board.
	 */
	boolean isPieceOnBoard(CommonHantoPiece piece);
	
	/**
	 * Checks to see if a piece is in a hand.
	 * @param piece The piece to check if its in a hand.
	 * @return True if the piece is in a hand.
	 */
	boolean isPieceInHand(CommonHantoPiece piece);
	
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
	
	/**
	 * Tests to see if the game is over.
	 * @return True if the game is over.
	 */
	boolean isGameOver();
	
	/**
	 * Triggers a game over in the game state.
	 */
	void triggerGameOver();
	
	/**
	 * The number of moves made.
	 * @return The number of moves made in the game.
	 */
	int getMoveNumber();
	
	/**
	 * Called on the end of a move.
	 */
	void endMove();
	
	/**
	 * Creates a copy of the games state. This does not copy the piece structure, only the game state data.
	 * @return A copy of the game state.
	 */
	IHantoGameState copy();
}
