package com.asr.example.feature.hub.example.exception;

public class DisabledFeatureException extends RuntimeException {

  final String humanReadableMessage;

  public DisabledFeatureException(String featureName) {
    super(featureName + " is disabled for Current Release");
    this.humanReadableMessage = "Requested feature is not accessible with current release";
  }
}
