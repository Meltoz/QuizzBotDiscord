package fr.dvmk.quizzBotDiscord.lanceur;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import fr.dvmk.quizzBotDiscord.exception.InvalidOperationException;
import fr.dvmk.quizzBotDiscord.exception.ThemeNotExistException;
import fr.dvmk.quizzBotDiscord.manager.QuestionnaireMgt;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.impl.MemberImpl;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

public class GestionMessage {
	
	private static Logger logger = Logger.getLogger(GestionMessage.class);
	
	private static QuestionnaireMgt questionnaireMgt = new QuestionnaireMgt();
	
	public static void GestionMessageGuild(GuildMessageReceivedEvent gmre){
		
		//Vérification si le message ne vient pas d'un bot
    	if(!gmre.getAuthor().isBot()){
    		
    		//Récupération du channel
        	TextChannel chan = gmre.getChannel();
        	if(chan.getName().equals("quiz"))
        	{
        		Message msgReceiving = gmre.getMessage();
        		if(questionnaireMgt.validResult(msgReceiving.getContent())){
        			
        			MessageBuilder msgReturned = new MessageBuilder();
        			msgReturned.append("Bravo ");
        			msgReturned.append(new MemberImpl(gmre.getGuild(), msgReceiving.getAuthor()));
        			msgReturned.append(" ! Tu as été le premier a trouver la bonne réponse");
        			chan.sendMessage(msgReturned.build()).queue();
        		
        			
        			try{
        				nextQuestion();
        			}catch(InvalidOperationException ioe){
        				logger.error(ioe.getMessage());
        				
        				MessageBuilder msg = new MessageBuilder();
        				msg.append(ioe.getMessage());
        				chan.sendMessage(msg.build()).queue();
        			}
        			
        			try {
        				Questionnaire(msgReceiving.getJDA());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
        			
        		}
        		
        		
        	}
    	}
	}
	
	public static void GestionMessagePrivate(PrivateMessageReceivedEvent pmre){
		
		if(!pmre.getAuthor().getName().equals("testbotMaisOnSaitPasTropMdr")){
				
			//Liste des pattern disponible pour chaque commande
			Pattern patternChangeTheme = Pattern.compile("changeTheme ([\\w]+)");
			Pattern listThemes = Pattern.compile("listThemes");
			
			
			//Récupération du Message
			Message receivingMessage= pmre.getMessage();	
			
			logger.info("Reception d'un message privé <" +receivingMessage.getContent()+"> par <"+receivingMessage.getAuthor().getName()+">");
			
			//Matcher pour chaque commande
			Matcher mChangeTheme = patternChangeTheme.matcher(receivingMessage.getContent());
			Matcher mListThemes= listThemes.matcher(receivingMessage.getContent());
			
			//Generation du messages envoyée
			MessageBuilder msgReturned = new MessageBuilder();
			
			//Match avec le changement de theme
			if(mChangeTheme.matches())
			{
				logger.info("Commande <ChangeThemes> demande par <"+receivingMessage.getAuthor().getName()+">");
				try
				{
					questionnaireMgt.ChangeTheme(mChangeTheme.group(1));
					msgReturned.append("Changement du theme vers <"+ mChangeTheme.group(1)+">.");
				}
				catch(InvalidOperationException ioe)
				{
					msgReturned.append(ioe.getMessage());
					logger.error(ioe.getMessage());
				}
				catch(ThemeNotExistException tnee)
				{
					msgReturned.append(tnee.getMessage());
					logger.error(tnee.getMessage());
				}
			}
			
			//Match avec list themes
			else if(mListThemes.matches())
			{
				logger.info("Commande <ListThemes> demande par <"+receivingMessage.getAuthor().getName()+">");
				
				List<String> themesNames = questionnaireMgt.ListThems();
				msgReturned.append("Liste des themes disponible : \n");
				for (String theme : themesNames) {

						if(questionnaireMgt.getCurrentTheme()!=null &&theme.equals(questionnaireMgt.getCurrentTheme().getName())){
							msgReturned.append("\t<"+theme+">\n");
						}
				
					else{
						msgReturned.append("\t"+theme+"\n");
					}
				}
				
			}
			
			else if(receivingMessage.getContent().equals("startQuizz"))
			{
				logger.info("Commande <startQuizz> demande par <"+receivingMessage.getAuthor().getName()+">");
				try
		    	{
					GestionMessage.Questionnaire(receivingMessage.getJDA());
					
		    	}
		    	catch(Exception e){
		    		logger.error("Impossible de commancé le quizz : <"+e.getMessage()+">");
		    		msgReturned.append(e.getMessage());
		    	}
			}
			
			///Command "nextQuestion"
			else if(receivingMessage.getContent().equals("nextQuestion"))
			{
			
				logger.info("Commande <nextQuestion> demande par <"+receivingMessage.getAuthor().getName()+">");
				try{
					GestionMessage.nextQuestion();
	            	try
	            	{
	            		Questionnaire(receivingMessage.getJDA());
	            	}
	            	catch(Exception e)
	            	{
	            		logger.error(e.getMessage());
	            	}
				}
				catch(InvalidOperationException ioe)
				{
					msgReturned.append(ioe.getMessage());
					logger.error(ioe.getMessage());
				}
				
			}
			//par default
			else
			{
				logger.info("Aucun commande reconnu");
				msgReturned.append("Commande non valide !\n");
				msgReturned.append("Commande disponible :\n");
				msgReturned.append("\tlistThemes\n");
				msgReturned.append("\tchangeTheme <NomDuTheme>");
				msgReturned.append("\tstartQuizz");
			}
			
			
			if(!msgReturned.isEmpty())
				pmre.getChannel().sendMessage(msgReturned.build()).queue();
		}
	}

	public static void Questionnaire(JDA jda) throws Exception
	{
		MessageBuilder msg = new MessageBuilder();
		if(questionnaireMgt.canStart())
		{
			TextChannel channel = jda.getTextChannelsByName("quiz", false).get(0);
			msg.append("==== Question ====\n");
			msg.append(questionnaireMgt.getEnonceQuestion());
			channel.sendMessage(msg.build()).queue();
		}
		else
			throw new Exception("Impossible un theme n'as pas ete selectionné");
	}
	
	public static void nextQuestion() throws InvalidOperationException{
		questionnaireMgt.nextQuestion();
	}
}
