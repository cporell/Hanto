package hanto.studentCPBP.common;

import hanto.common.HantoCoordinate;
import hanto.tournament.HantoMoveRecord;

/**
 * Used to generate valid piece move locations from a list of locations.
 * @author Benny Peake
 * @author Connor Porell
 *
 */
public interface IHantoPieceMoveGenerator 
{
	/**
	 * Gets a list of valid moves for a piece.
	 * @param validLocations The valid locations on the board.
	 * @param state The state of the game world.
	 * @return An array of moves that can be made.
	 */
	HantoMoveRecord[] getValidMovesFrom(HantoCoordinate[] validLocations, IHantoGameState state);
}
