package com.fileuploader.report;

import static java.util.Comparator.comparing;

import java.util.HashMap;
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
import com.fileuploader.model.ReportType;

@Component
public class GenerateReport implements ReportGeneration<Bet> {

  @Autowired
  private CsvMapper csvMapper;

  public String generate(List<Bet> betList, ReportType reportType) throws JsonProcessingException {

    Map<String, Report> map = new HashMap<>();
    for (Bet bet : betList) {
      String mapKey = null;
      if (ReportType.LIABILITY.equals(reportType)) {
        mapKey = bet.getCurrency();
      } else if (ReportType.SELECTION.equals(reportType)) {
        mapKey = bet.getSelectionName() + "-" + bet.getCurrency();
      }
      if (map.containsKey(mapKey)) {
        Report report = map.get(mapKey);
        report.incrementNumberOfBets();
        report.calculateTotalStake(bet.getStake());
        report.calculateTotalLiability(bet.getStake(), bet.getPrice());
      } else {
        map.put(mapKey, new Report(bet.getSelectionName(), bet.getCurrency(), 1, bet.getStake(),
            bet.getStake().multiply(bet.getPrice())));
      }
    }

    List<Report> reportList = map.values().stream()
        .sorted(comparing(Report::getCurrency).reversed()
            .thenComparing(comparing(Report::getTotalLiability).reversed()))
        .collect(Collectors.toList());

    return generateSelectionReport(reportList, reportType);
  }

  private String generateSelectionReport(List<Report> reports, ReportType reportType)
      throws JsonProcessingException {
    CsvSchema schema = null;
    if (ReportType.LIABILITY.equals(reportType)) {
      schema = CsvSchema.builder().addColumn("currency").addColumn("numberOfBets")
          .addColumn("totalStakes").addColumn("totalLiability").build().withHeader()
          .withoutQuoteChar();
    } else {
      schema = csvMapper.schemaFor(Report.class).withHeader().withoutQuoteChar();
    }
    return csvMapper.writer(schema).writeValueAsString(reports);
  }
}
