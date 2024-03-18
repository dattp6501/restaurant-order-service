package com.dattp.order.dto.kafka;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserKafka {
    private Long id;
    private String fullname;
    private String username;
    private String mail;

    public UserKafka() {
    }
    public UserKafka(Long id, String fullname, String username, String mail) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.mail = mail;
    }
}