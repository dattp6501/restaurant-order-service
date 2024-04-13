package com.dattp.order.converter;

import com.dattp.order.pojo.booking.BookingOverview;

import javax.persistence.Converter;

@Converter
public class BookingOverviewConverter extends BaseConverter<BookingOverview>{
    @Override
    protected Class<BookingOverview> getObjectType() {
        return BookingOverview.class;
    }
}
