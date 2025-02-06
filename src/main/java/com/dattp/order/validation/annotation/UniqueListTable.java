package com.dattp.order.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueListTable {
  String message() default "Bàn đặt bị trùng nhau";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
