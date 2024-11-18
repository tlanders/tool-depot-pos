package com.tooldepot.pos.api;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import com.tooldepot.pos.service.ToolService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.record.RecordModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * REST API controller for tools services.
 */
@RestController
@RequestMapping("/api/tools")
public class ToolApiController {
    @Autowired
    private ToolService toolService;

    @GetMapping
    public ResponseEntity<FindToolsResponseModel> findAllTools() {
        List<Tool> tools = toolService.findAllTools();
        ModelMapper modelMapper = new ModelMapper().registerModule(new RecordModule());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ToolModel> responseTools = tools.stream()
                .map(tool -> modelMapper.map(tool, ToolModel.class))
                .toList();

        return ResponseEntity.ok(new FindToolsResponseModel(responseTools));
    }

    @GetMapping("/{toolCode}")
    public ResponseEntity<ToolResponseModel> getTool(@PathVariable @NonNull String toolCode) {
        Optional<Tool> optionalTool = toolService.getTool(toolCode);

        if (optionalTool.isPresent()) {
            ModelMapper modelMapper = new ModelMapper().registerModule(new RecordModule());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            ToolResponseModel toolResponseModel = modelMapper.map(
                    optionalTool.get(), ToolResponseModel.class);
            toolResponseModel.setMessage("success");

            return ResponseEntity.ok(toolResponseModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class FindToolsResponseModel extends BaseApiResponseModel {
        private List<ToolModel> tools;

        public FindToolsResponseModel(List<ToolModel> tools) {
            super(0, "success");
            this.tools = tools;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ToolResponseModel extends BaseApiResponseModel {
        private String toolCode;
        private ToolType toolType;
        private String brand;
    }

    @Data
    public static class ToolModel {
        private String toolCode;
        private ToolType toolType;
        private String brand;
    }
}
