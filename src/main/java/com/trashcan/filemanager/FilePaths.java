package com.trashcan.filemanager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>FilePaths</h1>
 * <p>FilePaths contains all the paths to the files as String may make a few files in the process</p>
 */
public class FilePaths {
    private String rootPath = System.getenv("APPDATA");
    private String directoryPath = rootPath + "\\Medex";
    private String savedPath = directoryPath + "\\saved";
    private String userPath = directoryPath + "\\user.json";

    public FilePaths() {
    }

    public FilePaths(String rootPath) {
        this.rootPath = rootPath;
        this.directoryPath = rootPath + "\\Medex";
        this.savedPath = directoryPath + "\\saved";
        this.userPath = directoryPath + "\\user.json";
    }

    public Path getNewSave(String fileName) throws IOException {
        if(!Files.exists(Path.of(savedPath + "\\" + fileName + ".json"))) {
            Files.createFile(Path.of(savedPath + "\\" + fileName + ".json"));
        }
        return Path.of(savedPath +"\\" + fileName + ".json");
    }

    public String getSavedString() {
        return savedPath;
    }

    public Path getSavedPath() {
        return Path.of(directoryPath + "\\saved\\");
    }

    public String getUserString() {
        return userPath;
    }

    public Path getUserPath() {
        return Path.of(directoryPath + "\\user.json");
    }

    public String getMedexDirectoryString() {
        return rootPath;
    }

    public Path getRootPath() {
        return Path.of(rootPath);
    }

    public String getDirectoryString() {
        return directoryPath;
    }

    public Path getDirectoryPath() {
        return Path.of(directoryPath);
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }
}
