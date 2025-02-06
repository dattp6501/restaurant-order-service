package com.dattp.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class JSONUtils {
  public static <T> T toEntity(String json, Class<T> tClass) {
    try {
      if (json == null || json.isEmpty()) return null;
      return new ObjectMapper().readValue(json, tClass);
    } catch (JsonProcessingException e) {
      log.error("=========> toEntity:JsonProcessingException:{}", e.getMessage());
      return null;
    }
  }

  public static String toJson(Object entity) {
    try {
      return new ObjectMapper().writeValueAsString(entity);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static <T> T toStringJson(byte[] bytes, Class<T> tClass) {
    try {
      if (bytes == null) return null;
      return new ObjectMapper().readValue(bytes, tClass);
    } catch (IOException e) {
      return null;
    }
  }

  public static byte[] toByteArray(Object data) {
    try {
      return new ObjectMapper().writeValueAsBytes(data);
    } catch (JsonProcessingException e) {
      return null;
    }
  }
}