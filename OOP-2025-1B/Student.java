class Student extends Person {
    String id;

    Student(String name, String id) {
        this.name = name;
        this.id = id;
    }

    String getID() {
        return this.id;
    }

    void setID(String id) {
        this.id = id;
    }

    void introduce() {
        System.out.printf("My name is %s%nMy student ID is %s%n", this.name, this.id);
    }
}