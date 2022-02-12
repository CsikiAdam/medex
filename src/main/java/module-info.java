module com.medical.magazinmedical {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires javafx.web;


    opens com.trashcan.med to javafx.fxml;
    exports com.trashcan.med;
    exports com.trashcan.controllers;
    opens com.trashcan.controllers to javafx.fxml;
    exports com.trashcan.account;
    opens com.trashcan.account to javafx.fxml;
    exports com.trashcan.filemanager;
    opens com.trashcan.filemanager to javafx.fxml;
}