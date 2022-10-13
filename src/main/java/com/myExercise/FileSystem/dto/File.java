package com.myExercise.FileSystem.dto;

import lombok.Data;

@Data
public class File {
    private String name;
    private String size;
    private String createdOn;
    private String modifiedOn;
}
