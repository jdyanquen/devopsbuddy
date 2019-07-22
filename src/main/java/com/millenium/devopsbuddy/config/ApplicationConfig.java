package com.millenium.devopsbuddy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@EnableJpaRepositories(basePackages = "com.millenium.devopsbuddy.backend.persistence.repositories")
@EntityScan(basePackages = "com.millenium.devopsbuddy.backend.persistence.domain.backend")
@EnableTransactionManagement
@PropertySource("file:///${user.home}/.devopsbuddy/application-common.properties")
public class ApplicationConfig {

	@Value("${aws.s3.profile}")
	private String awsProfileName;
	
	@Bean
	public AmazonS3Client s3Client() {
		AWSCredentialsProvider credentials = new ProfileCredentialsProvider(awsProfileName);
		AmazonS3Client client = (AmazonS3Client) AmazonS3ClientBuilder
				.standard()
				.withCredentials(credentials)
				.withRegion(Regions.US_WEST_1)
				.build();
				
		return client;
	}
}
