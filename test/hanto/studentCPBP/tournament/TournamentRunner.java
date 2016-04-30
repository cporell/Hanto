package hanto.studentCPBP.tournament;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentCPBP.HantoGameFactory;
import hanto.tournament.HantoMoveRecord;

public class TournamentRunner
{
	private HantoPlayer blue, red;
	private HantoGame game;
	
	private HantoPlayerColor playerTurn;
	private HantoMoveRecord lastMove;
	private MoveResult lastResult = MoveResult.OK;
	
	public TournamentRunner(HantoGameID gameId, HantoPlayerColor startingColor)
	{
		playerTurn = startingColor;
		
		blue = new HantoPlayer();
		red = new HantoPlayer();
		
		game = HantoGameFactory.getInstance().makeHantoGame(gameId);
		
		blue.startGame(gameId, HantoPlayerColor.BLUE, startingColor == HantoPlayerColor.BLUE);
		red.startGame(gameId, HantoPlayerColor.RED, startingColor == HantoPlayerColor.RED);
	}
	
	public TournamentRunner(HantoGame game, HantoGameID gameId, HantoPlayerColor startingColor)
	{
		playerTurn = startingColor;
		
		blue = new HantoPlayer();
		red = new HantoPlayer();
		
		this.game = game;
		
		blue.startGame(gameId, HantoPlayerColor.BLUE, startingColor == HantoPlayerColor.BLUE);
		red.startGame(gameId, HantoPlayerColor.RED, startingColor == HantoPlayerColor.RED);
	}
	
	public MoveResult step()
	{
		if(lastResult != MoveResult.OK)
		{
			return lastResult;
		}
		
		HantoMoveRecord move;
		if(playerTurn == HantoPlayerColor.BLUE)
		{
			move = blue.makeMove(lastMove);
		}
		else
		{
			move = red.makeMove(lastMove);
		}
		
		lastMove = move;
		
		MoveResult result;
		try 
		{
			result = game.makeMove(move.getPiece(), move.getFrom(), move.getTo());
		} 
		catch (HantoException e) 
		{
			result = playerTurn == HantoPlayerColor.BLUE ? MoveResult.RED_WINS : MoveResult.BLUE_WINS;
		}
		
		lastResult = result;
		
		playerTurn = playerTurn == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		return result;
	}
	
	public MoveResult getLastResult()
	{
		return lastResult;
	}
	
	public HantoGame getGame()
	{
		return game;
	}
}
