package com.dattp.order.storage;

import com.dattp.order.entity.BookedTable;
import com.dattp.order.repository.BookedTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookedTableStorage extends Storage {
    @Autowired
    private BookedTableRepository bookedTableRepository;

    public List<BookedTable> saveAll(List<BookedTable> bookedTables) {
        return bookedTableRepository.saveAll(bookedTables);
    }

    public BookedTable findById(Long id) {
        return bookedTableRepository.findById(id).orElse(null);
    }

    public BookedTable save(BookedTable bookedTable) {
        return bookedTableRepository.save(bookedTable);
    }

    public boolean existsById(Long id){
        return bookedTableRepository.existsById(id);
    }
}
