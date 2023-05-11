package com.asr.example.feature.hub.example.config.property;

import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class UserProperties {

  Map<String, List<String>> agentsPhases;

  @PostConstruct
  void postConstruct() {
    agentsPhases = Map.of(
        "test2@yopmail.com", List.of("PHASE2"),
        "test1@yopmail.com", List.of("PHASE1")
    );
  }
}
