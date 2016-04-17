/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.common;

import java.util.HashMap;
import java.util.Map;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * Abstract class/template for Hanto Hands
 * Can be extended in individual game versions to have unique hands for each version
 * @author cgporell
 *
 */
public abstract class CommonHantoHand 
{
	private Map<HantoPieceType, Integer> hand;
	private boolean butterflyPlaced;
	private HantoPlayerColor playerColor;
	
	/**
	 * Make a CommonHanto hand
	 * @param color The player who owns this hand
	 */
	public CommonHantoHand(HantoPlayerColor color)
	{
		hand = new HashMap<HantoPieceType, Integer>();
		butterflyPlaced = false;
		playerColor = color;
	}
	
	/**
	 * Get this player's color
	 * @return The player color
	 */
	public HantoPlayerColor getPlayerColor()
	{
		return playerColor;
	}
	
	/**
	 * Get the butterfly placement status of this player
	 * @return If the butterfly is placed or not
	 */
	public boolean getButterflyPlaced()
	{
		return butterflyPlaced;
	}
	
	/**
	 * Remove a piece from the player's hand
	 * @param pieceType The piece type to remove
	 */
	public void takePieceFromHand(HantoPieceType pieceType)
	{
		int pieceCount = hand.get(pieceType);
		pieceCount--;
		hand.put(pieceType, pieceCount);
		if(pieceType == HantoPieceType.BUTTERFLY)
		{
			butterflyPlaced = true;
		}
	}
	
	/**
	 * Get the piece count of a particular type of piece
	 * @param pieceType The type of piece
	 * @return The amount of that type of piece
	 */
	public int getCountOfPieceInHand(HantoPieceType pieceType)
	{
		return hand.get(pieceType);
	}
	
	/**
	 * Set the number of a piece 
	 * Used for initializing the player
	 * @param pieceType The type of piece
	 * @param startingAmt The starting amount for this player's hand
	 */
	public void addPieceToHand(HantoPieceType pieceType, int startingAmt)
	{
		hand.put(pieceType, startingAmt);
	}
	
}
