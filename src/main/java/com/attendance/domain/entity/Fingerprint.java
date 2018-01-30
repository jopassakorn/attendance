package com.attendance.domain.entity;

import lombok.Cleanup;

import javax.persistence.*;

/**
 * Created by developer on 17/1/2561.
 */
@Entity
@Table(name = "fingerprint")
public class Fingerprint {

    private int id;
    private String fingerprintId;
    private String room;

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    @Column(name = "fingerprint_id")
    public String getFingerprintId(){return fingerprintId;}

    public void setFingerprintId(String fingerprintId){this.fingerprintId = fingerprintId;}

    @Column(name = "room")
    public String getRoom(){return room;}

    public void setRoom(String room){this.room = room;}
}
