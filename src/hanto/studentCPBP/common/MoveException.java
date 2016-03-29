package hanto.studentCPBP.common;

/**
 * An exception thrown when an invalid move occurs.
 * @author Connor Porell
 * @author Benny Peake
 *
 */
@SuppressWarnings("serial")
public class MoveException extends Exception
{
	/**
	 * Every instance of this exception must have a message describing the exception.
	 * 
	 * @param message
	 *            the string describing the error causing the exception
	 */
	public MoveException(String message)
	{
		super(message);
	}

	/**
	 * An exception that was caused by some other exception.
	 * 
	 * @param message
	 *            the string describing the error causing the exception
	 * @param cause
	 *            the error that caused this exception
	 */
	public MoveException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
