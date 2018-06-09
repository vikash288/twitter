package com.twitter.app.twitter;

import java.io.IOException;
import java.util.ArrayList;

public class WhatToThink {

	public static void main(String[] args) throws IOException {
		String topic[] = {"trump"};
		TweetManager.getTweets(topic);
		//System.out.println(tweets);

		NLP.init();
		/*for(String tweet : tweets) {
			//System.out.println( tweet);
			//System.out.println(tweet + " : " + NLP.findSentiment(tweet));
		}*/
	}
}

