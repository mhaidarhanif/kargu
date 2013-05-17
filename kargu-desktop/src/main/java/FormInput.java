package main.java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * This is a utility class for displaying a dialog that asks for multiple values. Based on ideas in
 * Wu's javabook.FormInputBox class and on ideas in javax.swing.JOptionPane.
 */
public class FormInput extends JOptionPane {

  private JTextField[] fields;
  private boolean ok;

  /**
   * Constructor
   *
   * @param prompts       the prompts to display
   * @param initialValues the initial values to display for each item - this parameter can be null,
   *                      in which case no initial values are specified; or individual elements can
   *                      be null, indicating that no initial value is specified for a particular
   *                      field
   */
  private FormInput(String[] prompts, String[] initialValues) {

    super("Informasi Entri");
    removeAll();

    setLayout(new GridLayout(prompts.length + 1, 2, 5, 5));
    fields = new JTextField[prompts.length];

    for (int i = 0; i < prompts.length; i++) {
      add(new JLabel(prompts[i]));
      fields[i] = new JTextField();
      add(fields[i]);
      if (initialValues != null && initialValues[i] != null) {
        fields[i].setText(initialValues[i]);
      }
    }

    JPanel okPanel = new JPanel();
    JButton okButton = new JButton("Oke");
    okPanel.add(okButton);
    add(okPanel);
    JPanel cancelPanel = new JPanel();
    JButton cancelButton = new JButton("Batal");
    cancelPanel.add(cancelButton);
    add(cancelPanel);

    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ok = true;
        getTopLevelAncestor().setVisible(false);
      }
    });

    cancelButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getTopLevelAncestor().setVisible(false);
      }
    });
  }

  /**
   * Pop up a dialog asking for multiple items of input
   *
   * @param parentComponent the parent Component of the dialog that is shown
   * @param prompts         the prompts to display
   * @param initialValues   the initial values to display for each item - this parameter can be
   *                        null, in which case no initial values are specified; or individual
   *                        elements can be null, indicating that no initial value is specified for
   *                        a particular field
   * @param title           the title for this dialog
   * @return an array of values corresponding to the various prompts, or null if the user cancelled
   */
  public static String[] showFormInputDialog(Component parentComponent,
                                             String[] prompts,
                                             String[] initialValues,
                                             String title) {

    FormInput fi = new FormInput(prompts, initialValues);
    JDialog dialog =
        fi.createDialog(parentComponent,
                        title != null ? title : "Formulir Entri");
    dialog.pack();
    dialog.setVisible(true);

    if (!fi.ok) {
      return null;
    }

    String[] results = new String[prompts.length];
    for (int i = 0; i < prompts.length; i++) {
      results[i] = fi.fields[i].getText();
    }

    return results;
  }

  /**
   * Pop up a dialog asking for multiple items of input
   *
   * @param parentComponent the parent Component of the dialog that is shown
   * @param prompts         the prompts to display
   * @param title           the title for this dialog
   * @return an array of values corresponding to the various prompts, or null if the user cancelled
   */
  public static String[] showFormInputDialog(Component parentComponent,
                                             String[] prompts,
                                             String title) {
    return showFormInputDialog(parentComponent, prompts, null, title);
  }

  /**
   * Pop up a dialog asking for multiple items of input
   *
   * @param parentComponent the parent Component of the dialog that is shown
   * @param prompts         the prompts to display
   * @param initialValues   the initial values to display for each item - this parameter can be
   *                        null, in which case no initial values are specified; or individual
   *                        elements can be null, indicating that no initial value is specified for
   *                        a particular field
   * @return an array of values corresponding to the various prompts, or null if the user cancelled
   */
  public static String[] showFormInputDialog(Component parentComponent,
                                             String[] prompts,
                                             String[] initialValues) {
    return showFormInputDialog(parentComponent, prompts, initialValues, null);
  }

  /**
   * Pop up a dialog asking for multiple items of input
   *
   * @param parentComponent the parent Component of the dialog that is shown
   * @param prompts         the prompts to display
   * @return an array of values corresponding to the various prompts, or null if the user cancelled
   */
  public static String[] showFormInputDialog(Component parentComponent,
                                             String[] prompts) {
    return showFormInputDialog(parentComponent, prompts, null, null);
  }
  
}

