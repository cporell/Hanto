package hanto.studentCPBP.gamma;

import java.util.Arrays;
import java.util.HashSet;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.studentCPBP.common.HantoCommonPiece;
import hanto.studentCPBP.common.HantoCoordinateImpl;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoMover;

public class WalkMover implements IHantoMover
{
	private HantoCommonPiece piece;
	private HantoCoordinate to;
	
	public HantoCoordinate getTargetLocation()
	{
		return to;
	}
	
	public HantoCommonPiece getPiece()
	{
		return piece;
	}
	
	public WalkMover(HantoCommonPiece piece, HantoCoordinate to) 
	{
		this.piece = piece;
		this.to = to; 
	}
	
	@Override
	public boolean iterateMove(IHantoBoard board) throws HantoException 
	{
		board.movePiece(piece, to);
		
		return false;
	}

}
