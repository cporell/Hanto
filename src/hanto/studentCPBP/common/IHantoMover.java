package hanto.studentCPBP.common;

import hanto.common.HantoException;

public interface IHantoMover
{
	boolean iterateMove(IHantoBoard board) throws HantoException;
}
