package org.fanduel.depthcharts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.service.PlayerDepthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

@SpringBootTest
public class PlayerControllerTest {

  protected MockMvc mvc;

  @Autowired
  PlayerDepthService service;

  @Autowired
  WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  @Test
  public void addPlayerToDepthChartTest() throws Exception {
    String requestUrl = "/v1/addPlayerToDepthChart";

    PlayerDto player = PlayerDto.builder().playerPosition("QB").playerName("Tom Brady").playerPositionDepth(0).build();

    String inputJson = mapToJson(player);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(requestUrl)
      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(200, status);
  }

  @Test
  public void addPlayerToDepthChartExceptionTest() throws Exception {
    String requestUrl = "/v1/addPlayerToDepthChart";

    PlayerDto player = PlayerDto.builder().playerPosition("QB").playerName("Tom Brady").playerPositionDepth(0).build();

    String inputJson = mapToJson(player);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(requestUrl)
      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(200, status);
  }

  @Test
  public void removePlayerToDepthChartTest() throws Exception {
    String requestUrl = "/v1/removePlayerToDepthChart";

    PlayerDto player = PlayerDto.builder().playerPosition("QB").playerName("Tom Brady").build();

    String inputJson = mapToJson(player);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(requestUrl)
      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(200, status);
  }

  @ParameterizedTest
  @MethodSource("invalidRequestObjectTest")
  public void checkEndpointForException(String requestUrl) throws Exception {
    PlayerDto playerDto = PlayerDto.builder().playerName("Tom Brady").build();
    String inputJson = mapToJson(playerDto);
    MvcResult mvcResult;

    if(requestUrl.contains("removePlayerToDepthChart")) {
      mvcResult = mvc.perform(MockMvcRequestBuilders.delete(requestUrl)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
    } else {
      mvcResult = mvc.perform(MockMvcRequestBuilders.post(requestUrl)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
    }

    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(400, status);
  }

  @ParameterizedTest
  @MethodSource("invalidRequestTypeTest")
  public void checkEndpointForInvalidRequestTypeException(String requestUrl) throws Exception {
    PlayerDto playerDto = PlayerDto.builder().playerName("Tom Brady").build();
    String inputJson = mapToJson(playerDto);
    MvcResult mvcResult;
      mvcResult = mvc.perform(MockMvcRequestBuilders.patch(requestUrl)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();


    int status = mvcResult.getResponse().getStatus();
    Assertions.assertEquals(405, status);
  }

  static Stream<Arguments> invalidRequestObjectTest() {
    return Stream.of(
      Arguments.of("/v1/addPlayerToDepthChart"),
      Arguments.of("/v1/removePlayerToDepthChart"),
      Arguments.of("/v1/getBackups"));
  }

  static Stream<Arguments> invalidRequestTypeTest() {
    return Stream.of(
      Arguments.of("/v1/addPlayerToDepthChart"),
      Arguments.of("/v1/removePlayerToDepthChart"),
      Arguments.of("/v1/getFullDepthChart"),
      Arguments.of("/v1/getBackups"));
  }
}
