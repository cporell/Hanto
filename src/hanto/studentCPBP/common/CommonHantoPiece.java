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
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * HantoCommonPiece - Abstract class for a custom implementation of a Hanto Piece.
 * @author Benny Peake bpeake
 * @author Connor Porell cgporell
 */
public abstract class CommonHantoPiece implements HantoPiece
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	
	/**
	 * Deafault constructor
	 * @param color the piece color
	 * @param type the piece type
	 */
	public CommonHantoPiece(HantoPlayerColor color, HantoPieceType type)
	{
		this.color = color;
		this.type = type;
	}
	
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor()
	{
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType()
	{
		return type;
	}
	
	/**
	 * Create a PlaceMover, giving it the coordinate where a piece is being placed
	 * @param at Where we are placing the piece
	 * @return A PlaceMover for the desired location
	 */
	public abstract IHantoMover createPlaceMover(HantoCoordinate at);
	
	/**
	 * Create a WalkMover, giving it the coordinate we are moving to
	 * @param to Where the piece is attempting to move to
	 * @return A WalkMover for the desired location
	 */
	public abstract IHantoMover createWalkMover(HantoCoordinate to);
}
