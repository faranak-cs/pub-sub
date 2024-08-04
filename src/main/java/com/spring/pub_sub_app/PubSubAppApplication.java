package com.spring.pub_sub_app;

import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class PubSubAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubSubAppApplication.class, args);
	}

	@Autowired private PulsarTemplate<User> userTemplate;
	@Autowired private PulsarTemplate<String> stringTemplate;
	@Autowired private UserService userService;

	// PRODUCER FOR TOPIC "SSH_LOGS"
	@Scheduled(initialDelay = 1_000, fixedDelay = 1_000)
	void sourceStringToPulsar(){
		var msgId = stringTemplate.send("persistent://public/default/ssh_logs", "Wrong password...");
		System.out.println("@@@ PRODUCE: " + msgId);
	}

	// CONSUMER FOR TOPIC "SSH_LOGS"
	@PulsarListener(subscriptionName = "PubSubAppApplication", topics = "persistent://public/default/ssh_logs")
	void logStringFromPulsar(String log){
		System.out.println("@@@ CONSUME: " + log);
	}


	// PRODUCER FOR TOPIC "USER-TOPIC"
	@Scheduled(initialDelay = 10_000, fixedDelay = 1_000)
	void sourceUserToPulsar(){

		// org.apache.pulsar.client.api.MessageId
		// send(String topic, T message, org.apache.pulsar.client.api.Schema<T> schema)
		// returns the id assigned by the broker to the published message

		var msgId = userTemplate.send("persistent://test/fakhan/user-topic", userService.singleUser(), Schema.JSON(User.class));
		System.out.println("### PRODUCE: " + msgId);
	}

	// CONSUMER FOR TOPIC "USER-TOPIC"
	@PulsarListener(subscriptionName = "PubSubAppApplication", topics = "persistent://test/fakhan/user-topic", schemaType = SchemaType.JSON)
	void logUserFromPulsar(User user){
		System.out.println("### CONSUME: " + user);
	}

	// USER RECORD
	public record User(String uid, String username) {}

}
