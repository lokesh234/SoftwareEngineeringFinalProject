package edu.wpi.cs3733.c20.teamU.Administration;

public class Account {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String cred;
    private String number;

    public Account(String userName, String password, String firstName, String lastName, String cred, String number) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
        this.cred = cred.toUpperCase();
    }

    public String getUserName() {
        return userName;
    }

    public String getCred() {
        return cred;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String  getNumber() {return number;}
}
