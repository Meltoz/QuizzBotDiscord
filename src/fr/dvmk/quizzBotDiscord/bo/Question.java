package fr.dvmk.quizzBotDiscord.bo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Question implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 208912371537830052L;
	private int _id;
	private String _enonce;
	private String _reponse;
	
	public Question(){
		
	}
	
	@XmlAttribute(name="id")
	public int getId(){
		return _id;
	}
	public void setId(int id){
		_id=id;
	}
	
	@XmlElement(name="statement")
	public String getEnonce(){
		return _enonce;
	}
	public void setEnonce(String enonce){
		_enonce = enonce;
	}
	
	@XmlElement(name="response")
	public String getReponse(){
		return _reponse;
	}
	
	public void setReponse(String reponse){
		_reponse = reponse;
	}
	
}
