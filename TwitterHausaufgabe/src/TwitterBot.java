import java.util.ArrayList;
import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBot {

	private static String CON_KEY = "LsdtBCRJN3lSotARzeuQYw";
	private static String CON_SEC = "0ylBJAWGfN5ZJe5V0vWbNbKS8d9ZZOvwWNuT3mtrQ";
	private static String ACC_KEY = "406540577-Mpn45CROCW47IZ7XtSiiluah7hmMKHiHxRPYzX8A";
	private static String ACC_SEC = "J2S9avix6F3bJTMv2BeDMkiax3Hsibe716ZhmHaAgk";
	
	private static String STANDARD_ANSWER="Ich bin nur ein dummer Twitterbot!";	
	private static String UNIVERSAL_ANSWER="42!";	

	private ArrayList<String> toFollow;
	
	private Twitter twitter;

	public TwitterBot() {

	}

	public void login() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(CON_KEY);
		cb.setOAuthConsumerSecret(CON_SEC);
		cb.setOAuthAccessToken(ACC_KEY);
		cb.setOAuthAccessTokenSecret(ACC_SEC);
		
		TwitterFactory tf = new TwitterFactory(cb.build());
		this.twitter = tf.getInstance();
	}
	
	

	/**
	 * sends private message to someone
	 */
	public void sendDirectMessage(String name) {

		try {
			DirectMessage message = twitter.sendDirectMessage(name,
					"Message Example");
			System.out.println("Message erfolgreich gesendet an:" + message.getRecipientScreenName());
		} catch (TwitterException e) {
			System.out.println("Senden der Message fehlgeschlagen an: " + e.getMessage());
		}
	}

	/**
	 * shows all private Messages of the twitter account
	 */
	public void getDirectMessages() {

		try {
			Paging paging = new Paging(1);
			List<DirectMessage> messages;
			do {
				messages = twitter.getDirectMessages(paging);
				for (DirectMessage message : messages) {
					System.out.println("From: @"
							+ message.getSenderScreenName() + " id:"
							+ message.getId() + " - " + message.getText());
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
		} catch (TwitterException te) {
			System.out.println("Failed to get messages: " + te.getMessage());
		}
	}

	public void addToFollow(String name) {

	}

	public boolean contactHasNewMessageForMe() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * checke Kontakte ab und antworte falls nötig
	 */
	public void checkToAnswer() {
		//gucke nach dem Status der 
		//zu verfolgendenden twitteraccounts
		for(String contact : this.toFollow){
			//check			
			//ist eine neue Nachricht da dann 
			//ueberpruefe ob du angesprochen wurdest
			if(this.contactHasNewMessageForMe()){
		//falls ja
		//dann schicke nachricht
			
				
			}
		}
		
	}
	
	

}
