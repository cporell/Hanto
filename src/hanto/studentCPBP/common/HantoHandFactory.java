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

import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.beta.BetaHantoHand;
import hanto.studentCPBP.delta.DeltaHantoHand;
import hanto.studentCPBP.gamma.GammaHantoHand;

/**
 * Factory class for building Hanto players
 * @author cgporell
 *
 */
public class HantoHandFactory
{
	private static final HantoHandFactory instance = new HantoHandFactory();
	
	/**
	 * Default private descriptor.
	 */
	private HantoHandFactory()
	{
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static HantoHandFactory getInstance()
	{
		return instance;
	}
	
	/**
	 * Factory method that returns the appropriately configured Hanto game.
	 * @param gameId the version desired.
	 * @param color the player color that moves first
	 * @return the game instance
	 */
	public  CommonHantoHand makeHantoHand(HantoGameID gameId, HantoPlayerColor color) {
		CommonHantoHand player = null;
		switch(gameId)
		{
		case BETA_HANTO:
			player = new BetaHantoHand(color);
			player.addPieceToHand(HantoPieceType.BUTTERFLY, 1);
			player.addPieceToHand(HantoPieceType.SPARROW, 5);
			break;
		case GAMMA_HANTO:
			player = new GammaHantoHand(color);
			player.addPieceToHand(HantoPieceType.BUTTERFLY, 1);
			player.addPieceToHand(HantoPieceType.SPARROW, 5);
			break;
		case DELTA_HANTO:
			player = new DeltaHantoHand(color);
			player.addPieceToHand(HantoPieceType.BUTTERFLY, 1);
			player.addPieceToHand(HantoPieceType.SPARROW, 4);
			player.addPieceToHand(HantoPieceType.CRAB, 4);
			break;
		}
		return player;
	}
}
