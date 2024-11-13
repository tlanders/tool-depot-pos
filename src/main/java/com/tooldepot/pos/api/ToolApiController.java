package com.tooldepot.pos.api;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolApiController {
    @Autowired
    private ToolService toolService;

    @GetMapping
    public ResponseEntity<List<Tool>> findAllTools() {
        return ResponseEntity.ok(toolService.findAllTools());
    }
}
