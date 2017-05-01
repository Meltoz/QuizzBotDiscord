package fr.dvmk.quizzBotDiscord.dal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;

import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.bo.Thematheque;
import fr.dvmk.quizzBotDiscord.bo.Theme;
import fr.dvmk.quizzBotDiscord.util.DatabaseConnection;

public class ThemeDAO 
{
	 private static Logger logger = Logger.getLogger(ThemeDAO.class);
	 
	public static Thematheque LoadXML() throws FileNotFoundException
	{
		logger.info("Chargement du fichier XML ./data/QuizQuest.xml");
		try{
			return JAXB.unmarshal(new FileInputStream("./data/QuizQuestion.xml"), Thematheque.class);
		}catch(FileNotFoundException fnfe){
			logger.fatal("Impossibe de charger le fichier Quizz Question");
			throw fnfe;
		}
	}
		
		public static List<Theme> getAllThemes()
		{
			List<Theme> themes = new ArrayList<>();
			Connection cnx =null;
			
			try 
			{
				cnx = DatabaseConnection.getInstance().getConnection();
			} 
			catch (Exception e1) 
			{
				logger.fatal("impossible de contacter la base de donnée");
			}
			
			
			if(cnx !=null)
			{
				CallableStatement cst =null;
				try 
				{
					cst = cnx.prepareCall("{call all_themes()}");
				} 
				catch (SQLException e) 
				{
					logger.error("Erreur lors du chargement de la requete");
				}
				if(cst !=null)
				{
					ResultSet rs=null;
					
					//Execution de la requete
					try 
					{
						rs = cst.executeQuery();
					} 
					catch (SQLException e) 
					{
						logger.error("Error lors de l'execution de la requete");
					}
					
					if(rs !=null)
					{
						try 
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
						catch (SQLException e) 
						{
						logger.error("Error lors du chargement des requetes");
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
			return themes;
		}
		
	}
