import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBot extends Thread{

	protected ArrayList<Long> ids = new ArrayList<Long>();
	
	private static String CON_KEY = "LsdtBCRJN3lSotARzeuQYw";
	private static String CON_SEC = "0ylBJAWGfN5ZJe5V0vWbNbKS8d9ZZOvwWNuT3mtrQ";
	private static String ACC_KEY = "406540577-Mpn45CROCW47IZ7XtSiiluah7hmMKHiHxRPYzX8A";
	private static String ACC_SEC = "J2S9avix6F3bJTMv2BeDMkiax3Hsibe716ZhmHaAgk";
	
	private static String STANDARD_ANSWER="Ich bin nur ein dummer Twitterbot123!";	
	
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


	public void addToFollow(String name) {
		
		this.followTwitterUser(name);
	}

	
	public void run(){
		
		this.checkToAnswer();
		try {
			//max 120 Anfragen/Stunde
			TwitterBot.sleep(30000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * checke Kontakte ab und antworte falls nötig
	 */
	public void checkToAnswer() {
		
		List<Status> stats;
		try {
			stats = twitter.getMentions();		
			
			for (Status status : stats) {
				if (isNotYetAnswered(ids, status.getId())) {
					update("@" + status.getUser().getScreenName() + " sent: " + STANDARD_ANSWER);
				}
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public void followTwitterUser(String name) {

		try {
			twitter.createFriendship(name);
			System.out.println("Folge Erfolgreich (" + name + ")");
		} catch (TwitterException te) {
			System.err.println("Konnte nicht folgen: " + te.getMessage());
		}
	}
	
	
	public boolean isNotYetAnswered(List<Long> ids, long id) {

		if (ids.contains(id)) {
			return false;
		} else {
			ids.add(id);
			return true;
		}
	}
	
	public void update(String message) {

		try {
			Status status = twitter.updateStatus(message);
			System.out.println("Erfolgreich updated zu (" + status.getText() + ")");
		} catch (TwitterException te) {
			 System.err.println("Konnte nicht updaten: " + te.getMessage());
		}
	}
}
