package com.asr.example.feature.hub.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

  @Id
  @Schema(description = "Unique id for the Entity", accessMode = AccessMode.READ_ONLY)
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false, updatable = false)
  @org.hibernate.annotations.Type(type = "uuid-char")
  private UUID id;

  public static final String COLUMN_VERSION_NAME = "version";

  @Setter(AccessLevel.NONE)
  @Version
  @Schema(description = "Latest version for the Entity", accessMode = AccessMode.READ_ONLY)
  @Column(name = COLUMN_VERSION_NAME)
  private Integer version;

  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  @Column(nullable = false, updatable = false)
  @Schema(description = "Time when entity was created", accessMode = AccessMode.READ_ONLY)
  private Date createdDate;

  @Setter(AccessLevel.NONE)
  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  @Schema(description = "Latest Time when Entity was updated", accessMode = AccessMode.READ_ONLY)
  private Date lastModifiedDate;

  @Setter(AccessLevel.NONE)
  @CreatedBy
  @Column(updatable = false)
  @Schema(description = "Name of the user who created the entity", accessMode = AccessMode.READ_ONLY)
  private String createdBy;

  @Setter(AccessLevel.NONE)
  @LastModifiedBy
  @Schema(description = "Name of the user who updated the entity at last", accessMode = AccessMode.READ_ONLY)
  private String modifiedBy;
}