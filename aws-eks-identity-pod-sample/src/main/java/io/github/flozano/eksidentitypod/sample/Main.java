package io.github.flozano.eksidentitypod.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

	public static void main(String[] argz) throws Exception {
		new SpringApplicationBuilder(Main.class)
				.registerShutdownHook(true)
				.run(argz);
	}
}
