package org.sekon.ppin.exceptions;


public class NoCandidatesException extends Exception {
	private static final long serialVersionUID = -6318906990778975078L;

	public NoCandidatesException() {
	    super();
	  }
	  
	  public NoCandidatesException(String message) {
	    super(message);
	  }

	  public NoCandidatesException(String message, Throwable ex) {
	    super(message, ex);
	  }
}
