package oop.ex3B1;

public class Replacer {

  private int year;
  private int month;
  private int day;

  public Replacer(int year, int month, int day) {
    this.year = year;
    this.month = month;
    this.day = day;
  }

  public String replace(String template) {
    template = template.replace("YYYY", String.format("%04d", year));
    template = template.replace("MM", String.format("%02d", month));
    template = template.replace("DD", String.format("%02d", day));
    return template;
  }


}
