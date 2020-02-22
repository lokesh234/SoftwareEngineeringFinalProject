package edu.wpi.cs3733.c20.teamU.Administration;

public class Account {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String cred;
    private String email;

    public Account(String userName, String password, String firstName, String lastName, String cred, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getEmail() {return email;}
}
