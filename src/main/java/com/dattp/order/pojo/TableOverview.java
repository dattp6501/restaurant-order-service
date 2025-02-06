package com.dattp.order.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TableOverview implements Serializable {
  private Long id;
  private String name;
  private String image;
}