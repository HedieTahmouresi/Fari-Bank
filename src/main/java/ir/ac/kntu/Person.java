package ir.ac.kntu;


public class Person {
    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String lastName) {
        this.surname = lastName;
    }

    public Person(String name, String surame) {
        setName(name);
        setSurname(surame);
    }
}
