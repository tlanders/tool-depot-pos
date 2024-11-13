package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.repo.ToolRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ToolService {
    @Autowired
    private ToolRepo toolRepo;

    public List<Tool> findAllTools() {
        return toolRepo.findAllTools();
    }
}
