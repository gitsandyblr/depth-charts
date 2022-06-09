package org.fanduel.depthcharts.repository.player;

import org.fanduel.depthcharts.api.PlayerDto;
import org.fanduel.depthcharts.common.TestData;
import org.fanduel.depthcharts.exception.DepthChartException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlayerRepositoryWrapperTest {

  @InjectMocks
  PlayerRepositoryWrapper wrapper;

  @Mock
  PlayerRepository repository;

  @Test
  public void addPlayerToDepthChartNewPlayerWithNoBackUpsTest() {

    PlayerDto playerDto = PlayerDto.builder().playerPositionDepth(0).playerName("Mike Evans").playerPosition("LWR").build();
    Player player1 = Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();

    when(repository.findPlayersByName(anyString())).thenReturn(null);
    when(repository.getExistingDepthPosition(anyString(), anyInt())).thenReturn(new ArrayList<>());
    when(repository.save(any())).thenReturn(player1);

    Player player = wrapper.addPlayerToDepthChart(playerDto);

    Assertions.assertEquals(player.getPlayerName(), playerDto.getPlayerName());
    Assertions.assertEquals(player.getPlayerPosition(), playerDto.getPlayerPosition());
    Assertions.assertEquals(player.getPlayerPositionDepth(), playerDto.getPlayerPositionDepth());
    verify(repository, times(0)).updateDepthPosition(anyInt(), anyInt());
  }

  @Test
  public void addPlayerToDepthChartNewPlayerWithBackUpsTest() {

    PlayerDto playerDto = PlayerDto.builder().playerPositionDepth(0).playerName("Mike Evans").playerPosition("LWR").build();
    Player player1 = Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();
    Player player2 = Player.builder().playerId(14).playerName("Mike Peter").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();
    Player player3 = Player.builder().playerId(15).playerName("Mike Buttler").playerPosition("LWR")
      .playerPositionDepth(1).playerSportsId(1).playerTeamId(768).build();

    when(repository.findPlayersByName(anyString())).thenReturn(null);
    when(repository.getExistingDepthPosition(anyString(), anyInt())).thenReturn(Arrays.asList(player2, player3));
    when(repository.save(any())).thenReturn(player1);

    doNothing().when(repository).updateDepthPosition(anyInt(), anyInt());

    Player player = wrapper.addPlayerToDepthChart(playerDto);

    Assertions.assertEquals(player.getPlayerName(), playerDto.getPlayerName());
    Assertions.assertEquals(player.getPlayerPosition(), playerDto.getPlayerPosition());
    Assertions.assertEquals(player.getPlayerPositionDepth(), playerDto.getPlayerPositionDepth());
    verify(repository, times(2)).updateDepthPosition(anyInt(), anyInt());
  }

  @Test
  public void addPlayerToDepthChartExistingPlayerWithNoBackUpsTest() {

    PlayerDto playerDto = PlayerDto.builder().playerPositionDepth(0).playerName("Mike Evans").playerPosition("LWR").build();
    Player player1 = Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();

    when(repository.findPlayersByName(anyString())).thenReturn(player1);
    when(repository.getExistingDepthPosition(anyString(), anyInt())).thenReturn(new ArrayList<>());
    when(repository.save(any())).thenReturn(player1);

    Player player = wrapper.addPlayerToDepthChart(playerDto);

    Assertions.assertEquals(player.getPlayerName(), playerDto.getPlayerName());
    Assertions.assertEquals(player.getPlayerPosition(), playerDto.getPlayerPosition());
    Assertions.assertEquals(player.getPlayerPositionDepth(), playerDto.getPlayerPositionDepth());
    verify(repository, times(0)).updateDepthPosition(anyInt(), anyInt());
  }

  @Test
  public void addPlayerToDepthChartExistingPlayerWithBackUpsTest() {

    PlayerDto playerDto = PlayerDto.builder().playerPositionDepth(0).playerName("Mike Evans").playerPosition("LWR").build();
    Player player1 = Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();
    Player player2 = Player.builder().playerId(14).playerName("Mike Peter").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();
    Player player3 = Player.builder().playerId(15).playerName("Mike Buttler").playerPosition("LWR")
      .playerPositionDepth(1).playerSportsId(1).playerTeamId(768).build();

    when(repository.findPlayersByName(anyString())).thenReturn(player1);
    when(repository.getExistingDepthPosition(anyString(), anyInt())).thenReturn(Arrays.asList(player2, player3));
    when(repository.save(any())).thenReturn(player1);

    doNothing().when(repository).updateDepthPosition(anyInt(), anyInt());

    Player player = wrapper.addPlayerToDepthChart(playerDto);

    Assertions.assertEquals(player.getPlayerName(), playerDto.getPlayerName());
    Assertions.assertEquals(player.getPlayerPosition(), playerDto.getPlayerPosition());
    Assertions.assertEquals(player.getPlayerPositionDepth(), playerDto.getPlayerPositionDepth());
    verify(repository, times(2)).updateDepthPosition(anyInt(), anyInt());
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void addPlayerToDepthChartExceptionTest(Class<? extends Throwable> exceptionClass) {
    when(repository.findPlayersByName(anyString())).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> wrapper.addPlayerToDepthChart(PlayerDto.builder().playerName("Chris Evans").build()));
  }

  @Test
  public void removePlayerFromDepthChartTest() throws DepthChartException {

    PlayerDto playerDto = PlayerDto.builder().playerName("Mike Evans").playerPosition("LWR").build();
    Player player1 = Player.builder().playerId(13).playerName("Mike Evans").playerPosition("LWR")
      .playerPositionDepth(0).playerSportsId(1).playerTeamId(768).build();

    when(repository.findPlayersByName(anyString())).thenReturn(player1);
    when(repository.getExistingDepthPosition(anyString(), anyInt())).thenReturn(new ArrayList<>());
    doNothing().when(repository).delete(any());

    Player player = wrapper.removePlayerFromDepthChart(playerDto);

    Assertions.assertEquals(player.getPlayerName(), playerDto.getPlayerName());
    Assertions.assertEquals(player.getPlayerPosition(), playerDto.getPlayerPosition());
    verify(repository, times(0)).updateDepthPosition(anyInt(), anyInt());
  }

  @Test
  public void removePlayerFromDepthChartNoPlayerExistingTest() throws DepthChartException {

    when(repository.findPlayersByName(anyString())).thenReturn(null);

    Assertions.assertThrows(DepthChartException.class, () ->
      wrapper.removePlayerFromDepthChart(PlayerDto.builder().playerName("Chris Evans").build()));
  }

  @ParameterizedTest
  @MethodSource("wrapperDbException")
  public void removePlayerToDepthChartExceptionTest(Class<? extends Throwable> exceptionClass) {
    when(repository.findPlayersByName(anyString())).thenThrow(exceptionClass);
    Assertions.assertThrows(exceptionClass, () -> wrapper.removePlayerFromDepthChart(PlayerDto.builder().playerName("Chris Evans").build()));
  }

  @Test
  public void getBackupsNoBackUpsTest() {
    when(repository.getBackups(anyString(), anyString())).thenReturn(null);
    Assertions.assertEquals(null, wrapper.getBackups("LWR", "Cris Rodney"));
  }

  @Test
  public void getBackupsWithBackUpsTest() {
    Player player = Player.builder().playerPosition("LWR").playerPositionDepth(0).build();

    Player player2 = Player.builder().playerId(14).playerName("Mike Peter").playerPosition("LWR")
      .playerPositionDepth(1).playerSportsId(1).playerTeamId(768).build();
    Player player3 = Player.builder().playerId(15).playerName("Mike Buttler").playerPosition("LWR")
      .playerPositionDepth(2).playerSportsId(1).playerTeamId(768).build();

    when(repository.getBackups(anyString(), anyString())).thenReturn(player);
    when(repository.getBackupsByPositionAndDepth(anyString(), anyInt())).thenReturn(Arrays.asList(player2, player3));

    Assertions.assertEquals(2, wrapper.getBackups("LWR", "Cris Rodney").size());
  }

  @Test
  public void getBackupsExceptionTest() {
    when(repository.getBackups(anyString(), anyString())).thenThrow(QueryTimeoutException.class);
    Assertions.assertThrows(QueryTimeoutException.class,() -> wrapper.getBackups("LWR", "Cris Rodney"));
  }

  @Test
  public void getFullDepthChartNoBackupsTest() {
    when(repository.findAll()).thenReturn(null);
    Assertions.assertEquals(Optional.empty(), wrapper.getFullDepthChart());
  }

  @Test
  public void getFullDepthChartWithBackupsTest() {
    when(repository.findAll()).thenReturn(TestData.getFullDepthChartData().get());
    Assertions.assertEquals(6, wrapper.getFullDepthChart().get().size());
  }

  @Test
  public void getFullDepthChartExceptionTest() {
    when(repository.findAll()).thenThrow(QueryTimeoutException.class);
    Assertions.assertThrows(QueryTimeoutException.class,() -> wrapper.getFullDepthChart());
  }

  static Stream<Arguments> wrapperDbException
    () {
    return Stream.of(
      Arguments.of(ConstraintViolationException.class),
      Arguments.of(DataException.class),
      Arguments.of(JDBCConnectionException.class),
      Arguments.of(LockAcquisitionException.class),
      Arguments.of(QueryTimeoutException.class),
      Arguments.of(SQLGrammarException.class),
      Arguments.of(GenericJDBCException.class));
  }

}
