package fr.dvmk.quizzBotDiscord.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.bo.Question;
import fr.dvmk.quizzBotDiscord.util.DatabaseConnection;

public class QuestionDAO {

	private static Logger logger = Logger.getLogger(QuestionDAO.class);
	
	
	public static List<Question> getQuestionByTheme(String themeName) 
	{
		List<Question> questions = new ArrayList<>();
		
		Connection cnx = null;
		try {
			 cnx = DatabaseConnection.getInstance().getConnection();
		} catch (Exception e) {
			logger.fatal("Impossible de se connecter à la base de donnée");
		}
		
		if(cnx !=null)
		{
			CallableStatement cst = null;
			
			try
			{
				cst = cnx.prepareCall("{CALL all_questions_by_theme_name(?)}");
				cst.setString(1, themeName);
			}
			catch(Exception e)
			{
				logger.error("Erreur lors du chargement de la requete");
			}
			
			if(cst !=null)
			{
				
				ResultSet rs =null;
				
				try
				{
					rs = cst.executeQuery();
				}
				catch(Exception e)
				{
					logger.error("Erreur lors de la requete");
				}
				
				if(rs != null)
				{
					try {
						while(rs.next())
						{
							Question q = new Question();
							q.setEnonce(rs.getString("question"));
							q.setReponse(rs.getString("response"));
							questions.add(q);
						}
					} catch (SQLException e) {
						logger.error("Erreur lors du traitement de la requete");
					}
				}
				try{
					rs.close();
					cst.close();
				}catch(Exception e){
					logger.error("Erreur lors de la fermeture des flux");
				}
				
			}
			
		}
		return questions;
	}
}
