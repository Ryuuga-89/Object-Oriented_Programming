package oop.ex2B1.existingpackage;

public class Student {
  final private String name;
  final private int entryYear;
  final private int studentNumber;

  public Student(String name, int entryYear, int studentNumber) {
    this.name = name;
    this.entryYear = entryYear;
    this.studentNumber = studentNumber;
  }

  public String getName() {
    return name;
  }

  public String getStudentID() {
    char sep = 'B';
    if(entryYear < 16) sep = '_';
    return String.format("%02d%c%05d", entryYear, sep, studentNumber);
  }
}
