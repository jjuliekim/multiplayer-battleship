package me.julie.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import me.julie.model.Coord;
import me.julie.model.CoordAdapter;
import me.julie.model.EndGameJson;
import me.julie.model.GameResult;
import me.julie.model.JoinJson;
import me.julie.model.JsonUtils;
import me.julie.model.MessageJson;
import me.julie.model.Player;
import me.julie.model.ReportDamageClientJson;
import me.julie.model.ReportDamageServerJson;
import me.julie.model.SetupClientJson;
import me.julie.model.SetupServerJson;
import me.julie.model.Ship;
import me.julie.model.ShipAdapter;
import me.julie.model.SuccessfulHitsJson;
import me.julie.model.TakeShotsJson;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyController.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if an error occurs while reading or writing
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.err.println("IOException is thrown from parsing malformed JSON.");
    }
  }

  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    switch (name) {
      case "join" -> handleJoin();
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> handleTakeShots();
      case "report-damage" -> handleReportDamage(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndGame(arguments);
      default -> throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Serializes the player's username and game type to Json and sends the response to the server.
   */
  private void handleJoin() {
    JoinJson response = new JoinJson("jjuliekim", "MULTI");
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("join", jsonResponse);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);

    this.out.println(serializedMessage);
  }

  /**
   * Parses the given message arguments as a SetupServerJSON type and serializes
   * the player's fleet to Json and sends the response to the server.
   *
   * @param arguments the Json representation of a SetupServerJSON
   */
  private void handleSetup(JsonNode arguments) {
    SetupServerJson setupArgs = this.mapper.convertValue(arguments, SetupServerJson.class);
    List<Ship> ships =
        this.player.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleetSpec());
    ShipAdapter[] shipAdapters = new ShipAdapter[ships.size()];
    ShipAdapter testAdapter = new ShipAdapter(
        new CoordAdapter(0, 0), 5, "VERTICAL");

    for (int i = 0; i < ships.size(); i += 1) {
      Ship s = ships.get(i);
      ShipAdapter adaptedShip = new ShipAdapter(new CoordAdapter(s.getPosition().get(0).getX(),
          s.getPosition().get(0).getY()), s.getType().getLength(), testAdapter.getDirection(s));
      shipAdapters[i] = adaptedShip;
    }

    SetupClientJson response = new SetupClientJson(shipAdapters);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("setup", jsonResponse);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);

    this.out.println(serializedMessage);
  }

  /**
   * Serializes the player's volley to Json and sends the response to the server.
   */
  private void handleTakeShots() {
    List<Coord> playerShots = this.player.takeShots();
    CoordAdapter[] shots = new CoordAdapter[playerShots.size()];
    for (int i = 0; i < playerShots.size(); i += 1) {
      shots[i] = new CoordAdapter(playerShots.get(i).getX(), playerShots.get(i).getY());
    }

    TakeShotsJson response = new TakeShotsJson(shots);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("take-shots", jsonResponse);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);

    this.out.println(serializedMessage);
  }

  /**
   * Parses the given message arguments as a ReportDamageJSON type and serializes the
   * shots to Json and sends the response to the server.
   *
   * @param arguments the Json representation of a ReportDamageJson
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageServerJson damageArgs =
        this.mapper.convertValue(arguments, ReportDamageServerJson.class);
    CoordAdapter[] damageArgsArray = damageArgs.coordinates();
    List<Coord> damageArgsList = new ArrayList<>();
    for (CoordAdapter coordAdapter : damageArgsArray) {
      damageArgsList.add(new Coord(coordAdapter.getX(), coordAdapter.getY()));
    }

    List<Coord> shots = this.player.reportDamage(damageArgsList);
    CoordAdapter[] coords = new CoordAdapter[shots.size()];
    for (int i = 0; i < shots.size(); i += 1) {
      coords[i] = new CoordAdapter(shots.get(i).getX(), shots.get(i).getY());
    }

    ReportDamageClientJson response = new ReportDamageClientJson(coords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("report-damage", jsonResponse);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);

    this.out.println(serializedMessage);
  }

  /**
   * Parses the given message arguments as a SuccessfulHitsJson type and serializes the
   * successful shots to Json and sends the response to the server.
   *
   * @param arguments the Json representation of a SuccessfulHitsJson
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHits = this.mapper.convertValue(arguments,
        SuccessfulHitsJson.class);
    CoordAdapter[] successfulHitCoordAdapters = successfulHits.coordinates();
    List<Coord> successfulHitsCoords = new ArrayList<>();
    for (CoordAdapter c : successfulHitCoordAdapters) {
      successfulHitsCoords.add(new Coord(c.getX(), c.getY()));
    }

    this.player.successfulHits(successfulHitsCoords);

    MessageJson messageJson = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);

    this.out.println(serializedMessage);
  }

  /**
   * Parses the given message arguments as a EndGameJSON type.
   *
   * @param arguments the Json representation of a EndGameJson
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    this.player.endGame(GameResult.valueOf(endGameArgs.result()), endGameArgs.reason());

    MessageJson messageJson = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode serializedMessage = JsonUtils.serializeRecord(messageJson);
    this.out.println(serializedMessage);

    try {
      this.server.close();
    } catch (IOException exc) {
      System.err.println("Error while trying to close the socket");
    }
  }
}
