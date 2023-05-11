package com.asr.example.feature.hub.example.config;

import io.featurehub.client.ClientContext;
import io.featurehub.client.EdgeFeatureHubConfig;
import io.featurehub.client.FeatureHubConfig;
import io.featurehub.sse.model.StrategyAttributeCountryName;
import io.featurehub.sse.model.StrategyAttributeDeviceName;
import io.featurehub.sse.model.StrategyAttributePlatformName;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

@Configuration
public class CustomFeatureHubConfig {

  @Value("${feature.hub.api.key}")
  Optional<String> featureHubApiKey;

  @Value("${feature.hub.url}")
  Optional<String> featureHubUrl;

  @Bean
  public FeatureHubConfig featureHubConfig() {
    if (featureHubUrl.filter(StringUtils::hasText).isEmpty()) {
      throw new RuntimeException("Unable to get the Feature Hub Url from configuration properties");
    } else if (featureHubApiKey.filter(StringUtils::hasText).isEmpty()) {
      throw new RuntimeException("Unable to get the Feature Hub API key from configuration properties");
    }
    EdgeFeatureHubConfig featureHubConfig = new EdgeFeatureHubConfig(
        featureHubUrl.get().trim(), featureHubApiKey.get().trim());

    featureHubConfig.init();
    return featureHubConfig;
  }

  @Bean
  @Scope("request")
  ClientContext featureHubClient(FeatureHubConfig fhConfig, HttpServletRequest request) {
    final ClientContext fhClient = fhConfig.newContext();

    if (request.getHeader("Authorization") != null) {
      // you would always authenticate some other way, this is just an example
      fhClient.userKey(request.getHeader("Authorization"));
    }
    getCountryName(request.getHeader("x-countryCode"))
        .ifPresent(fhClient::country);
    getDeviceName(request.getHeader("User-Agent"))
        .ifPresentOrElse(
            fhClient::device,
            () -> fhClient.device(StrategyAttributeDeviceName.BROWSER));
    getPlatformName(request.getHeader("User-Agent"))
        .ifPresentOrElse(
            fhClient::platform,
            () -> fhClient.platform(StrategyAttributePlatformName.WINDOWS));
    Optional
        .ofNullable(request.getHeader("x-session-id"))
        .filter(StringUtils::hasText)
        .map(String::trim)
        .ifPresent(fhClient::sessionKey);
    fhClient.version("V2");
    setUserDetails(fhClient, SecurityContextHolder.getContext().getAuthentication());
    Optional
        .ofNullable(request.getHeader("x-application-source"))
        .filter(StringUtils::hasText)
        .map(String::trim)
        .ifPresent(applicationSource -> fhClient.attr("ApplicationSource", applicationSource));
    return fhClient;
  }

  private void setUserDetails(ClientContext fhClient, Authentication authentication) {
    if (authentication != null) {
      if (!authentication.isAuthenticated()) {
        return;
      }
      Object credentials = authentication.getCredentials();
      if (credentials instanceof Jwt) {
        Jwt jwt = (Jwt) credentials;
        fhClient.userKey(jwt.getSubject());
        fhClient.attr("email", jwt.getClaimAsString("email"));
      }
    }
  }

  private Optional<StrategyAttributeDeviceName> getDeviceName(String userAgent) {
    if (StringUtils.hasText(userAgent)) {
      String processedUA = userAgent.trim().toLowerCase();
      if (processedUA.contains("mobi")) {
        return Optional.of(StrategyAttributeDeviceName.MOBILE);
      } else if (processedUA.contains("bot")) {
        return Optional.of(StrategyAttributeDeviceName.SERVER);

      }
    }
    return Optional.empty();
  }

  private Optional<StrategyAttributePlatformName> getPlatformName(String userAgent) {
    if (StringUtils.hasText(userAgent)) {
      String processedUA = userAgent.trim().toLowerCase();
      if (processedUA.contains("mobi")) {
        if (processedUA.contains("android")) {
          return Optional.of(StrategyAttributePlatformName.ANDROID);
        } else if (processedUA.contains("iphone")) {
          return Optional.of(StrategyAttributePlatformName.IOS);
        } else if (processedUA.contains("windows nt")) {
          return Optional.of(StrategyAttributePlatformName.WINDOWS);
        } else if (processedUA.contains("macintosh")) {
          return Optional.of(StrategyAttributePlatformName.MACOS);
        } else if (processedUA.contains("linux")) {
          return Optional.of(StrategyAttributePlatformName.LINUX);
        }
      }
    }
    return Optional.empty();
  }

  private static Optional<StrategyAttributeCountryName> getCountryName(String countryCode) {
    if (StringUtils.hasText(countryCode)) {
      switch (countryCode.trim().toUpperCase()) {
        case "US":
        case "USA":
          return Optional.of(StrategyAttributeCountryName.UNITED_STATES);
        case "CA":
          return Optional.of(StrategyAttributeCountryName.CANADA);
        case "IN":
        case "IND":
          return Optional.of(StrategyAttributeCountryName.INDIA);
        case "GB":
        case "UK":
          return Optional.of(StrategyAttributeCountryName.UNITED_KINGDOM);
        default:
          // Do nothing
          break;
      }
    }
    return Optional.empty();
  }
}
