package main.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import main.java.Controller;
import main.java.Document;

public class KarguDesktopGUI extends JFrame implements Observer {

  // GUI components
  private JMenuBar menuBar;
  private JMenu fileMenu, dataMenu, helpMenu;
  private JMenuItem newDocumentItem, newPageItem, openDocumentItem,
      saveDocumentItem, saveDocumentAsItem, printDocumentItem,
      closeDocumentItem, closePageItem, quitAppItem;
  private JMenuItem addEntryItem, editEntryItem, deleteEntryItem, clearEntryItem,
      resultCompleteItem, resultShortItem;
  private JMenuItem manualItem, aboutItem;
  private JPanel controlPanel, managePanel,
      settingsPanel, entryPanel, resultPanel, versionPanel;
  private JTabbedPane tabbedPage;
  private JScrollPane entriesTable;
  private JLabel settingsLabel, groupLabel, powerLabel, entryLabel, resultLabel, versionLabel;
  private JComboBox groupBox;
  private String[] groups = {
      "S1", "S2", "S3",
      "R1", "R2", "R3"
  };
  private JTextField powerField;
  private JButton addEntryButton, editEntryButton, deleteEntryButton, clearEntryButton,
      resultCompleteButton, resultShortButton, newPageButton;
  // Display entries in specific
  private NameListModel nameListModel;
  private JList nameList;
  // The controller that performs operations in response to user action gestures
  private Controller controller;
  // The document this GUI displays/operates on
  private Document document;

  private Object groupRate;
  private int powerLimit;

  /**
   * GUI Constructor
   *
   * @param controller the controller which performs operations in response to user gestures on this
   *                   GUI
   * @param document   the Document this GUI displays
   */

  public KarguDesktopGUI(final Controller controller,
                         final Document document) {

    super("Kargu");
    setResizable(false);
    this.controller = controller;

    // ------------------------------
    // Create and add menu with items
    menuBar = new JMenuBar();

    fileMenu = new JMenu("Berkas");
    newDocumentItem = new JMenuItem("Dokumen Baru", 'N');
    newPageItem = new JMenuItem("Laman Baru", 'M');
    openDocumentItem = new JMenuItem("Buka...", 'O');
    saveDocumentItem = new JMenuItem("Simpan", 'S');
    saveDocumentAsItem = new JMenuItem("Simpan Sebagai...", 'A');
    printDocumentItem = new JMenuItem("Cetak", 'P');
    closeDocumentItem = new JMenuItem("Tutup Dokumen", 'C');
    closePageItem = new JMenuItem("Tutup Laman", 'D');
    quitAppItem = new JMenuItem("Berhenti", 'Q');
    fileMenu.add(newDocumentItem);
    fileMenu.add(newPageItem);
    fileMenu.add(openDocumentItem);
    fileMenu.addSeparator();
    fileMenu.add(saveDocumentItem);
    fileMenu.add(saveDocumentAsItem);
    fileMenu.addSeparator();
    fileMenu.add(printDocumentItem);
    fileMenu.addSeparator();
    fileMenu.add(closeDocumentItem);
    fileMenu.add(closePageItem);
    fileMenu.add(quitAppItem);

    dataMenu = new JMenu("Data");
    addEntryItem = new JMenuItem("Tambah Entri");
    editEntryItem = new JMenuItem("Edit Entri");
    deleteEntryItem = new JMenuItem("Hapus Entri");
    clearEntryItem = new JMenuItem("Kosongkan Entri");
    resultCompleteItem = new JMenuItem("Hasil Lengkap");
    resultShortItem = new JMenuItem("Hasil Singkat");
    dataMenu.add(addEntryItem);
    dataMenu.add(editEntryItem);
    dataMenu.add(deleteEntryItem);
    dataMenu.add(clearEntryItem);
    dataMenu.addSeparator();
    dataMenu.add(resultCompleteItem);
    dataMenu.add(resultShortItem);

    helpMenu = new JMenu("Bantuan");
    manualItem = new JMenuItem("Manual");
    aboutItem = new JMenuItem("Tentang");
    helpMenu.add(manualItem);
    helpMenu.add(aboutItem);

    // Stack menus into menu bar
    menuBar.add(fileMenu);
    menuBar.add(dataMenu);
    menuBar.add(helpMenu);
    setJMenuBar(menuBar);

    // --------------------------
    // Create main panel category
    controlPanel = new JPanel();
    managePanel = new JPanel();

    // ---------------------
    // Part of control panel
    settingsPanel = new JPanel();
    settingsPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createLineBorder(Color.gray, 1)));
    settingsLabel = new JLabel("PENGATURAN");
    groupLabel = new JLabel("Golongan:");
    groupBox = new JComboBox(groups);
    groupBox.setSelectedIndex(0);
    powerLabel = new JLabel("Batas Daya (VA):");
    powerField = new JTextField(
        String.valueOf(document.getVA()));
    settingsPanel.add(settingsLabel);
    settingsPanel.add(groupLabel);
    settingsPanel.add(groupBox);
    settingsPanel.add(powerLabel);
    settingsPanel.add(powerField);

    entryPanel = new JPanel();
    entryPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createLineBorder(Color.gray, 1)));
    entryLabel = new JLabel("ENTRI");
    addEntryButton = new JButton("Tambah");
    editEntryButton = new JButton("Edit");
    deleteEntryButton = new JButton("Hapus");
    clearEntryButton = new JButton("Kosong");
    entryPanel.add(entryLabel);
    entryPanel.add(addEntryButton);
    entryPanel.add(editEntryButton);
    entryPanel.add(deleteEntryButton);
    entryPanel.add(clearEntryButton);

    resultPanel = new JPanel();
    resultPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(5, 10, 10, 10),
        BorderFactory.createLineBorder(Color.gray, 1)));
    resultLabel = new JLabel("HASIL");
    resultCompleteButton = new JButton("Lengkap");
    resultShortButton = new JButton("Singkat");
    resultPanel.add(resultLabel);
    resultPanel.add(resultCompleteButton);
    resultPanel.add(resultShortButton);

    versionPanel = new JPanel();
    versionLabel = new JLabel("Kargu v0.1");
    versionPanel.add(versionLabel);

    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
    entryPanel.setLayout(new BoxLayout(entryPanel, BoxLayout.Y_AXIS));
    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));

    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
    controlPanel.add(settingsPanel);
    controlPanel.add(entryPanel);
    controlPanel.add(resultPanel);

    // --------------------
    // Part of manage panel

    // Tabbed page for contain pages
    // tabbedPage = new JTabbedPane();
    // tabbedPage.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    // addNewPage(tabbedPage, "Page");
    // managePanel.add(tabbedPage, BorderLayout.CENTER);

    // The displayed list of names gets its information from the document
    nameListModel = new NameListModel();
    // The nameListModel and saveDocumentItem objects must exist before this is done;
    // but this must be done before the nameList is created
    setDocument(document);

    nameList = new JList(nameListModel);
    entriesTable = new JScrollPane(nameList);
    nameList.setVisibleRowCount(15);
    entriesTable.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createEmptyBorder(10, 10, 10, 10),
        BorderFactory.createLineBorder(Color.gray, 1)));
    getContentPane().add(entriesTable, BorderLayout.CENTER);

    managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.Y_AXIS));
    // managePanel.add(entriesTable);

    // Add main category panels onto content pane
    getContentPane().add(controlPanel, BorderLayout.WEST);
    getContentPane().add(managePanel, BorderLayout.EAST);

    // ----------------------------------------------------------------
    // Add the action listeners for the buttons, menu items, close box,
    // and for double-clicking the list to edit information

    addEntryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addEntry();
      }
    });

    editEntryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editEntry();
      }
    });

    deleteEntryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteEntry();
      }
    });

    clearEntryButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearEntry();
      }
    });

    resultCompleteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resultComplete();
      }
    });

    resultShortButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resultShort();
      }
    });

    newDocumentItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (getDocument().getChangedSinceLastSave()) {
            controller.doOfferSaveChanges(KarguDesktopGUI.this);
          }
          controller.doNewDocument(KarguDesktopGUI.this);
        } catch (IOException exception) {
          reportError("Bermasalah untuk menulis berkas:\n" + exception);
        } catch (InterruptedException exception) {
          // Thrown if user cancels a save or a file dialog - can be ignored
        } catch (SecurityException exception) {
          // Thrown if security manager disallows the operation -
          // will always happen in an applet
          reportError("Aksi tidak diperbolehkan:\n" + exception);
        }
      }
    });

    openDocumentItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (getDocument().getChangedSinceLastSave()) {
            controller.doOfferSaveChanges(KarguDesktopGUI.this);
          }
          controller.doOpenDocument(KarguDesktopGUI.this);
        } catch (IOException exception) {
          reportError("Bermasalah untuk menulis berkas:\n" + exception);
        } catch (InterruptedException exception) {
          // Thrown if user cancels a save or a file dialog - can be ignored
        } catch (SecurityException exception) {
          // Thrown if security manager disallows the operation -
          // will always happen in an applet
          reportError("Aksi tidak diperbolehkan:\n" + exception);
        } catch (Exception exception) {
          // Any other case means the file did not contain a document or page
          reportError("This file did not contain a document or page");
        }
      }
    });

    saveDocumentItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          controller.doSaveDocument(KarguDesktopGUI.this);
        } catch (IOException exception) {
          reportError("Bermasalah untuk menulis berkas:\n" + exception);
        } catch (InterruptedException exception) {
          // Thrown if user cancels a file dialog - can be ignored
        } catch (SecurityException exception) {
          // Thrown if security manager disallows the operation -
          // will always happen in an applet
          reportError("Aksi tidak diperbolehkan:\n" + exception);
        }
      }
    });

    saveDocumentAsItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          controller.doSaveDocumentAs(KarguDesktopGUI.this);
        } catch (IOException exception) {
          reportError("Bermasalah untuk menulis berkas:\n" + exception);
        } catch (InterruptedException exception) {
          // Thrown if user cancels a file dialog but can be ignored
        } catch (SecurityException exception) {
          // Thrown if security manager disallows the operation
          // It will always happen in an applet
          reportError("Aksi tidak diperbolehkan:\n" + exception);
        }
      }
    });

    printDocumentItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doPrint(KarguDesktopGUI.this);
      }
    });

    quitAppItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        KarguDesktopApp.quit();
      }
    });

    addEntryItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addEntry();
      }
    });

    editEntryItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editEntry();
      }
    });

    deleteEntryItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteEntry();
      }
    });

    clearEntryItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearEntry();
      }
    });

    resultCompleteItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resultComplete();
      }
    });

    resultShortItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        resultShort();
      }
    });

    manualItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doTest(KarguDesktopGUI.this);
      }
    });
    aboutItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        controller.doAbout(KarguDesktopGUI.this);
      }
    });

    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        try {
          if (getDocument().getChangedSinceLastSave()) {
            controller.doOfferSaveChanges(KarguDesktopGUI.this);
          }
          dispose();
          if (Frame.getFrames().length == 0) {
            KarguDesktopApp.quit();
          }
        } catch (IOException exception) {
          reportError("Bermasalah untuk menulis berkas:\n" + exception);
        } catch (InterruptedException exception) {
          // Thrown if user cancels a file dialog but can be ignored
        } catch (SecurityException exception) {
          // Thrown if security manager disallows the operation
          // It will always happen in an applet
          reportError("Aksi tidak diperbolehkan:\n" + exception);
        }
      }
    });

    /**
     * The following is adapted from an example in the documentation
     * for class JList. It invokes the controller's doEdit method
     * if the user double clicks a name.
     */
    nameList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          int index = nameList.locationToIndex(e.getPoint());
          controller.doEdit(KarguDesktopGUI.this, index);
        }
      }
    });
    pack();
    setLocationRelativeTo(null);
  }

  /**
   * Accessor for the document in the GUI
   *
   * @return the current document for this GUI
   */
  public Document getDocument() {
    return document;
  }

  /**
   * Mutator to change the document this GUI displays
   *
   * @param document the new document for this GUI
   */
  public void setDocument(Document document) {
    if (this.document != null) {
      this.document.deleteObserver(this);
    }
    this.document = document;
    document.addObserver(this);
    update(document, null);
  }

  /**
   * Report an error to the user
   *
   * @param message the message to display
   */
  public void reportError(String message) {
    JOptionPane.showMessageDialog(this, message, "Kargu | Galat",
                                  JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Method required by the Observer interface To update the display in response to any change in
   * the document
   */
  public void update(Observable o, Object arg) {
    if (o == document) {
      setTitle("Kargu | " + document.getTitle());
      saveDocumentItem.setEnabled(document.getChangedSinceLastSave());
      nameListModel.contentsChanged();
    }
  }

  /**
   * Class used for the model for the list of persons in the document
   */
  private class NameListModel extends AbstractListModel {

    // Report that the contents of the list have changed
    void contentsChanged() {
      super.fireContentsChanged(this, 0, 0);
    }

    // Implementation of abstract methods of the base class
    public Object getElementAt(int index) {
      return getDocument().getEntryCompleteInfo(index);
    }

    // ...
    public int getSize() {
      return getDocument().getNumberOfEntries();
    }
  }

  /**
   * Custom method to get text of the selected button
   */
  public String getSelectedButtonText(ButtonGroup buttonGroup) {
    Enumeration<AbstractButton> buttons;
    for (buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
      AbstractButton button = buttons.nextElement();
      if (button.isSelected()) {
        return button.getText();
      }
    }
    return null;
  }

  private void newPage() {
    // ...
  }

  private void addEntry() {
    controller.doAdd(KarguDesktopGUI.this);
    int index = getDocument().getNumberOfEntries() - 1;
    // This will ensure that the person just added is visible in list
    nameList.ensureIndexIsVisible(index);
  }

  private void editEntry() {
    int index = nameList.getSelectedIndex();
    if (index < 0) {
      reportError("Pilih entri dahulu");
    } else {
      controller.doEdit(KarguDesktopGUI.this, index);
    }
  }

  private void deleteEntry() {
    int index = nameList.getSelectedIndex();
    if (index < 0) {
      reportError("Pilih entri dahulu");
    } else {
      controller.doDelete(KarguDesktopGUI.this, index);
    }
  }

  private void clearEntry() {
    int num = document.getNumberOfEntries();
    if (num < 1) {
      reportError("Entri sudah kosong");
    } else {
      controller.doClear(KarguDesktopGUI.this, num);
    }
  }

  private void resultComplete() {
    groupRate = groupBox.getSelectedItem();
    powerLimit = Integer.parseInt(powerField.getText());
    int num = document.getNumberOfEntries();
    if (num < 1) {
      reportError("Entri masih kosong");
    } else {
      controller.doResultComplete(KarguDesktopGUI.this, groupRate, powerLimit);
    }
  }

  private void resultShort() {
    groupRate = groupBox.getSelectedItem();
    powerLimit = Integer.parseInt(powerField.getText());
    int num = document.getNumberOfEntries();
    if (num < 1) {
      reportError("Entri masih kosong");
    } else {
      controller.doResultShort(KarguDesktopGUI.this, groupRate, powerLimit);
    }
  }

}
