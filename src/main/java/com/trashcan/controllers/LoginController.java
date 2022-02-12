package com.trashcan.controllers;

import com.trashcan.account.User;
import com.trashcan.database.DB;
import com.trashcan.filemanager.FileManager;
import com.trashcan.med.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private Button enterButton;
    @FXML
    private TextField mailTextField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private Label checkLabel;
    @FXML
    private StackPane mainLoginStackPane;

    private final FXMLLoader registerLoader = new FXMLLoader(Main.class.getResource("Register.fxml"));
    private User userAccount = new User();
    private final DB db = new DB();
    private final Stage stage = new Stage();
    private final FileManager fileManager = new FileManager();

    public void close() {
        ((BorderPane) mainLoginStackPane.getParent()).getChildren().remove(mainLoginStackPane);
    }

    public void loginCheck() throws SQLException, IOException {
        if(db.loginCheck(mailTextField.getText(), passTextField.getText())) {
            userAccount = db.getUser(mailTextField.getText(), passTextField.getText());
            checkLabel.setText("Success");
            System.out.println(userAccount);
            fileManager.createUserFile(userAccount);
            ((BorderPane) mainLoginStackPane.getParent()).getChildren().remove(mainLoginStackPane);
        }
        else {
            System.out.println(userAccount);
            checkLabel.setText("Something is Wong");
        }
    }

    public void openRegister() {
        mainLoginStackPane.getChildren().add(1, registerLoader.getRoot());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            registerLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}