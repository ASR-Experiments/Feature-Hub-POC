package com.asr.example.feature.hub.example.entity;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
@AttributeOverride(name = "id", column = @Column(name = "customer_id", unique = true, nullable = false, updatable = false))
public class Customer extends BaseEntity {
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "locale")
  private Locale locale;

  @Column(name = "currency")
  private Currency currency;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Lob
  @Column(name = "address_line_1")
  @Type(type = "org.hibernate.type.TextType")
  private String addressLine1;

  @Lob
  @Column(name = "address_line_2")
  @Type(type = "org.hibernate.type.TextType")
  private String addressLine2;

  @Column(name = "city")
  private String city;

  @Column(name = "state")
  private String state;

  @Column(name = "zip_code")
  private String zipCode;

  @Column(name = "country")
  private String country;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  /**
   * Last hyphen considered as end of ISD Code
   */
  @Column(name = "phone_number_with_isd", nullable = false, unique = true)
  private String phoneNumberWithIsd;

}