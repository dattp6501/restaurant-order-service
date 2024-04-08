package com.dattp.order.dto.bookedtable;

import com.dattp.order.entity.BookedTable;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class BookedTableResponseDTO {
    private Long id;
    private Long tableId;
    private String name;
    private BookedTableState state;
    private Float price;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime from;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime to;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime updateAt;
    public BookedTableResponseDTO() {
        super();
    }
    public BookedTableResponseDTO(BookedTable bt) {
        copyProperties(bt);
    }
    public void copyProperties(BookedTable bt){
        BeanUtils.copyProperties(bt, this);
        this.createAt = DateUtils.convertToLocalDateTime(bt.getCreateAt());
        this.updateAt = DateUtils.convertToLocalDateTime(bt.getUpdateAt());
    }
    @Override
    public boolean equals(Object obj) {
        BookedTableResponseDTO other = (BookedTableResponseDTO) obj;
        return Objects.equals(this.id, other.id);
    }


}
