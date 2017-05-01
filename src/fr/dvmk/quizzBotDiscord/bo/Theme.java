package fr.dvmk.quizzBotDiscord.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public class Theme implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3677611559832663812L;
	private String _name;
	private int _nbQuestion;
	private List<Question> _questions;
	
	public Theme(){
		_questions = new ArrayList<Question>();
		
	}
	
	@Override
	public boolean equals(Object o){
		
		boolean isEqual =false;
		
		if(o !=null){
			
			if(o instanceof Theme){
				Theme t = (Theme)o;
				if(t.getName().equals(_name)){
					isEqual=true;
				}
			}
		}
		return isEqual;
	}
	
	@XmlAttribute(name="name")
	public String getName(){
		return _name;
	}
	
	public void setName(String name){
		_name = name;
	}
	
	@XmlAttribute(name="nbQuestion")
	public int getNbQuestion(){
		return _nbQuestion;
	}
	
	public void setNbQuestion(int nbQuestion){
		_nbQuestion = nbQuestion;
	}
	
	@XmlElementWrapper(name="questions")
	@XmlElements({@XmlElement(name="question", type=Question.class)})
	public List<Question> getQuestions(){
		return _questions;
	}
	
	public void setQuestions(List<Question> questions){
		_questions= questions;
	}

}
