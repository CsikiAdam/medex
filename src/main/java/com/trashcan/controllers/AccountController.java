package com.trashcan.controllers;

import com.trashcan.med.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    // center size
    // Width:  1090
    // Height: 757

    @FXML
    private BorderPane accountBorderPane;

    private final FXMLLoader savedProductLoader = new FXMLLoader(Main.class.getResource("SavedProducts.fxml"));
    private final FXMLLoader databaseLoader = new FXMLLoader(Main.class.getResource("Database.fxml"));

    public void openSaved() {
        accountBorderPane.setCenter(savedProductLoader.getRoot());
    }

    public void openDatabase() {
        accountBorderPane.setCenter(databaseLoader.getRoot());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            savedProductLoader.load();
            databaseLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}