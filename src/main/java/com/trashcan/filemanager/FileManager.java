package com.trashcan.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.trashcan.account.User;
import com.trashcan.med.Med;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;


public class FileManager {
    private FilePaths paths = new FilePaths();

    public void saveMeds(ArrayList<Med> medList, String userName) throws IOException {
        System.out.println(medList);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(paths.getNewSave("default"));

        if(!Objects.equals(userName, "")) {
            writer = Files.newBufferedWriter(paths.getNewSave(userName));
        }

        gson.toJson(medList, writer);

        writer.close();
    }

    public void createUserFile(User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = Files.newBufferedWriter(paths.getUserPath());
        gson.toJson(user, writer);
        writer.close();
    }

    public boolean deleteUserFile() throws IOException {
        if(Files.exists(paths.getUserPath())) {
            Files.delete(paths.getUserPath());
            return true;
        }
        return false;
    }

    public ArrayList<Med> getCurentMeds(String userName) throws IOException {
        Gson gson = new Gson();

        ArrayList<Med> newList = new ArrayList<>();

        if(Files.size(paths.getNewSave(userName)) != 0) {
            Reader reader = Files.newBufferedReader(paths.getNewSave(userName));
            Type listTokenType = new TypeToken<ArrayList<Med>>(){}.getType();
            newList = gson.fromJson(reader, listTokenType);
        }

        return newList;
    }

    public void createMainDirectory() throws IOException {
        if(!(Files.exists(paths.getDirectoryPath()) && Files.isDirectory(paths.getDirectoryPath()))) {
            Files.createDirectories(paths.getDirectoryPath());
        }
        if(!(Files.exists(paths.getSavedPath()))) {
            Files.createDirectories(paths.getSavedPath());
        }
    }

    public boolean userExists() throws IOException {
        return Files.exists(paths.getUserPath());
    }

    public User getUser() throws IOException {
        Gson gson = new Gson();

        User user = new User();

        if(userExists()) {
            Reader reader = Files.newBufferedReader(paths.getUserPath());
            user = gson.fromJson(reader, User.class);

            reader.close();
        }

        return user;
    }
}
