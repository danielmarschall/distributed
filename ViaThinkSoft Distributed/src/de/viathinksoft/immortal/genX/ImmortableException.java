package de.viathinksoft.immortal.genX;

public class ImmortableException extends Exception {

	private static final long serialVersionUID = -2350754871091479027L;
	
	public ImmortableException() {
		super();
	}

	public ImmortableException(Throwable cause) {
		super(cause);
	}

	public ImmortableException(String message) {
		super(message);
	}

	public ImmortableException(String message, Throwable cause) {
		super(message, cause);
	}

}
