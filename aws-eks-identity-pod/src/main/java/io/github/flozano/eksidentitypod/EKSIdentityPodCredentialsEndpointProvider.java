package io.github.flozano.eksidentitypod;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.amazonaws.SdkClientException;
import com.amazonaws.internal.CredentialsEndpointProvider;
import com.amazonaws.retry.internal.CredentialsEndpointRetryParameters;
import com.amazonaws.retry.internal.CredentialsEndpointRetryPolicy;

final class EKSIdentityPodCredentialsEndpointProvider extends CredentialsEndpointProvider {

	static final String CONTAINER_CREDENTIALS_FULL_URI = "AWS_CONTAINER_CREDENTIALS_FULL_URI";

	static final String CONTAINER_AUTHORIZATION_TOKEN_FILE = "AWS_CONTAINER_AUTHORIZATION_TOKEN_FILE";

	@Override
	public URI getCredentialsEndpoint() {
		return URI.create(getRequiredEnvVar(CONTAINER_CREDENTIALS_FULL_URI));
	}

	@Override
	public CredentialsEndpointRetryPolicy getRetryPolicy() {
		return IdentityPodRetryPolicy.INSTANCE;
	}

	@Override
	public Map<String, String> getHeaders() {
		String tokenFile = getRequiredEnvVar(CONTAINER_AUTHORIZATION_TOKEN_FILE);
		try {
			String authorizationToken = new String(Files.readAllBytes(Paths.get(tokenFile)), StandardCharsets.UTF_8);
			return Map.of("Authorization", authorizationToken);
		} catch (IOException e) {
			throw new SdkClientException("The token file " + tokenFile + " cannot be read", e);
		}
	}

	private static String getRequiredEnvVar(String name) {
		String value = System.getenv(name);
		if (value == null || value.length() == 0) {
			throw new SdkClientException("The environment variable " + name + " is empty");
		}
		return value;
	}
}

class IdentityPodRetryPolicy implements CredentialsEndpointRetryPolicy {

	private static final int MAX = 3;

	static IdentityPodRetryPolicy INSTANCE = new IdentityPodRetryPolicy();

	private IdentityPodRetryPolicy() {
	}

	@Override
	public boolean shouldRetry(int retriesAttempted, CredentialsEndpointRetryParameters retryParams) {
		if (retriesAttempted >= MAX) {
			return false;
		}
		Integer statusCode = retryParams.getStatusCode();
		if (statusCode != null && statusCode >= 500 && statusCode < 600) {
			return true;
		}
		if (retryParams.getException() != null && retryParams.getException() instanceof IOException) {
			return true;
		}

		return false;
	}

}
