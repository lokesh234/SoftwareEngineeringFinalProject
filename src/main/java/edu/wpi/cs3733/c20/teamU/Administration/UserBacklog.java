package edu.wpi.cs3733.c20.teamU.Administration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @AllArgsConstructor
public class UserBacklog {
    private String username;
    private String dateCompleted;
    private String serviceType;
    private String operations;
    private String info;
}
