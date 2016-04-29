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

import hanto.common.HantoException;

/**
 * Default Hanto Mover Validator is used as the constructed MoverValidator
 * if no other type of Validator is applicable
 * @author cgporell
 * @author bpeake
 *
 */
public class DefaultHantoMoverValidator implements IHantoMoverValidator 
{
	@Override
	public void checkIteration(IHantoGameState state) throws HantoException 
	{
	}
}
