package main.gui;

import java.awt.*;
import java.awt.event.WindowEvent;

import main.java.Controller;
import main.java.Database;
import main.java.Document;

public class KarguDesktopApp {

  /**
   * Main method for program
   * Initiate application GUI
   */
  public static void main(String[] args) {
    Database db = new Database();
    Controller controller = new Controller(db);
    KarguDesktopGUI gui = new KarguDesktopGUI(controller, new Document());
    gui.setSize(600, 350);
    gui.setVisible(true);
  }

  /**
   * Terminate the application
   * (unless cancelled by the user)
   */
  public static void quit() {

    // When the user requests to quit the application, any open
    // windows must be closed
    Frame[] openWindows = Frame.getFrames();

    for (int i = 0; i < openWindows.length; i++) {
      // Attempt to close any window that belongs to this program
      if (openWindows[i] instanceof KarguDesktopGUI) {
        openWindows[i].dispatchEvent(new WindowEvent(
            openWindows[i],
            WindowEvent.WINDOW_CLOSING));
        // If the window is still showing, this means that this attempt
        // to close the window was cancelled by the user
        // so abort the quit operation
        if (openWindows[i].isShowing()) {
          return;
        }
      }
    }

    // Terminate application after all open windows have been successfully closed
    System.exit(0);

  }

}
