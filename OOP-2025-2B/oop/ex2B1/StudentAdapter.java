package oop.ex2B1;
import oop.ex2B1.existingpackage.Student;

public class StudentAdapter implements Identifiable {
    final private Student student;

    public StudentAdapter(String name, int entryYear, int studentNumber) {
        this.student = new Student(name, entryYear, studentNumber);
    }

    public String getName() {return student.getName();}

    public String getID() {return student.getStudentID();}

}