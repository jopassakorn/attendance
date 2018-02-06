package com.attendance.domain.entity;

import javax.persistence.*;

/**
 * Created by developer on 5/2/2561.
 */

@Entity
@Table(name = "activated")
public class Activated {

    private int id;
    private int userId;
    private int fingerprintId;
    private boolean activatedStatus;
    private Fingerprint fingerprint;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fingerprint_id",insertable = false,updatable = false)
    public Fingerprint getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(Fingerprint fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "fingerprint_id")
    public int getFingerprintId() {
        return fingerprintId;
    }

    public void setFingerprintId(int fingerprintId) {
        this.fingerprintId = fingerprintId;
    }

    @Column(name = "activate_status")
    public boolean isActivatedStatus() {
        return activatedStatus;
    }

    public void setActivatedStatus(boolean activatedStatus) {
        this.activatedStatus = activatedStatus;
    }
}
