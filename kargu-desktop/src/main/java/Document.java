package main.java;

import java.io.File;
import java.io.Serializable;
import java.util.Observable;
import java.util.Vector;

import javax.swing.*;

public class Document extends Observable implements Serializable {

  private Vector collection;
  private transient File file;
  private transient boolean changedSinceLastSave;
  private String output;
  private String groupRate;
  private float VA;
  private float KVA;
  private int numEntries;

  private float WH;
  private float dailyWH;
  private float dailyKWH;
  private float monthlyKWH;

  private float activeHour; // Jam Nyala
  private int subscriptionCost;
  private int usageFeePrepaid;
  private int usageFeePostpaid;
  private float burdenCost;
  private float minAccount; // Rekening Minimum

  private float monthlyCostWithoutTaxPrepaid;
  private float monthlyCostWithoutTaxPostpaid;
  private float monthlyTax;
  private float monthlyCostPrepaid;
  private float monthlyCostPostpaid;

  public Document() {
    this.collection = new Vector();
    this.file = null;
    this.changedSinceLastSave = false;
    this.output = null;

    this.groupRate = null;
    this.VA = 0;
    this.KVA = 0;
    this.numEntries = 0;

    this.WH = 0;
    this.dailyWH = 0;
    this.dailyKWH = 0;
    this.monthlyKWH = 0;

    this.activeHour = 0;
    this.minAccount = 0;

    this.subscriptionCost = 0;
    this.usageFeePrepaid = 0;
    this.usageFeePostpaid = 0;
    this.burdenCost = 0;

    this.monthlyCostWithoutTaxPrepaid = 0;
    this.monthlyCostWithoutTaxPostpaid = 0;
    this.monthlyTax = 0;
    this.monthlyCostPrepaid = 0;
    this.monthlyCostPostpaid = 0;
  }

  private void resetResult() {
    this.groupRate = null;
    this.VA = 0;
    this.KVA = 0;
    this.numEntries = 0;

    this.WH = 0;
    this.dailyWH = 0;
    this.dailyKWH = 0;
    this.monthlyKWH = 0;

    this.activeHour = 0;
    this.minAccount = 0;

    this.subscriptionCost = 0;
    this.usageFeePrepaid = 0;
    this.usageFeePostpaid = 0;
    this.burdenCost = 0;

    this.monthlyCostWithoutTaxPrepaid = 0;
    this.monthlyCostWithoutTaxPostpaid = 0;
    this.monthlyTax = 0;
    this.monthlyCostPrepaid = 0;
    this.monthlyCostPostpaid = 0;
  }

  // Enumerators

  // -------------------------------------
  // Setters

  private enum GroupRate {
    S1, S2, S3,
    R1, R2, R3
  }

  public void setGroupRate(Object group) {
    this.groupRate = (String) group;
  }

  public void setVA(int power) {
    this.VA = power;
  }

  public void setKVA() {
    this.KVA = getVA() / 1000;
    System.out.println(">>> " + VA);
    System.out.println(">>> " + KVA);
  }

  public void setNumberOfEntries() {
    this.numEntries = this.collection.size();
  }

  public void setWH() {
    this.WH = getDailyWH() / 24;
  }

  public void setDailyWH() {
    for (int i = 0; i < this.collection.size(); i++) {
      Entry localEntry = (Entry) this.collection.elementAt(i);
      this.dailyWH += (localEntry.getQty() * localEntry.getWatt() * localEntry.getHour());
    }
  }

  public void setDailyKWH() {
    this.dailyKWH = getDailyWH() / 1000;
  }

  public void setMonthlyKWH() {
    this.monthlyKWH = getDailyKWH() * 30;
  }

  // Critical calculation
  public void setDocumentGroup() {
    GroupRate g = GroupRate.valueOf(this.groupRate);

    switch (g) {
      case S1:
        setSubscriptionCost(14800);
        break;
      case S2:
        if (getVA() >= 450) {
          setUsageFeePrepaid(325);
          setUsageFeePostpaid(360);
          setBurdenCost(10000);
        } else if (getVA() >= 900) {
          setUsageFeePrepaid(455);
          setUsageFeePostpaid(360);
          setBurdenCost(15000);
        } else if (getVA() >= 1300) {
          setUsageFeePrepaid(654);
          setUsageFeePostpaid(654);
          setBurdenCost(getMinAccount());
        } else if (getVA() >= 2200) {
          setUsageFeePrepaid(703);
          setUsageFeePostpaid(703);
          setBurdenCost(getMinAccount());
        } else if (getVA() >= 3500) {
          setUsageFeePrepaid(824);
          setUsageFeePostpaid(824);
          setBurdenCost(getMinAccount());
        }
        break;
      case S3:
        setUsageFeePostpaid(839);
        setBurdenCost(getMinAccount());
        break;
      case R1:
        setUsageFeePrepaid(415);
        setUsageFeePostpaid(495);
        setBurdenCost(11000);
        break;
      case R2:
        if (getVA() >= 900) {
          setUsageFeePrepaid(605);
          setUsageFeePostpaid(495);
          setBurdenCost(20000);
        } else if (getVA() >= 1300) {
          setUsageFeePrepaid(879);
          setUsageFeePostpaid(879);
          setBurdenCost(getMinAccount());
        } else if (getVA() >= 2200) {
          setUsageFeePrepaid(893);
          setUsageFeePostpaid(893);
          setBurdenCost(getMinAccount());
        } else if (getVA() >= 3500) {
          setUsageFeePrepaid(1009);
          setUsageFeePostpaid(1009);
          setBurdenCost(getMinAccount());
        }
        break;
      case R3:
        setUsageFeePrepaid(1342);
        setUsageFeePostpaid(1380);
        setBurdenCost(getMinAccount());
        break;
      default:
        System.out.println("Golongan tarif tidak dikenali");
    }
  }

  public void setActiveHour() {
    this.activeHour = getMonthlyKWH() / getKVA();
  }

  public void setMinAccount() {
    this.minAccount = getActiveHour() * getKVA() * getUsageFeePostpaid();
  }

  public void setSubscriptionCost(int num) {
    this.subscriptionCost = num;
  }

  public void setUsageFeePrepaid(int num) {
    this.usageFeePrepaid = num;
  }

  public void setUsageFeePostpaid(int num) {
    this.usageFeePostpaid = num;
  }

  public void setBurdenCost(float num) {
    this.burdenCost = num;
  }

  public void setMonthlyCostWithoutTaxPrepaid() {
    this.monthlyCostWithoutTaxPrepaid =
        getUsageFeePrepaid() * getMonthlyKWH();
  }

  public void setMonthlyCostWithoutTaxPostpaid() {
    this.monthlyCostWithoutTaxPostpaid =
        (getUsageFeePostpaid() * getMonthlyKWH())
        + (getBurdenCost() * getKVA());
  }

  public void setMonthlyTax() {
    this.monthlyTax = 10;
  }

  public void setMonthlyCostPrepaid() {
    this.monthlyCostPrepaid =
        getMonthlyCostWithoutTaxPrepaid() +
        getMonthlyCostWithoutTaxPrepaid() * getMonthlyTax() / 100;
  }

  public void setMonthlyCostPostpaid() {
    this.monthlyCostPostpaid =
        getMonthlyCostWithoutTaxPostpaid() +
        getMonthlyCostWithoutTaxPostpaid() * getMonthlyTax() / 100;
  }

  // ------------------------------------------------------------
  // Getters

  public String getGroupRate() {
    return this.groupRate;
  }

  public float getVA() {
    return this.VA;
  }

  public float getKVA() {
    return this.KVA;
  }

  public int getNumberOfEntries() {
    // return this.numEntries;
    return this.collection.size();
  }

  public float getWH() {
    return this.WH;
  }

  public float getDailyWH() {
    return this.dailyWH;
  }

  public float getDailyKWH() {
    return this.dailyKWH;
  }

  public float getMonthlyKWH() {
    return this.monthlyKWH;
  }

  public float getActiveHour() {
    return this.activeHour;
  }

  public int getSubscriptionCost() {
    return this.subscriptionCost;
  }

  public int getUsageFeePrepaid() {
    return this.usageFeePrepaid;
  }

  public int getUsageFeePostpaid() {
    return this.usageFeePostpaid;
  }

  public float getBurdenCost() {
    return this.burdenCost;
  }

  public float getMinAccount() {
    return this.minAccount;
  }

  public float getMonthlyCostWithoutTaxPrepaid() {
    return this.monthlyCostWithoutTaxPrepaid;
  }

  public float getMonthlyCostWithoutTaxPostpaid() {
    return this.monthlyCostWithoutTaxPostpaid;
  }

  public float getMonthlyTax() {
    return this.monthlyTax;
  }

  public float getMonthlyCostPrepaid() {
    return this.monthlyCostPrepaid;
  }

  public float getMonthlyCostPostpaid() {
    return this.monthlyCostPostpaid;
  }

  public void addEntry(int id, String name, String type,
                       int qty, float watt, float hour) {
    this.collection.addElement(
        new Entry(id, name, type, qty, watt, hour));
    setChanged(true);
  }

  // Actions

  /**
   * Used to get entry's name
   */
  public String getNameOfEntry(int id) {
    Entry localEntry = (Entry) this.collection.elementAt(id);
    return localEntry.getName();
  }

  /**
   * Used to get all entry information
   */
  public String getEntryCompleteInfo(int id) {
    Entry localEntry = (Entry) this.collection.elementAt(id);
    return localEntry.getID() + ": " +
           localEntry.getName() + " (" + localEntry.getType() + "), @ " +
           localEntry.getQty() + " = " +
           localEntry.getWatt() + " Watt & " + localEntry.getHour() + " Jam";
  }

  /**
   * Used to get only the entry properties
   */
  public String[] getEntryInfo(int id) {
    Entry localEntry = (Entry) this.collection.elementAt(id);
    String[]
        entryStrings =
        {localEntry.getName(), localEntry.getType(),
         String.valueOf(localEntry.getQty()),
         String.valueOf(localEntry.getWatt()),
         String.valueOf(localEntry.getHour())};
    return entryStrings;
  }

  public void editEntry(int id, String name, String type,
                        int qty, float watt, float hour) {
    ((Entry) this.collection.elementAt(id))
        .update(name, type, qty, watt, hour);
    setChanged(true);
  }

  public void removeEntry(int id) {
    this.collection.removeElementAt(id);
    setChanged(true);
  }

  public void clearEntry() {
    this.collection.removeAllElements();
    setChanged(true);
  }

  /**
   * Get entries in complete format and store it in output
   */
  public void getEntriesAll() {
    this.output = "Document/Page: " + this.getTitle() + "\n"; // reset output storage first
    for (int i = 0; i < this.collection.size(); i++) {
      Entry localEntry = (Entry) this.collection.elementAt(i);
      this.output += "\n" + localEntry.getID();
      this.output += "\n" + localEntry.getName() + " (" + localEntry.getType() + ")";
      this.output += " @ " + localEntry.getQty();
      this.output += "\n" + localEntry.getWatt() + " Watt";
      this.output += " & " + localEntry.getHour() + " Jam" + "\n";
    }
  }

  /**
   * Print output on system terminal/console which only visible when launched via command line
   */
  public void printSystem() {
    System.out.println(output);
  }

  /**
   * Print output on pop-up GUI frame
   */
  public void printFrame() {
    JOptionPane.showMessageDialog(null, output);
  }

  public File getFile() {
    return this.file;
  }

  public void setFile(File doc) {
    this.file = doc;
    setChanged();
    notifyObservers();
  }

  public String getTitle() {
    // when the document is new or none
    if (this.file == null) {
      return "Tanpa Nama";
    }
    // when the document is exists
    return this.file.getName();
  }

  public boolean getChangedSinceLastSave() {
    return this.changedSinceLastSave;
  }

  public void setChanged(boolean changed) {
    this.changedSinceLastSave = changed;
    setChanged();
    notifyObservers();
  }

  public void calculateResult(Object group, int power) {
    this.resetResult();

    this.setGroupRate(group);
    this.setVA(power);
    this.setKVA();
    this.setNumberOfEntries();

    this.setDailyWH();
    this.setWH();
    this.setDailyKWH();
    this.setMonthlyKWH();

    this.setActiveHour();
    this.setMinAccount();
    this.setDocumentGroup(); // determinator
    this.setMonthlyCostWithoutTaxPrepaid();
    this.setMonthlyCostWithoutTaxPostpaid();
    this.setMonthlyTax();
    this.setMonthlyCostPrepaid();
    this.setMonthlyCostPostpaid();
  }

  public void displayResultComplete() {
    this.output = "[Laporan Kargu]\nLaman: " + this.getTitle();

    this.output += "\n\nGolongan Tarif: " + this.getGroupRate();
    this.output += "\nBatas Daya: " + this.getVA() + " VA";
    this.output += "\nJumlah Elektronik: " + this.getNumberOfEntries();

    // this.output += "\n\nJam Nyala: " + this.getActiveHour(); // Infinity problem
    this.output += "\nWatt/Jam: " + this.getWH();
    this.output += "\nWatt/Jam Sehari: " + this.getDailyWH();
    this.output += "\nkWh Sehari: " + this.getDailyKWH();
    this.output += "\nkWh Sebulan: " + this.getMonthlyKWH();
    this.output += "\nBiaya Abonemen: Rp " + this.getSubscriptionCost();

    this.output += "\n\n[Biaya Pemakaian]";
    this.output += "\nPrabayar: Rp " + this.getUsageFeePrepaid();
    this.output += "\nPascabayar: Rp " + this.getUsageFeePostpaid();

    this.output += "\n\nBiaya Beban/kWh: " + this.getBurdenCost();

    this.output += "\n\n[Biaya/Bulan tanpa Pajak]";
    this.output += "\nPrabayar: Rp " + this.getMonthlyCostWithoutTaxPrepaid();
    this.output += "\nPascabayar: Rp " + this.getMonthlyCostWithoutTaxPostpaid();

    this.output += "\n\nPajak/Bulan: Rp " + this.getMonthlyTax() + " %";

    this.output += "\n\n[Biaya/Bulan]";
    this.output += "\nPrabayar: Rp " + this.getMonthlyCostPrepaid();
    this.output += "\nPascabayar: Rp " + this.getMonthlyCostPostpaid();
  }

  public void displayResultShort() {
    this.output = "[Laporan Kargu]\nLaman: " + this.getTitle();

    this.output += "\n\nGolongan Tarif: " + this.getGroupRate();
    this.output += "\nBatas Daya: " + this.getVA() + "VA";

    this.output += "\n\n[Biaya/Bulan]";
    this.output += "\nPrabayar: Rp " + this.getMonthlyCostPrepaid();
    this.output += "\nPascabayar: Rp " + this.getMonthlyCostPostpaid();
  }

  public void setupTest() {
    addEntry(1, "Laptop", "Komputer", 1, 80, 15);
    addEntry(2, "PC Desktop", "Komputer", 1, 100, 9);
    addEntry(3, "Kotak Es", "Kulkas", 1, 200, 24);
    addEntry(4, "Lampu Ruangan", "Lampu LED", 2, 15, 7);
    addEntry(5, "Lampu Luar", "Lampu", 1, 100, 6);
    addEntry(6, "Televisi 18\"", "Monitor", 1, 120, 2);
    addEntry(7, "AC Tembok", "AC", 1, 500, 6);
    addEntry(8, "Lainnya", "", 1, 20, 18);
  }

}

