package edu.wpi.cs3733.c20.teamU.Administration;

public class Account {
    private String userName;
    private String password;
    private String cred;
    private String firstName;
    private String lastName;

    public Account(String userName, String password, String cred, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.cred = cred;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
