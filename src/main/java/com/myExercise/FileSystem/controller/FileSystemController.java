package com.myExercise.FileSystem.controller;

import com.myExercise.FileSystem.dto.FileList;
import com.myExercise.FileSystem.service.FileSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class FileSystemController {

    private static Logger logger = LoggerFactory.getLogger(FileSystemController.class);

    private final FileSystemService fileSystemService;

    @Autowired
    FileSystemController(final FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @RequestMapping(value = "file/list/**", method = RequestMethod.GET)
    public ResponseEntity<FileList> getFileList( HttpServletRequest request) throws IOException {
        final String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        final String bestMatchPattern = (String) request
                .getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        final AntPathMatcher apm = new AntPathMatcher();
        final String finalPath = apm.extractPathWithinPattern(bestMatchPattern, path);

        logger.info("-------------path----------"+finalPath);
        return ResponseEntity.ok(fileSystemService.getFileList(finalPath));
    }
}
