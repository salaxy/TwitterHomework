

public class Start {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		//Twitterbot erstellen
		TwitterBot bot=new TwitterBot();
		
		//anmelden des eigenen Accounts
		bot.login();
		
		//Kontakte adden
		bot.addToFollow("twitt3rbot");
		bot.addToFollow("TwitBot2");
		
		//Thread starten
		bot.run();
	}

}
