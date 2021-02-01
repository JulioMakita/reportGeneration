package com.fileuploader.report;

import java.util.Comparator;
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

@Component("liability")
public class TotalLiabilityReport implements ReportGeneration<Bet> {

  @Autowired
  private CsvMapper csvMapper;

  public String generate(List<Bet> betList) throws JsonProcessingException {
    List<Report> reportList = betList.stream()
        .map(b -> new Report(b.getCurrency(), 1, b.getStake(), b.getStake().multiply(b.getPrice())))
        .collect(Collectors.toList());

    return generateLiabilityReport(getReportMap(reportList));
  }

  private static List<Report> getReportMap(List<Report> reportList) {
    Map<String, Report> map = new HashMap<>();
    for (Report report : reportList) {
      if (map.containsValue(map.get(report.getCurrency()))) {
        Report r = map.get(report.getCurrency());
        map.put(report.getCurrency(),
            new Report(r.getName(), r.getCurrency(), r.getNumberOfBets() + 1,
                r.getTotalStakes().add(report.getTotalStakes()),
                r.getTotalLiability().add(report.getTotalLiability())));
      } else {
        map.put(report.getCurrency(), report);
      }
    }
    return map.values().stream().sorted(Comparator.comparing(Report::getCurrency).reversed())
        .collect(Collectors.toList());
  }

  private String generateLiabilityReport(List<Report> reports) throws JsonProcessingException {
    CsvSchema schema =
        CsvSchema.builder().addColumn("currency").addColumn("numberOfBets").addColumn("totalStakes")
            .addColumn("totalLiability").build().withHeader().withoutQuoteChar();
    return csvMapper.writer(schema).writeValueAsString(reports);
  }
}
