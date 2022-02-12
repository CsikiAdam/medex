package com.trashcan.med;

import com.trashcan.database.DB;
import com.trashcan.filemanager.FileManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

//FIXME sa te uiti in baza de date, tabla adm are rolu de a tine un cod care l-ar da admini pentru inregistrare

public class Main extends Application {
    private final DB db = new DB();
    private final FileManager fileManager = new FileManager();

    @Override
    public void start(Stage stage) {
        System.out.println("Deploying flashbang...");

        try {
            fileManager.createMainDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = db.dataBase.createStatement();
            statement.execute("use magmed");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        FXMLLoader meniuFxmlLoader = new FXMLLoader(Main.class.getResource("Menu.fxml"));

        try {
            meniuFxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(meniuFxmlLoader.getRoot());

        stage.setResizable(false);
        stage.getIcons().addAll(new Image("file:src/main/resources/com/trashcan/img/icon.png"));
        stage.setTitle("Medex");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}