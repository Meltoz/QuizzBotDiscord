package fr.dvmk.quizzBotDiscord.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;

public class Thematheque implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2687482487662961487L;
	private List<Theme> _themes;
	
	public Thematheque(){
		_themes = new ArrayList<Theme>();
	}
	
	@XmlElementWrapper(name="themes")
	@XmlElements({@XmlElement(name="theme", type=Theme.class)})
	public List<Theme> getThemes(){
		return _themes;
	}
	
	public void setThemes(List<Theme> themes){
		_themes = themes;
	}
}
