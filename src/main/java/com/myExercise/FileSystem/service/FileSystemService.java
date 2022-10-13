package com.myExercise.FileSystem.service;

import com.myExercise.FileSystem.dto.FileList;

import java.io.IOException;

/**
 * Interface to define the file system service.
 */
public interface FileSystemService {

    /**
     * Get file list from the path
     * @param path
     * @return file list
     */
    FileList getFileList(String path) throws IOException;
}
