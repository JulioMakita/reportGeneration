package com.fileuploader.model;

import java.math.BigDecimal;

public class Bet {

  private String betId;

  private Long betTimestamp;

  private Integer selectionId;

  private String selectionName;

  private BigDecimal stake;

  private BigDecimal price;

  private String currency;

  public Bet() {}

  public Bet(String betId, Long betTimestamp, Integer selectionId, String selectionName,
      BigDecimal stake, BigDecimal price, String currency) {
    this.betId = betId;
    this.betTimestamp = betTimestamp;
    this.selectionId = selectionId;
    this.selectionName = selectionName;
    this.stake = stake;
    this.price = price;
    this.currency = currency;
  }

  public String getBetId() {
    return betId;
  }

  public Long getBetTimestamp() {
    return betTimestamp;
  }

  public Integer getSelectionId() {
    return selectionId;
  }

  public String getSelectionName() {
    return selectionName;
  }

  public BigDecimal getStake() {
    return stake;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getCurrency() {
    return currency;
  }

}
