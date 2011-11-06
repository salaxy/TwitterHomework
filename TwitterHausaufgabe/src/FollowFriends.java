import twitter4j.Twitter;
import twitter4j.TwitterException;


public class FollowFriends {
	
	private Twitter twitterMessage;
	
	public FollowFriends(Twitter twitter,String fname){
		this.twitterMessage = twitter;
		createFriendship(fname);
	}
		

	public void createFriendship(String fname){
		
		try{
			twitterMessage.createFriendship(fname, true);
			System.out.println("Ok seid ihr mal Freunde: "+fname);
		}catch(TwitterException te){
			System.out.println("Verkackt");
			
		}
	}
}
