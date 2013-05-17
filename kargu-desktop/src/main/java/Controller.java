package main.java;

import java.io.File;
import java.io.IOException;

import javax.swing.*;

import main.gui.KarguDesktopGUI;

public class Controller {

  private Database db;

  /**
   * Document with file system constructor
   */
  public Controller(Database db) {
    this.db = db;
  }

  /**
   * Create new document from GUI
   */
  public void doNewDocument(KarguDesktopGUI gui) {
    gui.setDocument(new Document());
  }

  /**
   * Create new page from GUI
   */
  public void doNewPage(KarguDesktopGUI gui) {
    // gui.setPage(new Page());
  }

  /**
   * Open existing document from GUI
   */
  public void doOpenDocument(KarguDesktopGUI gui)
      throws IOException, ClassCastException, ClassNotFoundException,
             InterruptedException, SecurityException {

    JFileChooser localJFileChooser = new JFileChooser(System.getProperty("user.dir"));

    if (localJFileChooser.showOpenDialog(gui) == 0) {
      File localFile = localJFileChooser.getSelectedFile();
      gui.setDocument(this.db.readFile(localFile));
    } else {
      throw new InterruptedException("Pembukaan dibatalkan pengguna");
    }

  }

  /**
   * Save active document including available entry from GUI
   */
  public void doSaveDocument(KarguDesktopGUI gui)
      throws IOException, InterruptedException, SecurityException {
    File localFile = gui.getDocument().getFile();
    if (localFile == null) {
      doSaveDocumentAs(gui);
    } else {
      this.db.saveFile(gui.getDocument(), localFile);
    }
  }

  /**
   * Save new active document including available entry from GUI
   */
  public void doSaveDocumentAs(KarguDesktopGUI gui)
      throws IOException, InterruptedException, SecurityException {
    JFileChooser localJFileChooser = new JFileChooser(System.getProperty("user.dir"));
    if (localJFileChooser.showSaveDialog(gui) == 0) {
      File localFile = localJFileChooser.getSelectedFile();
      this.db.saveFile(gui.getDocument(), localFile);
    } else {
      throw new InterruptedException("Simpan dokumen sebagai dibatalkan pengguna");
    }
  }

  /**
   * Offer to save new changed document including edited entry from GUI
   */
  public void doOfferSaveChanges(KarguDesktopGUI gui)
      throws InterruptedException, IOException, SecurityException {
    int i =
        JOptionPane.showConfirmDialog(gui, "Ada perubahan yang belum disimpan. Simpan?",
                                      "Simpan perubahan", 1);
    // choice option condition
    switch (i) {
      case 1:
        break;
      case 0:
        doSaveDocument(gui);
        break;
      case 2:
      default:
        throw new InterruptedException("Simpan perubahan dibatalkan pengguna");
    }

  }

  /**
   * Print all entry information in active document from GUI
   */
  public void doPrint(KarguDesktopGUI gui) {
    // get output then store to be printed
    gui.getDocument().getEntriesAll();
    // print on both system and GUI
    gui.getDocument().printSystem();
    gui.getDocument().printFrame();
  }

  /**
   * Add entry from GUI
   */
  public void doAdd(KarguDesktopGUI gui) {
    String[] prop =
        {"ID", "Nama", "Jenis", "Jumlah", "Watt", "Jam"};
    String[] entry =
        FormInput.showFormInputDialog(gui, prop, "Tambah Entri");
    if (entry != null) {
      gui.getDocument()
          .addEntry(Integer.parseInt(entry[0]), // ID
                    // name and type
                    entry[1], entry[2],
                    // quantity
                    Integer.parseInt(entry[3]),
                    // watt and hour
                    Float.parseFloat(entry[4]),
                    Float.parseFloat(entry[5]));
    }
  }

  /**
   * Edit from GUI by edit with corresponding entry's index
   */
  public void doEdit(KarguDesktopGUI gui, int index) {
    String name = gui.getDocument().getNameOfEntry(index);
    String[] prop =
        {"Nama", "Jenis", "Jumlah", "Watt", "Jam"};
    String[] info =
        gui.getDocument().getEntryInfo(index);
    String[] entry =
        FormInput
            .showFormInputDialog(gui, prop, info, "Edit Entri " + name);
    if (entry != null) {
      gui.getDocument()
          .editEntry(index,
                     // name and type
                     entry[0], entry[1],
                     // quantity
                     Integer.parseInt(entry[2]),
                     // watt and hour
                     Float.parseFloat(entry[3]),
                     Float.parseFloat(entry[4]));
    }
  }

  /**
   * Delete entry from GUI
   */
  public void doDelete(KarguDesktopGUI gui, int id) {
    String name = gui.getDocument().getNameOfEntry(id);
    if (JOptionPane
            .showConfirmDialog(gui, "Yakin ingin menghapus " + name + "?",
                               "Konfirmasi Hapus", 0) == 0) {
      gui.getDocument().removeEntry(id);
    }
  }

  /**
   * Clear all entries from GUI
   */
  public void doClear(KarguDesktopGUI gui, int num) {
    if (JOptionPane
            .showConfirmDialog(gui, "Yakin laman dengan " + num + " entri ingin dikosongkan?",
                               "Konfirmasi Pengosongan", 0) == 0) {
      gui.getDocument().clearEntry();
    }
  }

  /**
   * Calculate result with complete information
   */
  public void doResultComplete(KarguDesktopGUI gui, Object group, int power) {
    gui.getDocument().calculateResult(group, power);
    gui.getDocument().displayResultComplete();

    gui.getDocument().printSystem();
    gui.getDocument().printFrame();
  }

  /**
   * Calculate result with short information
   */
  public void doResultShort(KarguDesktopGUI gui, Object group, int power) {
    gui.getDocument().calculateResult(group, power);
    gui.getDocument().displayResultShort();

    gui.getDocument().printSystem();
    gui.getDocument().printFrame();
  }

  /**
   * Test with prepared entries
   */
  public void doTest(KarguDesktopGUI gui) {
    gui.getDocument().setupTest();
  }

  /**
   * About and credits
   */
  public void doAbout(KarguDesktopGUI gui) {
    JOptionPane.showMessageDialog(gui,
                                  "M Haidar Hanif (2013)\n" +
                                  "<mhaidarhanif@gmail.com>\n" +
                                  "https://github.com/mhaidarh/kargu");
  }

}
