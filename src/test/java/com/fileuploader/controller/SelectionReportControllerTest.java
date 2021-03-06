package com.fileuploader.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileuploader.BetUtils;
import com.fileuploader.model.Bet;

@SpringBootTest
public class SelectionReportControllerTest {

  @Mock
  private RestTemplate restTemplate;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  private ObjectMapper mapper;

  @BeforeEach
  public void init() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mapper = new ObjectMapper();
  }

  @Test
  public void given_stringJsonArray_when_callingRestRequest_then_shouldCalculateAndReturnSelectionReportAsCsvFormat()
      throws Exception {

    List<Bet> betList = BetUtils.getBetList();

    String jsonRequest = mapper.writeValueAsString(betList);

    String expectedResponse = "name,currency,numberOfBets,totalStakes,totalLiability\n"
        + "My Fair Lady,GBP,3,18.80,122.55\n" + "Always a Runner,GBP,1,1.25,5.00\n"
        + "My Fair Lady,EUR,1,3.40,22.10\n";

    mockMvc
        .perform(
            post("/json/selection").contentType(MediaType.APPLICATION_JSON).content(jsonRequest))
        .andExpect(status().isAccepted()).andExpect(content().string(expectedResponse));
  }

  @Test
  public void given_csvFile_when_callingRestRequest_then_shouldCalculateAndReturnSelectionReportAsCsvFormat()
      throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "bet.csv", MediaType.TEXT_PLAIN_VALUE,
        BetUtils.getBetCsvFormat().getBytes());

    String expectedResponse = "name,currency,numberOfBets,totalStakes,totalLiability\n"
        + " My Fair Lady, GBP,2,11.00,79.65\n" + " My Fair Lady, EUR,1,3.40,22.10\n"
        + " Always a Runner, EUR,1,1.25,5.00\n";

    mockMvc.perform(multipart("/upload/selection").file(file))
        .andExpect(content().string(expectedResponse));
  }
}
