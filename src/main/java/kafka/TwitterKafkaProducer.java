package kafka;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
 
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
 
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
 
 
public class TwitterKafkaProducer {
 
    private static final String topic = "hadoop2";
 
    public static void run() throws InterruptedException {
 
        Properties properties = new Properties();
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        //properties.put("client.id","camus");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
                producerConfig);
 
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(100000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList("twitterapi",
                "#ipl2017"));
        
        String consumerKey=    TwitterSourceConstant.CONSUMER_KEY_KEY;
        String consumerSecret=TwitterSourceConstant.CONSUMER_SECRET_KEY;
        String accessToken=TwitterSourceConstant.ACCESS_TOKEN_KEY;
        String accessTokenSecret=TwitterSourceConstant.ACCESS_TOKEN_SECRET_KEY;
 
        Authentication auth = new OAuth1(consumerKey, consumerSecret, accessToken,
                accessTokenSecret);
 
        Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
                .endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();
 
        client.connect();
 
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            KeyedMessage<String, String> message = null;
            try {
                message = new KeyedMessage<String, String>(topic, queue.take());
            } catch (InterruptedException e) {
                //e.printStackTrace();
                System.out.println("Stream ended");
            }
            //producer.send(message);
        }
        producer.close();
        client.stop();
 
    }
 
    public static void main(String[] args) {
        try {
            TwitterKafkaProducer.run();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

/* 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

 public class TwitterKafkaProducer {
    private static Producer<Integer, String> producer;
    private static final String topic= "hadoop";

    public void initialize() {
          Properties producerProps = new Properties();
          producerProps.put("metadata.broker.list", "localhost:9092");
          producerProps.put("serializer.class", "kafka.serializer.StringEncoder");
          producerProps.put("request.required.acks", "1");
          ProducerConfig producerConfig = new ProducerConfig(producerProps);
          producer = new Producer<Integer, String>(producerConfig);
    }
    public void publishMesssage() throws Exception{            
          BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));               
      while (true){
          System.out.print("Enter message to send to kafka broker (Press 'Y' to close producer): ");
        String msg = null;
        msg = reader.readLine(); // Read message from console
        //Define topic name and message
        KeyedMessage<Integer, String> keyedMsg =
                     new KeyedMessage<Integer, String>(topic, msg);
        producer.send(keyedMsg); // This publishes message on given topic
        if("Y".equals(msg)){ break; }
        System.out.println("--> Message [" + msg + "] sent. Check message on Consumer's program console");
      }
      return;
    }

    public static void main(String[] args) throws Exception {
          TwitterKafkaProducer kafkaProducer = new TwitterKafkaProducer();
          // Initialize producer
          kafkaProducer.initialize();            
          // Publish message
          kafkaProducer.publishMesssage();
          //Close the producer
          producer.close();
    }
}*/