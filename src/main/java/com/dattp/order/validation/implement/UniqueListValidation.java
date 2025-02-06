package com.dattp.order.validation.implement;

import com.dattp.order.dto.bookedtable.BookedTableCreateDTO;
import com.dattp.order.validation.annotation.UniqueListTable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class UniqueListValidation implements ConstraintValidator<UniqueListTable, Collection<BookedTableCreateDTO>> {

  @Override
  public boolean isValid(Collection<BookedTableCreateDTO> arg0, ConstraintValidatorContext arg1) {
    System.out.println("======================================================================");
    System.out.println("List<RequestBookedTableDTO>.size = " + arg0.size());
    System.out.println("======================================================================");
    return true;
  }


}