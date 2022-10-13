package com.myExercise.FileSystem.service;

import com.myExercise.FileSystem.FileSystemApplication;
import com.myExercise.FileSystem.dto.FileList;
import com.myExercise.FileSystem.dto.Folder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;

import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemServiceImpl implements FileSystemService{

    private static Logger logger = LoggerFactory.getLogger(FileSystemServiceImpl.class);
    @Override
    public FileList getFileList(String path) throws IOException {

        FileList fileList = new FileList();

        List<Folder> folders = findFoldersInDirectory(path);
        List<com.myExercise.FileSystem.dto.File> files = findFileListInDirectory(path);

        fileList.setFolders(folders);
        fileList.setFiles(files);

        return fileList;
    }

    private List<Folder> findFoldersInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        FileFilter directoryFileFilter = new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };

        File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
        List<Folder> foldersInDirectory = new ArrayList<Folder>(directoryListAsFile.length);
        for (File directoryAsFile : directoryListAsFile) {
            Folder folder = new Folder();
            folder.setName(directoryAsFile.getName());
            foldersInDirectory.add(folder);
        }

        return foldersInDirectory;
    }

    private List<com.myExercise.FileSystem.dto.File> findFileListInDirectory(String path) throws IOException {

        List<com.myExercise.FileSystem.dto.File> fileList = new ArrayList<>();
        //Creating a File object for directory
        File directoryPath = new File(path);
        //List of all files and directories
        File filesList[] = directoryPath.listFiles();
        logger.info("List of files and directories in the specified directory:");


        for(File file : filesList) {
            if(!file.isDirectory()) {
                com.myExercise.FileSystem.dto.File fileData = new com.myExercise.FileSystem.dto.File();
                BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                fileData.setName(file.getName());
                fileData.setSize(convert(file.length()));
                fileData.setCreatedOn(String.valueOf(attr.creationTime()));
                fileData.setModifiedOn(String.valueOf(attr.lastAccessTime()));
                fileList.add(fileData);
            }
        }

        return fileList;
    }

    public static String convert(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int)(Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }
}
