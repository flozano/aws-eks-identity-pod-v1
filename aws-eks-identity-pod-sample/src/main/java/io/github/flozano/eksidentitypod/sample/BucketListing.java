package io.github.flozano.eksidentitypod.sample;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EC2ContainerCredentialsProviderWrapper;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.auth.WebIdentityTokenCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;

import io.github.flozano.eksidentitypod.EKSIdentityPodCredentialsProvider;

@Component
class BucketListing {

	private final AmazonS3 client;

	BucketListing() {
		client = AmazonS3Client.builder().withCredentials(
				new AWSCredentialsProviderChain(
						new EKSIdentityPodCredentialsProvider(), // added
						new EnvironmentVariableCredentialsProvider(),
						new SystemPropertiesCredentialsProvider(),
						WebIdentityTokenCredentialsProvider.create(),
						new ProfileCredentialsProvider(),
						new EC2ContainerCredentialsProviderWrapper()
				)).build();
	}

	public List<String> listBuckets() {
		return client.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
	}
}
