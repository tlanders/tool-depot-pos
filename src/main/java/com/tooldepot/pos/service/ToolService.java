package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.repo.ToolRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ToolService {
    private final ToolRepo toolRepo;

    public ToolService(ToolRepo toolRepo) {
        this.toolRepo = toolRepo;
    }

    public List<Tool> findAllTools() {
        return toolRepo.findAllTools();
    }

    public Optional<Tool> getTool(String toolCode) {
        return toolRepo.getTool(toolCode);
    }
}
