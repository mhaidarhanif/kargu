package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Database {

  public Document readFile(File file)
      throws IOException, ClassCastException, ClassNotFoundException {

    ObjectInputStream
        localObjectInputStream =
        new ObjectInputStream(new FileInputStream(file));
    Document localDocument = (Document) localObjectInputStream.readObject();
    localDocument.setChanged(false);
    localDocument.setFile(file);
    return localDocument;

  }

  public void saveFile(Document doc, File file)
      throws IOException {

    ObjectOutputStream
        localObjectOutputStream =
        new ObjectOutputStream(new FileOutputStream(file));
    localObjectOutputStream.writeObject(doc);
    doc.setChanged(false);
    doc.setFile(file);

  }

}

