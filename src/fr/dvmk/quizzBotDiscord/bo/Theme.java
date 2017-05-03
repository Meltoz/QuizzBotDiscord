package fr.dvmk.quizzBotDiscord.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Theme implements Serializable{
	
	private static final long serialVersionUID = 3677611559832663812L;
	private String name;
	private int nbQuestion;
	private List<Question> questions;
	
	public Theme(){
		questions = new ArrayList<Question>();
		
	}
	
	@Override
	public boolean equals(Object o){
		
		boolean isEqual =false;
		
		if(o !=null){
			
			if(o instanceof Theme){
				Theme t = (Theme)o;
				if(t.getName().equals(name)){
					isEqual=true;
				}
			}
		}
		return isEqual;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getNbQuestion(){
		return nbQuestion;
	}
	
	public void setNbQuestion(int nbQuestion){
		this.nbQuestion = nbQuestion;
	}
	
	public List<Question> getQuestions(){
		return questions;
	}
	
	public void setQuestions(List<Question> questions){
		this.questions= questions;
	}

}
