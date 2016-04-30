/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package hanto.studentCPBP.common.rules;

import java.util.HashSet;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.studentCPBP.common.CommonHantoPiece;
import hanto.studentCPBP.common.GenericHantoRuleCollection;
import hanto.studentCPBP.common.IHantoGameState;

/**
 * Rule that governs the valid piece types for a particular game of Hanto.
 * @author cgporell
 * @author bpeake
 *
 */
public class RuleValidPieceTypes implements GenericHantoRuleCollection.IRule
{
	private Set<HantoPieceType> allowedTypes = new HashSet<>();
	
	/**
	 * Constructor for RuleValidPieceTypes
	 * @param allowedTypes The piece types that are valid for this version of Hanto
	 */
	public RuleValidPieceTypes(HantoPieceType... allowedTypes)
	{
		for(HantoPieceType type : allowedTypes)
		{
			this.allowedTypes.add(type);
		}
	}
	
	@Override
	public void check(IHantoGameState state) throws HantoException
	{
		for(CommonHantoPiece piece : state.getPieces())
		{
			if(!allowedTypes.contains(piece.getType()))
			{
				throw new HantoException("Not a valid piece type.");
			}
		}
	}

	@Override
	public boolean isValidMoveLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

	@Override
	public boolean isValidSearchLocation(IHantoGameState state, HantoCoordinate location) 
	{
		return true;
	}

}
