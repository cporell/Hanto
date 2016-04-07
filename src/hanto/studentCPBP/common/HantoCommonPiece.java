package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public abstract class HantoCommonPiece implements HantoPiece
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	
	/**
	 * Deafault constructor
	 * @param color the piece color
	 * @param type the piece type
	 */
	public HantoCommonPiece(HantoPlayerColor color, HantoPieceType type)
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
	
	public abstract IHantoMover createPlaceMover(HantoCoordinate at);
	
	public abstract IHantoMover createWalkMover(HantoCoordinate to);
}
