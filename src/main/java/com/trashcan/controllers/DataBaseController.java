package com.trashcan.controllers;

import com.trashcan.account.User;
import com.trashcan.database.DB;
import com.trashcan.filemanager.FileManager;
import com.trashcan.med.Med;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataBaseController implements Initializable {
    @FXML
    private BorderPane dataBorderPane;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField firmTextField;
    @FXML
    private TextField substTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField madeForTextField;
    @FXML
    private TextField admTextField;
    @FXML
    private TextField doseTextField;
    @FXML
    private ComboBox<String> dangerComboBox;
    @FXML
    private TextArea descriptionTextArea;

    private boolean modeCheck = false; // True - remove, False - add

    private FileManager fileManager = new FileManager();

    private User user = fileManager.getUser();

    private DB db = new DB(user.admid);

    public DataBaseController() throws IOException {
    }

    /**
     * Sets the mode to REMOVE MED ( true )
     * @throws SQLException
     */
    public void deleteMed() throws SQLException {
        modeCheck = true;
        substTextField.setDisable(true);
        typeTextField.setDisable(true);
        madeForTextField.setDisable(true);
        admTextField.setDisable(true);
        doseTextField.setDisable(true);
        dangerComboBox.setDisable(true);
        descriptionTextArea.setDisable(true);
    }

    /**
     * Sets the page mode to ADD MED ( false )
     * @throws SQLException
     */
    public void addMed() throws SQLException {
        modeCheck = false;
        substTextField.setDisable(false);
        typeTextField.setDisable(false);
        madeForTextField.setDisable(false);
        admTextField.setDisable(false);
        doseTextField.setDisable(false);
        dangerComboBox.setDisable(false);
        descriptionTextArea.setDisable(false);
    }

    public void enter() throws SQLException {
        PreparedStatement preparedStatement;
        if(!modeCheck) {
            preparedStatement = db.dataBase.prepareStatement("insert into magmed.meds (medName, firm, substActv, medType, madeFor, adm, dosePerDay, danger, description) values (?, ?, ?, ?, ?, ?, ?, ?, ?); ");
            preparedStatement.setString(1, nameTextField.getText());
            preparedStatement.setString(2, firmTextField.getText());
            preparedStatement.setString(3, substTextField.getText());
            preparedStatement.setString(4, typeTextField.getText());
            preparedStatement.setString(5, madeForTextField.getText());
            preparedStatement.setString(6, admTextField.getText());
            preparedStatement.setString(7, doseTextField.getText());

            if(dangerComboBox.getValue() == "Yes") {
                preparedStatement.setString(8, "1");
            }
            else if(dangerComboBox.getValue() == "No") {
                preparedStatement.setString(8, "0");
            }

            preparedStatement.setString(9, descriptionTextArea.getText());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        else {
            preparedStatement = db.dataBase.prepareStatement("delete from magmed.meds where medName=? and firm=?");
            preparedStatement.setString(1, nameTextField.getText());
            preparedStatement.setString(2, firmTextField.getText());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dangerComboBox.getItems().addAll("Yes", "No");
    }
}
