package com.dattp.order.validation.implement;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dattp.order.dto.bookedtable.BookedTableCreateDTO;
import com.dattp.order.validation.annotation.UniqueListTable;

public class UniqueListValidation implements ConstraintValidator<UniqueListTable,Collection<BookedTableCreateDTO>>{

    @Override
    public boolean isValid(Collection<BookedTableCreateDTO> arg0, ConstraintValidatorContext arg1) {
        System.out.println("======================================================================");
        System.out.println("List<RequestBookedTableDTO>.size = "+arg0.size());
        System.out.println("======================================================================");
        return true;
    }


    
}