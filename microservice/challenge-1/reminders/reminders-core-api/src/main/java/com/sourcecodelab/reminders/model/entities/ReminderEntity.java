package com.sourcecodelab.reminders.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ReminderEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Date creationDate;
    private String externalId;
    private String recipient;
    private String text;
    private String cron;
    private String status;

    @PrePersist
    private void onSave() {
        this.creationDate = new Date();
        this.externalId = UUID.randomUUID().toString();
        this.status = "CREATED";
    }
}
