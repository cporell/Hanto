/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentCPBP.delta;

import hanto.common.HantoPlayerColor;
import hanto.studentCPBP.common.CommonHantoBoard;
import hanto.studentCPBP.common.CommonHantoGame;
import hanto.studentCPBP.common.IHantoBoard;
import hanto.studentCPBP.common.IHantoPieceFactory;
import hanto.studentCPBP.common.IHantoRuleSet;

/**
 * Delta version of Hanto
 * @author cgporell
 * @author bpeake
 *
 */
public class DeltaHantoGame extends CommonHantoGame {

	public DeltaHantoGame(HantoPlayerColor startColor)
	{
		super(startColor);
	}

	@Override
	protected IHantoBoard CreateBoard() 
	{
		return new CommonHantoBoard();
	}

	@Override
	protected IHantoRuleSet CreateRuleSet(HantoPlayerColor startingColor) 
	{
		return new DeltaHantoRuleSet(startingColor);
	}

	@Override
	protected IHantoPieceFactory CreatePieceFactory() 
	{
		return new DeltaHantoPieceFactory();
	}

	@Override
	public String getPrintableBoard() 
	{
		return null;
	}

}
