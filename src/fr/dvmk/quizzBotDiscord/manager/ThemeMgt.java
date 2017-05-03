package fr.dvmk.quizzBotDiscord.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.bo.Theme;
import fr.dvmk.quizzBotDiscord.dal.QuestionDAO;
import fr.dvmk.quizzBotDiscord.dal.ThemeDAO;

public class ThemeMgt {
	
	//Contient les themes contenu dans le fichiers XML
	private List<Theme> _themes;
	
	private static Logger logger = Logger.getLogger(ThemeMgt.class);
	
	
	public ThemeMgt(){
	
		try {
			
			_themes = ThemeDAO.getAllThemes();
			
		} catch (SQLException e) {
			
			logger.error("Impossible de charger les themes "+e.getMessage());
		}
	}
	
	/**
	 * Permet de vérifié si un theme exist
	 * @param themeName
	 * @return
	 */
	public boolean themeExist(String themeName) {
		
		boolean exist= false;
		for (Theme theme : _themes) {
			
			if(theme.getName().equals(themeName)) {
				
				exist=true;
				break;
			}
				
		}
		return exist;
	}

	/**
	 * Permet de retournée un theme précis
	 * @param name
	 * @return
	 */
	public Theme LoadTheme(String name){
		Theme returned=null;
		
		for (Theme theme : _themes) {
			
			if(theme.getName().equals(name)){
				
				try{
					theme.setQuestions(QuestionDAO.getQuestionByTheme(name));
				} catch(Exception e) {
					
					logger.error("Impossible de charger les questions "+ e.getMessage());
				}
				
				returned = theme;
				break;
			}
		}
		return returned;
		
	}

	/**
	 * Permet d'obtenir les noms de tous les themes
	 * @return
	 */
	public List<String> getThemesNames(){
		
		List<String> themesNames = new ArrayList<String>();
		
		for (Theme theme : _themes) {
			themesNames.add(theme.getName());
		}
		
		return themesNames;
	}
}
