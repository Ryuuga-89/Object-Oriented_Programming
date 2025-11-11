package oop.ex2B3;

import java.util.Arrays;

public class Data {
  public int field;
  public int[] intarray;
  public Data next;

  public Data() {
    field = 0; intarray = null; next = null;
  }

  @Override public String toString() {
    String s = "" + field;
    if(intarray != null) {
      s += "[";
      for(int i : intarray) {
        s += " " + i;
      }
      s += " ]";
    }
    if(next != null) {
      s += " + " + next;
    }
    return s;
  }

  // implements shallow and deep
  public Data shallow() {
    Data new_data = new Data();

    new_data.field = this.field;
    new_data.intarray = this.intarray;
    new_data.next = this.next;

    return new_data;
  }

  public Data deep() {
    Data new_data = new Data();

    new_data.field = this.field;

    if (new_data != null) {
      new_data.intarray = Arrays.copyOf(this.intarray, this.intarray.length);
    }
    
    if (this.next != null) {
      new_data.next = this.next.deep();
    }

    return new_data;
  }
}
