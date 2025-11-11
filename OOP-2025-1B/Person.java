public class Person {
  // (1)
  protected String name = "name";
  
  // (2)
  public String getName() {
    return this.name;
  }

  // (3)
  public void setName(String name) {
    this.name = name;
  }
}