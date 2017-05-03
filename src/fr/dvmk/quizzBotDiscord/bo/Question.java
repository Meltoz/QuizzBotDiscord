package fr.dvmk.quizzBotDiscord.bo;

import java.io.Serializable;

public class Question implements Serializable{

	private static final long serialVersionUID = 208912371537830052L;
	private int id;
	private String enonce;
	private String reponse;
	
	public Question(){
		
	}
	
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	
	
	public String getEnonce(){
		return enonce;
	}
	public void setEnonce(String enonce){
		this.enonce = enonce;
	}
	

	public String getReponse(){
		return reponse;
	}
	
	public void setReponse(String reponse){
		this.reponse = reponse;
	}
	
}
