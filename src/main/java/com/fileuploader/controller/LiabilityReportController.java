package com.fileuploader.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fileuploader.model.Bet;
import com.fileuploader.model.Report;
import com.fileuploader.parser.FileParser;
import com.fileuploader.report.ReportGeneration;

@RestController
public class LiabilityReportController {

  @Autowired
  private FileParser fileParser;

  @Autowired
  private CsvMapper csvMapper;

  @Resource(name = "liability")
  private ReportGeneration reportGeneration;

  @PostMapping(value = "/upload/liability", headers = "content-type=multipart/*")
  public ResponseEntity selectionReport(@RequestPart("file") MultipartFile file)
      throws JsonProcessingException {
    List<Bet> bets = fileParser.read(file);

    if (bets == null || bets.isEmpty()) {
      return ResponseEntity.badRequest().body("File content should not be empty");
    }
    return ResponseEntity.accepted().body(reportGeneration.generate(bets));
  }

  @PostMapping(value = "/json/liability", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity selection(@RequestBody List<Bet> bets) throws JsonProcessingException {
    if (bets == null || bets.isEmpty()) {
      return ResponseEntity.badRequest().body("Request should not be empty");
    }

    return  ResponseEntity.accepted().body(reportGeneration.generate(bets));
  }

  private String generateSelectionReport(List<Report> reports) throws JsonProcessingException {
    CsvSchema schema = csvMapper.schemaFor(Report.class).withHeader().withoutQuoteChar();
    return csvMapper.writer(schema).writeValueAsString(reports);
  }
}
