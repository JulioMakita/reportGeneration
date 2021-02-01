package com.fileuploader.report;

import static java.util.Comparator.comparing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fileuploader.model.Bet;
import com.fileuploader.model.Report;

@Component("selection")
public class TotalSelectionReport implements ReportGeneration<Bet> {

  @Autowired
  private CsvMapper csvMapper;

  public String generate(List<Bet> betList) throws JsonProcessingException {

    Map<String, Map<String, List<Bet>>> betMap = betList.stream().collect(
        Collectors.groupingBy(Bet::getSelectionName, Collectors.groupingBy(Bet::getCurrency)));

    return generateSelectionReport(getReports(betMap));
  }

  private static List<Report> getReports(Map<String, Map<String, List<Bet>>> groupedBet) {
    List<Report> reportList = new ArrayList<>();
    for (Map.Entry map : groupedBet.entrySet()) {
      reportCalculation(reportList, (Map) map.getValue());
    }
    return reportList;
  }

  private static void reportCalculation(List<Report> reportList, Map<String, List<Bet>> value) {
    for (Map.Entry map2 : value.entrySet()) {
      reportCalculation(reportList, map2);
    }
  }

  private static void reportCalculation(List<Report> reportList, Map.Entry map2) {
    List<Bet> listBet = (List<Bet>) map2.getValue();
    String name = "";
    String currency = "";
    int numBets = 0;
    BigDecimal totalStakes = BigDecimal.ZERO;
    BigDecimal totalLiability = BigDecimal.ZERO;
    for (Bet b : listBet) {
      numBets++;
      totalStakes = totalStakes.add(b.getStake());
      totalLiability = totalLiability.add(b.getStake().multiply(b.getPrice()));
      name = b.getSelectionName();
      currency = b.getCurrency();
    }
    reportList.add(new Report(name, currency, numBets, totalStakes, totalLiability));
    reportList.sort(comparing(Report::getCurrency).reversed()
        .thenComparing(comparing(Report::getTotalLiability).reversed()));
  }

  private String generateSelectionReport(List<Report> reports) throws JsonProcessingException {
    CsvSchema schema = csvMapper.schemaFor(Report.class).withHeader().withoutQuoteChar();
    return csvMapper.writer(schema).writeValueAsString(reports);
  }
}
