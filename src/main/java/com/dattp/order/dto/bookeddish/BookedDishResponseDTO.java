package com.dattp.order.dto.bookeddish;

import com.dattp.order.entity.BookedDish;
import com.dattp.order.entity.state.BookedDishState;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class BookedDishResponseDTO {
    private BookedDishState state;

    private Long id;

    private Long dishId;

    private Integer total;

    private Float price;

    private String name;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime updateAt;
    public BookedDishResponseDTO() {super();}

    public BookedDishResponseDTO(BookedDish bd){
        copyProperties(bd);
    }
    public void copyProperties(BookedDish bd){
        BeanUtils.copyProperties(bd, this);
        this.createAt = DateUtils.convertToLocalDateTime(bd.getCreateAt());
        this.updateAt = DateUtils.convertToLocalDateTime(bd.getUpdateAt());
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BookedDishResponseDTO)) return false;
        BookedDishResponseDTO other = (BookedDishResponseDTO) obj;
        return Objects.equals(id, other.id);
    }
}