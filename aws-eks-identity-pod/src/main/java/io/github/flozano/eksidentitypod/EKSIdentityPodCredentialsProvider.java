package io.github.flozano.eksidentitypod;

import com.amazonaws.auth.ContainerCredentialsProvider;

public final class EKSIdentityPodCredentialsProvider extends ContainerCredentialsProvider {
	public EKSIdentityPodCredentialsProvider() {
		super(new EKSIdentityPodCredentialsEndpointProvider());
	}
}
