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

    public void changeName(){
        System.out.println("Would you like to change the name? (previous name : " + this.getName() + ")");
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)){
            do {
                if("return".equalsIgnoreCase(ans)){
                    return;
                }else if("quit".equalsIgnoreCase(ans)){
                    System.out.println("Thanks for trusting our bank! Bye Bye");
                    System.exit(0);
                } else if("yes".equalsIgnoreCase(ans)){
                    System.out.println("Write the name you like!");
                    this.setName(Input.inputNextLine());
                    return;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans = Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    public void changeLastName(){
        System.out.println("Would you like to change the Last name? (previous last name : " + this.getSurname() + ")");
        String ans = Input.inputNextLine();
        if (!"no".equalsIgnoreCase(ans)){
            do {
                if("return".equalsIgnoreCase(ans)){
                    return;
                }else if("quit".equalsIgnoreCase(ans)){
                    System.out.println("Thanks for trusting our bank! Bye Bye");
                    System.exit(0);
                } else if("yes".equalsIgnoreCase(ans)){
                    System.out.println("Write the last name you like!");
                    this.setSurname(Input.inputNextLine());
                    return;
                }else{
                    System.out.println("wrong input! try again");
                }
                ans = Input.inputNextLine();
            }while(!"no".equalsIgnoreCase(ans));
        }
    }

    @Override
    public String toString() {
        return "Name : " + this.getName() +
                ", Last Name : " + this.getSurname()
                ;
    }
}
