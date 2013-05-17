package main.java;

import java.io.Serializable;

public class Entry implements Serializable {

  private int id;
  private String name;
  private String type;
  private int qty;
  private float watt;
  private float hour;

  /**
   * Entry constructor with no argument
   */
  public Entry() {
    //
  }

  /**
   * Entry constructor with complete arguments
   */
  public Entry(int id, String name, String type,
               int qty, float watt, float hour) {

    this.id = id;
    this.name = name;
    this.type = type;
    this.qty = qty;
    this.hour = hour;
    this.watt = watt;

  }

  /**
   * Various entry attribute getter
   */

  public int getID() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getType() {
    return this.type;
  }

  public int getQty() {
    return this.qty;
  }

  public float getWatt() {
    return this.watt;
  }

  public float getHour() {
    return this.hour;
  }

  /**
   * Update for all entry's information
   */
  public void update(String name, String type,
                     int qty, float watt, float hour) {

    this.name = name;
    this.type = type;
    this.qty = qty;
    this.watt = watt;
    this.hour = hour;

  }

}
