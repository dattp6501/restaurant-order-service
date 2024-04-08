package com.dattp.order.entity;

import java.util.Objects;
import javax.persistence.*;

import com.dattp.order.dto.bookedtable.BookedTableCreateDTO;
import com.dattp.order.dto.bookedtable.BookedTableResponseDTO;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="BOOKED_TABLE")
@Getter
@Setter
@AllArgsConstructor
public class BookedTable {
    @Column(name="id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="table_id")
    private Long tableId;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private BookedTableState state;

    
    @Column(name="price")
    private Float price;
    
    @Column(name="from_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Long from;

    @Column(name="to_")
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private Long to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="booking_id")
    @JsonIgnore
    private Booking booking;

    @Column(name = "create_at")
    private Long createAt = DateUtils.getCurrentMils();

    @Column(name = "update_at")
    private Long updateAt= DateUtils.getCurrentMils();
    
    public BookedTable(){super();}

    public BookedTable(BookedTableCreateDTO dto, Long from, Long to){
        copyProperties(dto);
        this.state = BookedTableState.PROCESSING;
        this.from = from;
        this.to = to;
    }
    public void copyProperties(BookedTableCreateDTO dto){
        BeanUtils.copyProperties(dto, this);
    }


    public void copyProperties(BookedTableResponseDTO dto){
        BeanUtils.copyProperties(dto, this);
        this.from = DateUtils.getMills(dto.getFrom());
        this.to = DateUtils.getMills(dto.getTo());
        this.createAt = DateUtils.getMills(dto.getCreateAt());
        this.updateAt = DateUtils.getMills(dto.getUpdateAt());
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BookedTable)) return false;
        BookedTable other = (BookedTable) obj;
        return Objects.equals(this.tableId, other.tableId);
    }
}