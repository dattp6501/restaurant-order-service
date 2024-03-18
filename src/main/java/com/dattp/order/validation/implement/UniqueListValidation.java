package com.dattp.order.validation.implement;

import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.dattp.order.dto.BookedTableRequestDTO;
import com.dattp.order.validation.annotation.UniqueListTable;

public class UniqueListValidation implements ConstraintValidator<UniqueListTable,Collection<BookedTableRequestDTO>>{

    @Override
    public boolean isValid(Collection<BookedTableRequestDTO> arg0, ConstraintValidatorContext arg1) {
        System.out.println("======================================================================");
        System.out.println("List<RequestBookedTableDTO>.size = "+arg0.size());
        System.out.println("======================================================================");
        return true;
    }


    
}