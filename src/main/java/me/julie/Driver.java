package me.julie;

import java.io.IOException;
import java.net.Socket;
import me.julie.controller.ProxyController;
import me.julie.model.Bot;
import me.julie.model.Player;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - args[0] is the host of the server, args[1] is the port number
   */
  public static void main(String[] args) {
    try {
      Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
      Player player = new Bot();
      ProxyController controller = new ProxyController(socket, player);
      controller.run();
    } catch (IOException exc) {
      System.err.println("IOException thrown in ProxyController: " + exc);
    }
  }
}
