package com.twitter.app.twitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class TweetManager {
	static FileOutputStream fos;
	static StatusListener listener = new StatusListener() {
		 
		// The onStatus method is executed every time a new tweet comes in.
 		public void onStatus(Status status) {
 			
 			int senti = NLP.findSentiment(status.getText().replaceAll("[\\uD83D\\uFFFD\\uFE0F\\u203C\\u3010\\u3011\\u300A\\u166D\\u200C\\u202A\\u202C\\u2049\\u20E3\\u300B\\u300C\\u3030\\u065F\\u0099\\u0F3A\\u0F3B\\uF610\\uFFFC]", ""));
 			String content =  status.getId() + ":" + status.getGeoLocation() + ":" + status.getText() + ":"+status.getUser().getScreenName() + ":"+ senti + "\n";
 			byte[] contentInBytes = content.getBytes();
 			
 			try {
				fos.write(contentInBytes );
				
			} catch (IOException e) {
				e.printStackTrace();
			}
 			 
 			// The EventBuilder is used to build an event using the headers and
			// the raw JSON of a tweet
			//NLP.findSentiment(status.getText());
			//NLP.findSentiment(status.getText());
			/*int senti = NLP.findSentiment(status.getText());
			
			 
			
			 */
			System.out.println( status.getUser().getScreenName() + " : " );
			
			
			
			
			//System.out.println(tweetsArry.toString());
			 
			
			
			//System.out.println(status.getUser().getScreenName() + ": " + status.getText());

			//System.out.println("timestamp : "+ String.valueOf(status.getCreatedAt().getTime()));
			/*try {
				fos.write(DataObjectFactory.getRawJSON(status).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}*/

		}

		// This listener will ignore everything except for new tweets
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
		public void onScrubGeo(long userId, long upToStatusId) {}
		public void onException(Exception ex) {}
		public void onStallWarning(StallWarning warning) {}
	};
	
	public static  void getTweets(String[] topic) throws IOException {

		try { 
			fos = new FileOutputStream(new File("c:\\VIKASH\\twitterstreamcsv.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Twitter twitter = new TwitterFactory().getInstance();
		//ArrayList<String> tweetList = new ArrayList<String>();
		//Status status = null;
		
		TwitterStream twitterStream = null;
 		Properties prop = new Properties();
 		
		try {
			prop.load(new FileInputStream("twitter.properties"));

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey(prop.getProperty("CONSUMER_KEY"));
			cb.setOAuthConsumerSecret(prop.getProperty("CONSUMER_SECRET"));
			cb.setOAuthAccessToken(prop.getProperty("ACCESS_TOKEN"));
			cb.setOAuthAccessTokenSecret(prop.getProperty("ACCESS_TOKEN_SECRET"));
			cb.setJSONStoreEnabled(true);
			cb.setIncludeEntitiesEnabled(true);
			
			twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		// Set up the stream's listener (defined above),
		twitterStream.addListener(listener);

		System.out.println("Starting down Twitter sample stream...");

		// Set up a filter to pull out industry-relevant tweets
		 
		FilterQuery query = new FilterQuery().track(topic);
		twitterStream.filter(query);
	 
		
		
		
		
		/*try {
			Query query = new Query(topic);
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				System.out.println( DataObjectFactory.getRawJSON(status).getBytes() );
				System.out.println( tweets.toString());
				for (Status tweet : tweets) {
					
					tweetList.add(tweet.getText());
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}*/
		//return tweetList;
	}
}


