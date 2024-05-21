package ir.ac.kntu;

import java.util.Objects;

public class Person {
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Person(String name, String lastName) {
        setName(name);
        setLastName(lastName);
    }
}
