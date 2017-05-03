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
	
	
	public static List<Question> getQuestionByTheme(String themeName) throws Exception
	{
		List<Question> questions = new ArrayList<>();
		CallableStatement cst =null;
		ResultSet rs =null;	
		
		Connection cnx = null;
		try {
			
			cnx = DatabaseConnection.getInstance().getConnection();
			 
			cst = cnx.prepareCall("{CALL all_questions_by_theme_name(?)}");
			cst.setString(1, themeName);
			
			
			rs = cst.executeQuery();
			
			if(rs != null)
			{
				while(rs.next())
				{
					Question q = new Question();
					q.setEnonce(rs.getString("question"));
					q.setReponse(rs.getString("response"));
					questions.add(q);
				}
			}
		
		} catch (SQLException sqle) {
			
			logger.fatal("Impossible d'executer la requete : "+sqle.getMessage());
			
		} catch(Exception e) {
			
			logger.fatal(e.getMessage());
		
		} finally {
			rs.close();
			cst.close();
		}
		
		return questions;
	}
}
