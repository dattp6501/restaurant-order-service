package com.dattp.order.converter;

import javax.persistence.Converter;

@Converter
public class UserOverviewConverter extends BaseConverter<UserOverviewConverter>{
    @Override
    protected Class<UserOverviewConverter> getObjectType() {
        return UserOverviewConverter.class;
    }
}
