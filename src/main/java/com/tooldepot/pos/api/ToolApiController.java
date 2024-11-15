package com.tooldepot.pos.api;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/tools")
public class ToolApiController {
    @Autowired
    private ToolService toolService;

    @GetMapping
    public ResponseEntity<List<Tool>> findAllTools() {
        return ResponseEntity.ok(toolService.findAllTools());
    }

    @GetMapping("/{toolCode}")
    public ResponseEntity<Tool> getTool(@PathVariable @NonNull String toolCode) {
        Optional<Tool> optionalTool = toolService.getTool(toolCode);
        return optionalTool.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
