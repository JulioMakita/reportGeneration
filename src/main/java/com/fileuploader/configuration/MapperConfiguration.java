package com.fileuploader.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class MapperConfiguration {

  public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";

  public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER =
      new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

  @Bean
  public CsvMapper csvMapper() {
    CsvMapper csvMapper = new CsvMapper();
    csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    csvMapper.configure(Feature.IGNORE_UNKNOWN, true);
    return csvMapper;
  }

  @Bean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    Jackson2ObjectMapperBuilder builder =
        new Jackson2ObjectMapperBuilder().serializers(LOCAL_DATETIME_SERIALIZER)
            .serializationInclusion(JsonInclude.Include.NON_NULL);
    return new MappingJackson2HttpMessageConverter(builder.build());
  }
}
