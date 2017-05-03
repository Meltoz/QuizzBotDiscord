package fr.dvmk.quizzBotDiscord.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.bo.Theme;
import fr.dvmk.quizzBotDiscord.util.DatabaseConnection;

public class ThemeDAO 
{
	 private static Logger logger = Logger.getLogger(ThemeDAO.class);
	
	 public static List<Theme> getAllThemes() throws SQLException
	 {
		List<Theme> themes = new ArrayList<>();
		CallableStatement cst = null;
		ResultSet rs = null;
			
		try {
			
			Connection cnx = DatabaseConnection.getInstance().getConnection();
			
			cst = cnx.prepareCall("{call all_themes()}");
			
			rs = cst.executeQuery();
			
			if(rs != null)
			{
				while(rs.next())
				{
					Theme th = new Theme();
					th.setName(rs.getString("themes"));
					th.setNbQuestion(rs.getInt("nbQuestions"));
					logger.info("Chargement du theme <"+th.getName()+"> avec "+th.getNbQuestion()+" questions.");
					themes.add(th);
				}
			}
			
		} catch(SQLException sqle){
			
			logger.fatal("Erreur dans la recuperation des themes"+sqle.getMessage());
			throw sqle;
			
		}catch(Exception e){
			
			logger.fatal(e.getMessage());
			
		}finally{
			
			if(rs!=null) {
				rs.close();
			}
			
			if(cst != null) {
				cst.close();
			}
					
		}
			
		return themes;
	}
		
}
