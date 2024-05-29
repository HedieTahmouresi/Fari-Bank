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

    public void changeName() {
        System.out.println(ColorConsole.CYAN + "Would you like to change the name? (previous name : " + ColorConsole.PURPLE + this.getName() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)) {
            do {
                if (!Input.checkInput(ans)) {
                    return;
                } else if ("yes".equalsIgnoreCase(ans)) {
                    System.out.println(ColorConsole.CYAN + "Write the name you like!" + ColorConsole.RESET);
                    this.setName(Input.inputNextLine());
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
                }
                ans = Input.inputNextLine();
            } while (!"no".equalsIgnoreCase(ans));
        }
    }

    public void changeLastName() {
        System.out.println(ColorConsole.CYAN + "Would you like to change the Last name? (previous last name : " + ColorConsole.PURPLE + this.getSurname() + ColorConsole.CYAN + ")" + ColorConsole.RESET);
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)) {
            do {
                if (!Input.checkInput(ans)) {
                    return;
                } else if ("yes".equalsIgnoreCase(ans)) {
                    System.out.println(ColorConsole.CYAN + "Write the last name you like!" + ColorConsole.RESET);
                    this.setSurname(Input.inputNextLine());
                    return;
                } else {
                    System.out.println(ColorConsole.RED + "wrong input! try again" + ColorConsole.RESET);
                }
                ans = Input.inputNextLine();
            } while (!"no".equalsIgnoreCase(ans));
        }
    }

    @Override
    public String toString() {
        return ColorConsole.PURPLE_BOLD + "Name : " + ColorConsole.PINK + this.getName() +
                ColorConsole.PURPLE_BOLD + ", Last Name : " + ColorConsole.PINK + this.getSurname();
    }
}
