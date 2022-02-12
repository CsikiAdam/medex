package com.trashcan.controllers;

import com.trashcan.database.DB;
import com.trashcan.filemanager.FileManager;
import com.trashcan.account.User;
import com.trashcan.med.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userMailLabel;
    @FXML
    private Label loggedInLabel;
    @FXML
    private BorderPane rootBorderPane;
    @FXML
    private Button logoutButton;
    @FXML
    private Button browseButton;
    @FXML
    private Button accountButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button helpButton;
    
    // rootBorderPane SIZE
    // Width:  1090
    // Height: 807

    private User user = new User();

    private int entryCode = 0;

    private final FileManager fileManager = new FileManager();

    private DB db = new DB();

    private final FXMLLoader welcomeScreenLoader = new FXMLLoader(Main.class.getResource("WelcomeScreen.fxml"));
    private final FXMLLoader browserLoader = new FXMLLoader(Main.class.getResource("Browser.fxml"));
    private final FXMLLoader savedProductsLoader = new FXMLLoader(Main.class.getResource("SavedProducts.fxml"));
    private final FXMLLoader databaseLoader = new FXMLLoader(Main.class.getResource("Database.fxml"));
    private final FXMLLoader loginLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
    private final FXMLLoader helpLoader = new FXMLLoader(Main.class.getResource("Help.fxml"));

    public void openBrowser() {
        rootBorderPane.setCenter(browserLoader.getRoot());
    }

    public void openSaved() {
        rootBorderPane.setCenter(savedProductsLoader.getRoot());
    }

    public void openLoginScreen() {
        rootBorderPane.setCenter(loginLoader.getRoot());
    }

    public void openDatabase() throws SQLException {
        System.out.println(user.admid);
        if(db.getAdminPrivileges(user.admid)) {
            rootBorderPane.setCenter(databaseLoader.getRoot());
        }
        else {
            openLoginScreen();
        }
    }

    public void openHelp() {
        rootBorderPane.setCenter(helpLoader.getRoot());
    }

    public void setLabelNon() {
        this.loggedInLabel.setText("No");
        this.userNameLabel.setText("NoN");
        this.userMailLabel.setText("NoN");
    }

    public void setUser(User newUser) throws IOException {
        if(!(Objects.equals(newUser, new User()) || fileManager.userExists())) {
            setLabelNon();
        }
        else {
            this.user = newUser;
            this.loggedInLabel.setText("True");
            this.userNameLabel.setText(newUser.name);
            this.userMailLabel.setText(newUser.mail);
        }
    }

    public void logout() throws IOException {
        setUser(new User());
        fileManager.deleteUserFile();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            welcomeScreenLoader.load();
            browserLoader.load();
            savedProductsLoader.load();
            databaseLoader.load();
            loginLoader.load();
            helpLoader.load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            user = fileManager.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            setUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rootBorderPane.setCenter(welcomeScreenLoader.getRoot());
    }
}
