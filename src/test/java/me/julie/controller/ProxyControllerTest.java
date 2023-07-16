package me.julie.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.julie.model.Bot;
import me.julie.model.CoordAdapter;
import me.julie.model.EndGameJson;
import me.julie.model.GameResult;
import me.julie.model.JsonUtils;
import me.julie.model.MessageJson;
import me.julie.model.ReportDamageServerJson;
import me.julie.model.SetupServerJson;
import me.julie.model.ShipType;
import me.julie.model.SuccessfulHitsJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private Bot bot;

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    this.bot = new Bot();
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);

    this.bot.setup(10, 10, ships);
    assertEquals("", logToString());
  }

  /**
   * Check that the client returns a proper message when given a request to join
   */
  @Test
  public void testHandleJoin() {
    // Create sample join record
    JsonNode jsonNode = createSampleMessage("join", new BlankStubJson());

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new Bot());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);
  }

  /**
   * Check that the client returns a proper message when given a request to setup
   */
  @Test
  public void testHandleSetup() {
    // Create sample setup record
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.CARRIER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    JsonNode jsonNode = createSampleMessage("setup", new SetupServerJson(10, 10, ships));

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new Bot());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);
  }

  /**
   * Check that the client returns a proper message when given a request to takeShots
   */
  @Test
  public void testHandleTakeShots() {
    // Create sample take-shots record
    JsonNode jsonNode = createSampleMessage("take-shots", new BlankStubJson());

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, new Bot());
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);

  }

  /**
   * Check that the client returns a proper message when given a request to reportDamage
   */
  @Test
  public void testHandleReportDamage() {
    // Create sample report-damage record
    CoordAdapter[] coords = new CoordAdapter[2];
    coords[0] = new CoordAdapter(0, 0);
    coords[1] = new CoordAdapter(1, 1);

    JsonNode jsonNode = createSampleMessage("report-damage",
        new ReportDamageServerJson(coords));

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, this.bot);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);

  }

  /**
   * Check that the client returns a proper message when given a request to successfulHits
   */
  @Test
  public void testHandleSuccessfulHits() {
    // Create sample successful-hits record
    CoordAdapter[] coords = new CoordAdapter[2];
    coords[0] = new CoordAdapter(0, 0);
    coords[1] = new CoordAdapter(1, 1);

    JsonNode jsonNode = createSampleMessage("successful-hits",
        new SuccessfulHitsJson(coords));

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, this.bot);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);
  }

  /**
   * Check that the client returns a proper message when given a request to endGame
   */
  @Test
  public void testEndGame() {
    JsonNode jsonNode = createSampleMessage("end-game",
        new EndGameJson(GameResult.WIN.toString(), "Player sunk all the AI's ships"));

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, this.bot);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    this.controller.run();
    try {
      socket.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the mocket");
    }

    responseToClass(MessageJson.class);

  }

  /**
   * tests that an exception is thrown in the default case of delegateMessage
   */
  @Test
  public void testDelegateMessage() {
    JsonNode jsonNode = createSampleMessage("invalid",
        new BlankStubJson());

    // Create socket with sample input
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    // Create a ProxyController
    try {
      this.controller = new ProxyController(socket, this.bot);
    } catch (IOException e) {
      fail(); // fail if the controller can't be created
    }

    // Run dealer and verify response.
    assertThrows(IllegalStateException.class, () -> this.controller.run());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to the given class, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}