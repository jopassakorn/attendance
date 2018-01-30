package com.attendance.app.worklog;

/**
 * Created by developer on 23/1/2561.
 */
public class FingerprintAddForm {

    private int id;
    private String fingerprintId;
    private String room;



    public String getFingerprintId() {
        return fingerprintId;
    }

    public void setFingerprintId(String fingerprintId) {
        this.fingerprintId = fingerprintId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
