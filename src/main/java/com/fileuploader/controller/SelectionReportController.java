package com.fileuploader.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fileuploader.model.Bet;
import com.fileuploader.parser.FileParser;
import com.fileuploader.report.ReportGeneration;

import javax.annotation.Resource;

@RestController
public class SelectionReportController {

  @Autowired
  private FileParser fileParser;

  @Autowired
  private CsvMapper csvMapper;

  @Resource(name = "selection")
  private ReportGeneration reportGeneration;

  @PostMapping(value = "/upload/selection", headers = "content-type=multipart/*")
  public ResponseEntity liabilityReport(@RequestPart("file") MultipartFile file)
          throws JsonProcessingException {
    List<Bet> bets = fileParser.read(file);

    if (bets == null || bets.isEmpty()) {
      return ResponseEntity.badRequest().body("File content should not be empty");
    }

    return ResponseEntity.accepted().body(reportGeneration.generate(bets));
  }

  @PostMapping(value = "/json/selection", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity selection(@RequestBody List<Bet> bets) throws JsonProcessingException {
    if (bets == null || bets.isEmpty()) {
      return ResponseEntity.badRequest().body("Request should not be empty");
    }

    return  ResponseEntity.accepted().body(reportGeneration.generate(bets));
  }
}
