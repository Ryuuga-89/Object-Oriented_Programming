package oop.ex2B2;

public class MyBoolean {
  private int value;

  public MyBoolean(int value) {
    this.value = value;
  }

  public boolean getValue() {
    if (value == 0) {
      return false;
    } else {
      return true;
    }
  }

  public void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    String b = String.valueOf(getValue());
    return String.format("%s (%d)", b, this.value);
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof MyBoolean)) return false;
    return ((MyBoolean) object).getValue() == this.getValue();
  }

  @Override
  public int hashCode() {
    return getValue() ? 1 : 0;
  }

}
