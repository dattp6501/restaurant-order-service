package com.dattp.order.converter;

import com.dattp.order.utils.JSONUtils;
import lombok.extern.log4j.Log4j2;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Log4j2
public abstract class BaseConverter<T> implements AttributeConverter<T, String> {
  @Override
  public String convertToDatabaseColumn(T data) {
    if (null == data) {
      return null;
    }
    return JSONUtils.toJson(data);
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (null == dbData || dbData.isEmpty()) {
      return null;
    }
    return JSONUtils.toEntity(dbData, getObjectType());
  }

  protected abstract Class<T> getObjectType();
}