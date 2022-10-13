package com.myExercise.FileSystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class FileList {
    private List<Folder> folders;
    private List<File> files;
}
