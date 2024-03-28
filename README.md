# AWS EKS Identity Pod support for AWS Java SDK v1
### Introduction

It seems AWS decided to explicitly discard supporting EKS Identity Pods, even if the feature is generally available and the maintenance mode of AWS SDK v1 will start in July. [1]

This project tries to backport such support in a minimal way. Host checks and/or https checks are not done.

### Usage

In order to use it, just include use the `EKSIdentityPodCredentialsProvider` in your Credentials chain.

### Maven coordinates

gradle: `implementation 'io.github.flozano:aws-eks-identity-pod:0.0.4'`


### Sample usage

The `aws-eks-identity-pod-sample` is a minimal sample server that we used to verify it works well.

