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

	@Autowired
	private PulsarTemplate<User> userTemplate;

	@Autowired
	private UserService userService;

	@Scheduled(initialDelay = 2_000, fixedDelay = 1_000)
	void sourceUserToPulsar(){
		var msgId = userTemplate.send("user-topic", userService.singleUser(), Schema.JSON(User.class));

		System.out.println("### PRODUCE: " + msgId);
	}

	@PulsarListener(topics = "user-topic", schemaType = SchemaType.JSON)
	void logUserFromPulsar(User user){
		System.out.println("### CONSUME: " + user);
	}

	public record User(String uid, String username) {}

}
