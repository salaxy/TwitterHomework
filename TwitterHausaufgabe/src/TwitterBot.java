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

	private static String CON_KEY = "yZR1qUww5XUo7AyGULMAcw";
	private static String CON_SEC = "JlzcAihS1C8fIA6rUJNBTr4ucVHUt3uYGkgLzZ6A";
	private static String ACC_KEY = "404279119-gO2LepPJrIMB4mXJnyfjVoZdugHWFT6tooIU5qE";
	private static String ACC_SEC = "gimlxVg1S0m1W7cEDOPyDzF5Mn0dY9pr9ZbBh9iM3Zk";

	private static String STANDARD_ANSWER = " The person you have talked is temporarily not available";

	private Twitter twitter;

	public TwitterBot() {

	}

	/**
	 * Authentifiziert die Applikation mit Twitter
	 */
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

	/**
	 * wird alle 5 Sekunden aufgerufen
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
					 update("@" + status.getUser().getScreenName() + STANDARD_ANSWER);
				}
			}
		} catch (TwitterException te) {
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
			System.err.println("Konnte nicht folgen: " + te.getMessage());
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
			System.out.println("Erfolgreich updated zu (" + status.getText()
					+ ")");
		} catch (TwitterException te) {	
		}
	}
}