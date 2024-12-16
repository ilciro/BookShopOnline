package laptop.exception;

import java.io.Serial;

public class IdException extends Exception{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public IdException(String mex)
	{
		super(mex);
	}

}
