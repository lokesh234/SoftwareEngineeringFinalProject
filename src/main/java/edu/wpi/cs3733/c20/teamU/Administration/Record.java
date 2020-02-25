package edu.wpi.cs3733.c20.teamU.Administration;

public class Record {
    private String userName, time, type, operations, info;

    public Record(String userName, String time, String type, String operations, String info) {
        this.userName = userName;
        this.time = time;
        this.type = type;
        this.operations = operations;
        this.info = info;
    }

    public String getUserName() {
        return userName;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getOperations() {
        return operations;
    }

    public String getInfo() {
        return info;
    }
}
