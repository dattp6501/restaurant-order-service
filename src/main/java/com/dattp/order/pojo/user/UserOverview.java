package com.dattp.order.pojo.user;

import lombok.Data;

@Data
public class UserOverview {
    private Long id;

    private String fullname;

    private String username;

    private String mail;
}