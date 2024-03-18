package com.dattp.order.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.dattp.order.dto.PeriodTimeResponseDTO;
import com.dattp.order.dto.PeriodsTimeBookedTableDTO;
import com.dattp.order.entity.BookedTable;
import com.dattp.order.exception.NotfoundException;
import com.dattp.order.repository.BookedTableRepository;

@Service
public class BookedTableService {
    @Autowired
    private BookedTableRepository bookedTableRepository;

    public List<PeriodsTimeBookedTableDTO> getPeriadrentAllTable(Date from, Date to, Pageable pageable){
        List<PeriodsTimeBookedTableDTO> list = new ArrayList<>();
        int lengthCur = -1;
        for(Object[] columns : bookedTableRepository.findPeriadrentAllTable(from, to, pageable).getContent()){
            // t.table_id, t.from_, t.to_
            if(list!=null && !list.isEmpty() && list.get(lengthCur).getId().longValue()==((BigInteger)columns[0]).longValue()){//if table_id new == table_old
                list.get(lengthCur).getTimes().add(new PeriodTimeResponseDTO((Date)columns[1], (Date)columns[2]));//add period rent of table
            }else{
                list.add(new PeriodsTimeBookedTableDTO(((BigInteger)columns[0]).longValue(),null,new ArrayList<>()));
                lengthCur++;
                list.get(lengthCur).getTimes().add(new PeriodTimeResponseDTO((Date)columns[1], (Date)columns[2]));
            }
        }
        return list;
    }

    public List<BookedTable> saveBookedTable(List<BookedTable> bookedTables){
        return bookedTableRepository.saveAll(bookedTables);
    }

    public void update(BookedTable bookedTable){
        BookedTable bookedTableSrc = bookedTableRepository.findById(bookedTable.getId()).orElse(null);
        if(bookedTableSrc==null) return;
        if(bookedTable.getPrice()>0) bookedTableSrc.setPrice(bookedTable.getPrice());
        if(!bookedTable.getName().isEmpty()) bookedTableSrc.setName(bookedTable.getName());
        bookedTableSrc.setState(bookedTable.getState());
        bookedTableRepository.save(bookedTableSrc);
    }

    public boolean existsById(long id){
        return bookedTableRepository.existsById(id);
    }

    // neu co ban da duoc dat trong khoang thoi gian nay thi tra ve true, nguoc lai tra ve false
    public boolean isFreetime(BookedTable bookedTable){
        List<Object[]> list = bookedTableRepository.findPeriodRent(bookedTable.getFrom(),bookedTable.getTo(), bookedTable.getTableId());
        if(list==null || list.isEmpty()) return true;
        return false;
    }

    public List<Pair<Date,Date>> getPeriodRentTable(Date from, Date to, Long tableId){
        List<Pair<Date,Date>> list = new ArrayList<>();
        bookedTableRepository.findPeriodRent(from, to, tableId).stream()
        .forEach((columns)->{
            list.add(Pair.of((Date)columns[0], (Date)columns[1]));
        });
        return list;
    }

    public void delete(Long id){
        BookedTable bt = bookedTableRepository.findById(id).orElse(null);
        if(bt==null) throw new NotfoundException("Bàn đặt không tồn tại");
        bookedTableRepository.delete(bt);
    }
}
