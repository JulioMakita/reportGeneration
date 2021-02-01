package com.fileuploader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fileuploader.model.Bet;

public class BetUtils {

  public static List<Bet> getBetList() {
    java.util.List<Bet> betList = new ArrayList<>();
    betList.add(new Bet("Bet-10", 1489490156000L, 1, "My Fair Lady", new BigDecimal(0.5),
        new BigDecimal(6.0), "GBP"));
    betList.add(new Bet("Bet-28", 1489324556000L, 1, "My Fair Lady", new BigDecimal(7.8),
        new BigDecimal(5.5), "GBP"));
    betList.add(new com.fileuploader.model.Bet("Bet-31", 1489407356000L, 1, "My Fair Lady",
        new BigDecimal(10.5), new BigDecimal(7.3), "GBP"));
    betList.add(new com.fileuploader.model.Bet("Bet-15", 1489140956000L, 1, "My Fair Lady",
        new BigDecimal(3.4), new BigDecimal(6.5), "EUR"));
    betList.add(new com.fileuploader.model.Bet("Bet-11", 1489490156000L, 2, "Always a Runner",
        new BigDecimal(1.25), new BigDecimal(4.0), "GBP"));

    return betList;
  }

  public static String getBetCsvFormat(){
      return "betId, betTimestamp, selectionId, selectionName, stake, price, currency\n" +
              "Bet-10, 1489490156000, 1, My Fair Lady, 0.5, 6.0, GBP\n" +
              "Bet-11, 1489490156000, 2, Always a Runner, 1.25, 4.0, EUR\n" +
              "Bet-15, 1489140956000, 1, My Fair Lady, 3.4, 6.5, EUR\n" +
              "Bet-31, 1489407356000, 1, My Fair Lady, 10.5, 7.3, GBP";
  }
}
