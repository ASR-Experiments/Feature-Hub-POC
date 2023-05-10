package com.asr.example.feature.hub.example.exception;

public class DisabledFeature extends RuntimeException {

  final String humanReadableMessage;

  public DisabledFeature(String featureName) {
    super(featureName + " is disabled for Current Release");
    this.humanReadableMessage = "Requested feature is not accessible with current release";
  }
}
