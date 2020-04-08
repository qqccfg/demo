package com.tets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class MamaBuyServiceMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MamaBuyServiceMonitorApplication.class, args);
	}
}
