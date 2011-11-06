import java.util.Timer;



public class Start {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		
		//init
		TwitterBot bot=new TwitterBot();
		//anmelden des eigenen Accounts
		bot.login();
		
		//Kontakte adden
		bot.addToFollow("person1");
		bot.addToFollow("person2");

		
		while(true){
			bot.checkToAnswer();
		}
	}
	



}
