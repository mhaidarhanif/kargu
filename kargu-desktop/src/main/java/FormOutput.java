package main.java;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FormOutput extends JOptionPane {

  private JTextField[] fields;
  private boolean ok;
  
  private FormOutput(String[] prompts, String[] initialValues) {

    super("Kargu | Informasi");
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

  public static String[] showFormOutputDialog(Component parentComponent,
                                             String[] prompts,
                                             String[] initialValues,
                                             String title) {

    FormOutput fo = new FormOutput(prompts, initialValues);
    JDialog dialog =
        fo.createDialog(parentComponent,
                        title != null ? title : "Formulir Entri");
    dialog.pack();
    dialog.setVisible(true);

    if (!fo.ok) {
      return null;
    }

    String[] results = new String[prompts.length];
    for (int i = 0; i < prompts.length; i++) {
      results[i] = fo.fields[i].getText();
    }

    return results;
  }

  public static String[] showFormOutputDialog(Component parentComponent,
                                             String[] prompts,
                                             String title) {
    return showFormOutputDialog(parentComponent, prompts, null, title);
  }

  public static String[] showFormOutputDialog(Component parentComponent,
                                             String[] prompts,
                                             String[] initialValues) {
    return showFormOutputDialog(parentComponent, prompts, initialValues, null);
  }

  public static String[] showFormOutputDialog(Component parentComponent,
                                             String[] prompts) {
    return showFormOutputDialog(parentComponent, prompts, null, null);
  }  

}
