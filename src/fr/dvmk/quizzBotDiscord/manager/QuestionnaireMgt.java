package fr.dvmk.quizzBotDiscord.manager;

import java.util.List;

import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.bo.Question;
import fr.dvmk.quizzBotDiscord.bo.Theme;
import fr.dvmk.quizzBotDiscord.exception.InvalidOperationException;
import fr.dvmk.quizzBotDiscord.exception.ThemeNotExistException;

public class QuestionnaireMgt {
	private Theme currentTheme;
	private Question currentQuestion;
	private ThemeMgt themeManager;
	private int index;
	private boolean isStart;
	private Logger logger = Logger.getLogger(QuestionnaireMgt.class);
	
	public QuestionnaireMgt()
	{
		themeManager = new ThemeMgt();
		isStart=false;
	}
	
	/**
	 * Permet de changer le theme en cours
	 * @param name
	 * @throws InvalidOperationException
	 * @throws ThemeNotExistException
	 */
	public void ChangeTheme(String name) throws InvalidOperationException, ThemeNotExistException{
		
		if(currentTheme!=null)
		{
			if(!currentTheme.getName().equals(name))
			{
				changeCurrentTheme(name);
			}
			else
			{
				logger.error("Theme selectionné est déjà le theme en cours");
				throw new InvalidOperationException("Le theme "+name+" est déjà le theme actuelle");
			}
		}
		else
		{
			changeCurrentTheme(name);
		}
		
	}
	/**
	 * Permet de charger un theme en fonction de son nom,
	 * La methode permet egalement de RAZ l'index des questions
	 * Elle s'occupe d'inscrire la nouvelle question dans currentQuestion
	 * @param name
	 * @throws ThemeNotExistException
	 */
	private void changeCurrentTheme(String name) throws ThemeNotExistException
	{
		
		if(themeManager.themeExist(name))
		{
			logger.info("Changement du theme vers "+ name);
			
			
			currentTheme=themeManager.LoadTheme(name);
			index = 0;
			currentQuestion = currentTheme.getQuestions().get(index); 
			isStart=true;
		}
		else{
			isStart=false;
			throw new ThemeNotExistException(name);
			
		}
	}

	/**
	 * Getter pour ListThems
	 * @return
	 */
	public List<String> ListThems(){
		return themeManager.getThemesNames();
	}
	
	
	/**
	 * getter de la propriété currentQuestion
	 * @return
	 */
	public String getEnonceQuestion(){
		return currentQuestion.getEnonce();
	}

	/**
	 * permet de valider une reponse
	 * @param response
	 * @return
	 */
	public boolean validResult(String response){

		boolean isValid= false;
		if(currentQuestion.getReponse().equalsIgnoreCase(response)){
			
			logger.info("Reponse <"+ response+"> trouvée.");
			isValid=true;			
		}
		return isValid;
	}

	/**
	 * Permet de passer a la question suivantes dans le test
	 * @throws InvalidOperationException
	 */
	public void nextQuestion() throws InvalidOperationException{
		
		//Le test est-il commencé
		if(isStart){
			//si on depasse la taille max de la liste des questions RAZ de l'index
			if(index+1 > currentTheme.getQuestions().size()-1){
				index =0;
			}
			else{
				index++;
			}
			
			logger.info("Passage a la question "+(index+1));
			currentQuestion = currentTheme.getQuestions().get(index);
		}
		else 
			throw new InvalidOperationException("Impossible tant qu'un theme n'as pas ete choisi");
	}

	/**
	 * Permet pour les autres classes de savoir si le test peut commencer
	 * @return
	 */
	public boolean canStart(){
		boolean canStart=false;
		
		if(currentTheme != null){
			if(!currentTheme.getQuestions().isEmpty()){
				canStart = true;
			}
		}
		return canStart;
	}
	
	public Theme getCurrentTheme()
	{
		return currentTheme;
	}

}
