package com.tooldepot.pos.repo;

import com.tooldepot.pos.domain.RentalTransaction;
import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Demo repo for rental transactions to be replaced by a real db.
 */
@Service
@Slf4j
public class RentalTransactionRepo {
    private final Map<String, Tool> tools = new HashMap<>();

    public RentalTransaction save(RentalTransaction rentalTransaction) {
        log.debug("save: {}", rentalTransaction);

        // currently a no-op until we have a real db

        return rentalTransaction;
    }
}
