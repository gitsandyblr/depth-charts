package org.fanduel.depthcharts.service;

import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.common.TestData;
import org.fanduel.depthcharts.exception.DepthChartException;
import org.fanduel.depthcharts.repository.Position.PlayerPosition;
import org.fanduel.depthcharts.repository.Position.PlayerPositionRepositoryWrapper;
import org.fanduel.depthcharts.repository.player.Player;
import org.fanduel.depthcharts.repository.player.PlayerRepositoryWrapper;
import org.hibernate.QueryTimeoutException;
import org.hibernate.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/cleanup.sql", "/db/data_load.sql"})
public class PlayerDepthServiceTest {

  @InjectMocks
  PlayerDepthService service;

  @Mock
  PlayerRepositoryWrapper wrapper;

  @Mock
  PlayerPositionRepositoryWrapper positionRepositoryWrapper;

  @Test
  public void getBackupsExceptionTest () {
    when(positionRepositoryWrapper.getPlayerPositionByName(anyString())).thenReturn(Optional.empty());
    Assertions.assertThrows(DepthChartException.class, () -> service.getBackups(anyString(), anyString()));
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void getBackupsExceptionCheckTest (Class<? extends Throwable> exceptionClass) {
    when(wrapper.getFullDepthChart()).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> service.getFullDepthChart());
  }

  @ParameterizedTest
  @MethodSource("backupsInputData")
  public void getBackupsTest (String position, String player, List<Player> playerDtos) throws DepthChartException {
    when(positionRepositoryWrapper.getPlayerPositionByName(anyString()))
      .thenReturn(Optional.ofNullable(PlayerPosition.builder().playerPositionName(position).build()));

    when(wrapper.getBackups(position, player)).thenReturn(playerDtos);

    List<PlayerDto> dtoList =  service.getBackups(position, player);
    int dtoSize = playerDtos.size();

    Assertions.assertEquals(dtoSize, dtoList.size());

    dtoList.stream().forEach(element ->
      Assertions.assertTrue(playerDtos.stream().anyMatch(
        dto -> (dto.getPlayerId() == element.getPlayerNumber() && dto.getPlayerName() == element.getPlayerName()))));
  }

  @Test
  public void getFullDepthChartTest () {
    Optional<List<Player>> playerList = TestData.getFullDepthChartData();
    List<Player> list = playerList.get();
    when(wrapper.getFullDepthChart()).thenReturn(playerList);
    List<PlayerDto> dtoList = service.getFullDepthChart();

    dtoList.stream().forEach(element ->
      Assertions.assertTrue(list.stream().anyMatch(player -> player.getPlayerId() == element.getPlayerNumber())));
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void getFullDepthChartExceptionCheckTest (Class<? extends Throwable> exceptionClass) {
    when(wrapper.getFullDepthChart()).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> service.getFullDepthChart());
  }

  @Test
  public void removePlayerToDepthChartTest () throws DepthChartException {

    PlayerDto playerDto = PlayerDto.builder().playerNumber(13).playerName("Mike Evans").build();
    Player player = Player.builder().playerId(13).playerName("Mike Evans").build();
    when(wrapper.removePlayerFromDepthChart(any(PlayerDto.class))).thenReturn(player);
    Player responsePlayer = service.removePlayerToDepthChart(playerDto);

    Assertions.assertEquals(player, responsePlayer);
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void removePlayerToDepthChartTestExceptionCheckTest (Class<? extends Throwable> exceptionClass) throws DepthChartException {
    when(wrapper.removePlayerFromDepthChart(any(PlayerDto.class))).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> service.removePlayerToDepthChart(PlayerDto.builder().build()));
  }


  @Test
  public void addPlayerToDepthChartTest () throws DepthChartException {

    PlayerDto playerDto = PlayerDto.builder().playerNumber(13).playerName("Mike Evans").build();
    Player player = Player.builder().playerId(13).playerName("Mike Evans").build();
    when(wrapper.addPlayerToDepthChart(any(PlayerDto.class))).thenReturn(player);
    Player responsePlayer = service.addPlayerToDepthChart(playerDto);

    Assertions.assertEquals(player, responsePlayer);
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void addPlayerToDepthChartTestExceptionCheckTest (Class<? extends Throwable> exceptionClass) {
    when(wrapper.addPlayerToDepthChart(any(PlayerDto.class))).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> service.addPlayerToDepthChart(PlayerDto.builder().build()));
  }

  static Stream<Arguments> wrapperDbException() {
    return Stream.of(
      Arguments.of(ConstraintViolationException.class),
      Arguments.of(DataException.class),
      Arguments.of(JDBCConnectionException.class),
      Arguments.of(LockAcquisitionException.class),
      Arguments.of(QueryTimeoutException.class),
      Arguments.of(SQLGrammarException.class),
      Arguments.of(GenericJDBCException.class));
  }


  static Stream<Arguments> backupsInputData() {
    return Stream.of(
      Arguments.of("QB", "Tom Brady",
        Arrays.asList(
          Player.builder().playerId(11).playerName("Blaine Gabbert").build(),
          Player.builder().playerId(2).playerName("Kyle Trask").build())),

      Arguments.of("QB", "Jaelon Darden",
        Arrays.asList(
          Player.builder().playerId(10).playerName("Scott Miller").build())),

      Arguments.of("QB", "Mike Evans", new ArrayList<>()),

      Arguments.of("QB", "Blaine Gabbert",
        Arrays.asList(
          Player.builder().playerId(2).playerName("Kyle Trask").build())),

      Arguments.of("QB", "Kyle Trask", new ArrayList<>()));
  }

}
