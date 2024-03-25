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

	public List<String> listBuckets(String region) {
		AmazonS3 client = null;
		try {
			client = getClient(region);
			return client.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	private AmazonS3 getClient(String region) {
		return AmazonS3Client.builder().withCredentials(
				new AWSCredentialsProviderChain(new EKSIdentityPodCredentialsProvider(), // added
						new EnvironmentVariableCredentialsProvider(), new SystemPropertiesCredentialsProvider(),
						WebIdentityTokenCredentialsProvider.create(), new ProfileCredentialsProvider(),
						new EC2ContainerCredentialsProviderWrapper())).withRegion(region).build();

	}
}
