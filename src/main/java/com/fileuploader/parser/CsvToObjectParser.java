package com.fileuploader.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fileuploader.model.Bet;

@Component
public class CsvToObjectParser implements FileParser<Bet> {

  @Autowired
  private CsvMapper csvMapper;

  @Override
  public List<Bet> read(MultipartFile file) {
    List<Bet> betList = null;

    try {
      CsvSchema orderLineSchema = CsvSchema.emptySchema().withHeader();
      MappingIterator<Bet> orderLines = csvMapper.readerFor(Bet.class).with(orderLineSchema)
          .readValues(new InputStreamReader(file.getInputStream()));
      betList = orderLines.readAll();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return betList;
  }
}
