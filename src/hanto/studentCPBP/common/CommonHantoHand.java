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
	 * @param The piece type to remove
	 */
	public void takePieceFromHand(HantoPieceType pieceType)
	{
		int pieceCount = hand.get(pieceType);
		pieceCount--;
		hand.put(pieceType, pieceCount);
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
