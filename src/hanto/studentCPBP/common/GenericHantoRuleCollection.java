package hanto.studentCPBP.common;

import java.util.ArrayList;

import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;

public abstract class GenericHantoRuleCollection implements IHantoRuleSet 
{
	private HantoPlayerColor currentTurn;
	private int moveCount = 0;
	private boolean isGameOver = false;
	
	public interface IRule
	{
		void checkBoard(IHantoBoard board) throws HantoException;
	}
	
	public interface IStartCondition
	{
		void check(IHantoBoard board) throws HantoException;
	}
	
	public interface IEndCondition
	{
		MoveResult checkForResult(IHantoBoard board);
	}
	
	private ArrayList<IRule> rules = new ArrayList<>();
	private ArrayList<IStartCondition> startConditions = new ArrayList<>();
	private ArrayList<IEndCondition> endConditions = new ArrayList<>();
	
	protected boolean getIsGameOver()
	{
		return isGameOver;
	}
	
	public void addRule(IRule rule)
	{
		rules.add(rule);
	}
	
	public void addStartCondition(IStartCondition condition)
	{
		startConditions.add(condition);
	}
	
	public void addEndCondition(IEndCondition condition)
	{
		endConditions.add(condition);
	}
	
	@Override
	public void beginTurn(IHantoBoard board) throws HantoException 
	{
		for(IStartCondition condition : startConditions)
		{
			condition.check(board);
		}
	}
	
	@Override
	public void checkBoard(IHantoBoard board) throws HantoException 
	{
		for(IRule rule : rules)
		{
			rule.checkBoard(board);
		}
	}

	@Override
	public HantoPlayerColor getCurrentTurn() 
	{
		return currentTurn;
	}

	@Override
	public MoveResult endTurn(IHantoBoard board) throws HantoException 
	{
		MoveResult result = MoveResult.OK;
		for(IEndCondition condition : endConditions)
		{
			result = condition.checkForResult(board);
			if(result != MoveResult.OK)
			{
				isGameOver = true;
				break;
			}
		}
		
		moveCount++;
		currentTurn = currentTurn == HantoPlayerColor.BLUE ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		
		return result;
	}

	@Override
	public int getTurnNumber() 
	{
		return (moveCount / 2) + 1;
	}
}
