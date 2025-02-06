package com.dattp.order.entity;

import com.dattp.order.dto.bookedtable.BookedTableCreateDTO;
import com.dattp.order.entity.state.BookedTableState;
import com.dattp.order.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BOOKED_TABLE")
@Getter
@Setter
@AllArgsConstructor
public class BookedTable {
  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "table_id")
  private Long tableId;

  @Column(name = "name")
  private String name;

  @Column(name = "image")
  private String image;

  @Column(name = "state")
  @Enumerated(EnumType.STRING)
  private BookedTableState state;


  @Column(name = "price")
  private Float price;

  @Column(name = "from_")
  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private Long from;

  @Column(name = "to_")
  @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
  private Long to;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "booking_id")
  @JsonIgnore
  private Booking booking;

  @Column(name = "create_at")
  private Long createAt = DateUtils.getCurrentMils();

  @Column(name = "update_at")
  private Long updateAt = DateUtils.getCurrentMils();

  public BookedTable() {
    super();
  }

  public BookedTable(BookedTableCreateDTO dto, Long from, Long to) {
    copyProperties(dto);
    this.state = BookedTableState.PROCESSING;
    this.from = from;
    this.to = to;
    this.image = dto.getImage();
    this.price = dto.getPrice();
    this.name = dto.getName();
  }

  @PrePersist
  protected void onCreate() {
    this.createAt = this.updateAt = DateUtils.getCurrentMils();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updateAt = DateUtils.getCurrentMils();
  }

  public void copyProperties(BookedTableCreateDTO dto) {
    BeanUtils.copyProperties(dto, this);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof BookedTable)) return false;
    BookedTable other = (BookedTable) obj;
    return Objects.equals(this.tableId, other.tableId);
  }
}