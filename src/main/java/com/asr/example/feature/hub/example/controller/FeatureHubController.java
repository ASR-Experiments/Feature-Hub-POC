package com.asr.example.feature.hub.example.controller;

import io.featurehub.client.ClientContext;
import io.featurehub.client.FeatureHubConfig;
import io.featurehub.client.FeatureState;
import io.featurehub.client.Readyness;
import javax.inject.Provider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feature/hub")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FeatureHubController {

  FeatureHubConfig featureHubConfig;

  Provider<ClientContext> ctxProvider;

  @GetMapping("/liveliness")
  public ResponseEntity<String> liveliness() {
    if (Readyness.Ready.equals(featureHubConfig.getReadyness())) {
      return ResponseEntity.ok("Ok");
    } else {
      log.warn("FeatureHub connection not yet available, reporting not live.");
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
  }

  /**
   * Fetches the property from Feature hub and return the effective value as a response
   * @param feature ID of the feature
   * @return Feature and its effective value
   */
  @GetMapping("/properties")
  public ResponseEntity<String> getProperties(@RequestParam String feature) {
    if (StringUtils.hasText(feature)) {
      ClientContext ctx = ctxProvider.get();
      FeatureState featureValue = ctx.feature(() -> feature);
      if (featureValue == null) {
        return ResponseEntity.notFound().build();
      }
      String value = featureValue.toString();
      if (StringUtils.hasText(value)) {
        return ResponseEntity.ok(String.format("%s:%s", feature, value));
      } else {
        return ResponseEntity.notFound().build();
      }
    } else {
      return ResponseEntity.badRequest().body("Feature is a required Attribute");
    }
  }
}
