import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBot extends TimerTask  {

	protected ArrayList<Long> ids = new ArrayList<Long>();

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	
	
	private long messageCounter=0;
	
	
	private static String STANDARD_ANSWER = " The person you have talked is temporarily not available!";

	private Twitter twitter;

	public TwitterBot(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
		this.consumerKey=consumerKey;
		this.consumerSecret=consumerSecret;
		this.accessToken=accessToken;
		this.accessTokenSecret=accessTokenSecret;
	}

	/**
	 * Authentifiziert die Applikation mit Twitter
	 */
	public void login() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessTokenSecret);

		TwitterFactory tf = new TwitterFactory(cb.build());
		this.twitter = tf.getInstance();
	}

	public void addToFollow(String name) {

		this.followTwitterUser(name);
	}

	/**
	 * wird alle 20 Sekunden aufgerufen
	 */
	public void run() {
		this.checkToAnswer();
	}

	/**
	 * checke Kontakte ab und antworte falls nötig.
	 */
	public void checkToAnswer() {
		
		try {
			List<Status> stats;
			stats = twitter.getMentions();
				
			for (Status status : stats) {
				if (isNotYetAnswered(ids, status.getId())) {
					messageCounter++;
					 update("@" + status.getUser().getScreenName() + STANDARD_ANSWER + " ("+ messageCounter +")");
				}
			}
		} catch (TwitterException te) {
			System.err.println(te.getMessage());
		}
	}

	/**
	 * Setzt angegegeben User auf Folgen.
	 * 
	 * @param name 
	 * 			(Name des zu folgenden Users )
	 */
	public void followTwitterUser(String name) {

		try {
			twitter.createFriendship(name);
			System.out.println("Folge Erfolgreich (" + name + ")");
		} catch (TwitterException te) {
			System.err.println("Konnte nicht folgen!");
		}
	}

	/**
	 * 
	 * Überprüft ob die auf eine Nachricht schonmal geantwortet wurde.
	 * @param ids
	 * @param id
	 * @return
	 * 		(true, wenn schon auf die Nachricht geantwortet wurde)
	 */
	public boolean isNotYetAnswered(List<Long> ids, long id) {

		if (ids.contains(id)) {
			return false;
		} else {
			ids.add(id);
			return true;
		}
	}

	
	/**
	 * setzt einen neuen Status des Twitteraccounts
	 * 
	 * @param message
	 *        (Nachricht die im geschrieben werden soll)
	 */	
	public void update(String message) {

		try {
			Status status = twitter.updateStatus(message);
			System.out.println("Erfolgreich updated zu (" + status.getText()+ ")");
		} catch (TwitterException te) {	
			System.err.println("Konnte nicht updaten!");
		}
	}
}