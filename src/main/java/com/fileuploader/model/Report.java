package com.fileuploader.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Report {

  private String name;

  private String currency;

  private Integer numberOfBets;

  private BigDecimal totalStakes;

  private BigDecimal totalLiability;

  public Report(String currency, Integer numberOfBets, BigDecimal totalStakes,
      BigDecimal totalLiability) {
    this.currency = currency;
    this.numberOfBets = numberOfBets;
    this.totalStakes = totalStakes.setScale(2, RoundingMode.CEILING);
    this.totalLiability = totalLiability.setScale(2, RoundingMode.CEILING);
  }

  public Report(String name, String currency, Integer numberOfBets, BigDecimal totalStakes,
      BigDecimal totalLiability) {
    this(currency, numberOfBets, totalStakes, totalLiability);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getCurrency() {
    return currency;
  }

  public Integer getNumberOfBets() {
    return numberOfBets;
  }

  public BigDecimal getTotalStakes() {
    return totalStakes;
  }

  public BigDecimal getTotalLiability() {
    return totalLiability;
  }

}
