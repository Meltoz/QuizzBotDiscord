package fr.dvmk.quizzBotDiscord.exception;

public class InvalidOperationException extends Exception{

	private static final long serialVersionUID = 4407763625767689234L;
	
	public InvalidOperationException(){
		super();
	}
	
	
	public InvalidOperationException(String message){
		super(message);
	}
}
