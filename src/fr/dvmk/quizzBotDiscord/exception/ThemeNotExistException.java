package fr.dvmk.quizzBotDiscord.exception;

public class ThemeNotExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ThemeNotExistException(){
		super();
	}
	
	public ThemeNotExistException(String name){
		super("Le th�me "+ name+" n'existe pas");
	}

}
