package fr.dvmk.quizzBotDiscord.lanceur;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;




public class MainBot implements EventListener{

    private JDA jda;
    private static Logger log = Logger.getLogger(MainBot.class);
    private boolean stop = false;

    MainBot(String token) {	
	    try
		{
	    	jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
	    	jda.addEventListener(this);

		}
	    catch(LoginException le)
	    {
	    	le.printStackTrace();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	    
	 	while (!stop) {
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.next();
            if (cmd.equals("stop")) {
                jda.shutdown(true);
                stop = true;
            }
            scanner.close();
	 	}
	        
    }
    
    @Override
    public void onEvent(Event event) 
    {
        if(event instanceof GuildMessageReceivedEvent)
        {
        	if(!((GuildMessageReceivedEvent) event).getAuthor().isBot())
        		GestionMessage.GestionMessageGuild((GuildMessageReceivedEvent) event);
        }
        else if(event instanceof PrivateMessageReceivedEvent)
        {
        	if(!((PrivateMessageReceivedEvent) event).getAuthor().isBot())
        	GestionMessage.GestionMessagePrivate((PrivateMessageReceivedEvent) event);
    	
        }
 
    }

    public static void main(String[] args) {
    	
    	log.info("Démarrage de l'application");
        new MainBot("MjgxODgzMTQ3NjM2MDQ3ODcy.C4ebvg.1fT6f62QuNjX7TXmawLz-QKhTQ4");
        System.out.println("terminée");
    }

}
