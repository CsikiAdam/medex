package com.trashcan.controllers;

import com.trashcan.account.User;
import com.trashcan.database.DB;
import com.trashcan.filemanager.FileManager;
import com.trashcan.med.Med;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SavedProductsController implements Initializable {
    @FXML
    private ScrollPane savedScrollPane;
    @FXML
    private FlowPane savedFlowPane;

    private FileManager fileManager = new FileManager();

    private User user = fileManager.getUser();

    private ArrayList<Med> medList = fileManager.getCurentMeds(user.name);

    public SavedProductsController() throws IOException {
    }

    public Button generateProduct(String title, double x, double y, int mid, String firm, String substActiva, String medType, String madeFor, String adm, String description, String imgLink, Font font) {
        final Button button = new Button();

        FlowPane.setMargin(button, new Insets(5, 5, 5, 5));

        Label tiLabel = new Label(title.toUpperCase(Locale.ROOT));
        Label fiLabel = new Label(firm);
        VBox vbox = new VBox();

        ImageView imageView = new ImageView();
        if(imgLink == null || !imgLink.contains("https://")) {
            imageView.setImage(new Image("file:src/main/resources/com/trashcan/img/medPlaceHolder.png"));
        }
        else {
            imageView.setImage(new Image(imgLink));
        }
        imageView.setFitWidth(x * 0.9);
        imageView.setFitHeight(y * 0.7);

        vbox.getChildren().addAll(tiLabel, fiLabel, imageView);

        button.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000;");

        button.setPrefWidth(x);
        button.setPrefHeight(y);

        button.setGraphic(vbox);

        button.setOnAction(e -> {
            Med med = new Med(mid ,title, firm, substActiva, medType, madeFor, adm, description, imgLink);
            Stage stage = new Stage();

            BorderPane root = new BorderPane();

            VBox left = new VBox();
            left.setPrefWidth(10);
            left.setPrefHeight(200);
            left.setStyle("-fx-background-color:  #2f7dc2");

            VBox right = new VBox();
            right.setPrefWidth(10);
            right.setPrefHeight(200);
            right.setStyle("-fx-background-color: #2f7dc2");

            HBox top = new HBox();
            top.setStyle("-fx-background-color: #2f7dc2");

            HBox bottom = new HBox();
            bottom.setPrefWidth(200);
            bottom.setPrefHeight(10);
            bottom.setStyle("-fx-background-color: #2f7dc2");

            root.setLeft(left);
            root.setRight(right);
            root.setTop(top);
            root.setBottom(bottom);

            VBox contentVBox = new VBox();
            contentVBox.setPrefWidth(326);
            contentVBox.setPrefHeight(400);

            HBox titleHBox = new HBox();
            Label tLabel = new Label("Title: ");
            tLabel.setFont(font);
            Label titleLabel = new Label(title);
            titleLabel.setFont(font);
            titleHBox.getChildren().addAll(tLabel, titleLabel);

            HBox firmHBox = new HBox();
            Label fLabel = new Label("Firm: ");
            fLabel.setFont(font);
            Label firmLabel = new Label(firm);
            firmLabel.setFont(font);
            firmHBox.getChildren().addAll(fLabel, firmLabel);

            HBox substActivaHBox = new HBox();
            Label sLabel = new Label("Substanta Activa: ");
            sLabel.setFont(font);
            Label substActivaLabel = new Label(substActiva);
            substActivaLabel.setFont(font);
            substActivaHBox.getChildren().addAll(sLabel, substActivaLabel);

            HBox medTypeHBox = new HBox();
            Label mLabel = new Label("Medication Type: ");
            mLabel.setFont(font);
            Label medTypeLabel = new Label(medType);
            medTypeLabel.setFont(font);
            medTypeHBox.getChildren().addAll(mLabel, medTypeLabel);

            HBox madeForHBox = new HBox();
            Label madeLabel = new Label("Good for: ");
            madeLabel.setFont(font);
            Label madeForLabel = new Label(madeFor);
            madeForLabel.setFont(font);
            madeForHBox.getChildren().addAll(madeLabel, madeForLabel);

            VBox descriptionVBox = new VBox();
            Label descriptionLabel = new Label("Description: ");
            descriptionLabel.setFont(font);

            TextFlow descriptionTextFlow = new TextFlow(new Text(description));
            descriptionTextFlow.setPrefWidth(300);
            descriptionTextFlow.setPrefHeight(300);
            descriptionTextFlow.setPadding(new Insets(0, 0, 0 ,5));

            descriptionVBox.getChildren().addAll(descriptionLabel);

            ScrollPane descriptionScrollPane = new ScrollPane();
            descriptionScrollPane.setPrefWidth(3000);
            descriptionScrollPane.setPrefHeight(3000);
            descriptionScrollPane.setContent(descriptionTextFlow);

            HBox buttonHBox = new HBox();
            buttonHBox.setAlignment(Pos.CENTER_RIGHT);
            buttonHBox.setPrefWidth(200);

            Button closeButton = new Button("Close");
            HBox.setMargin(closeButton, new Insets(0, 0, 0, 10));
            closeButton.setOnAction(e1 -> {
                ((Stage) (closeButton.getScene().getWindow())).close();
            });

            Button removeButton = new Button("Remove");
            HBox.setMargin(removeButton, new Insets(0, 0, 0, 5));

            removeButton.setOnAction(e2 -> {
                medList.remove(med);

                try {
                    fileManager.saveMeds(medList, user.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            buttonHBox.getChildren().addAll(closeButton, removeButton);

            contentVBox.getChildren().addAll(titleHBox, firmHBox, substActivaHBox, medTypeHBox, madeForHBox, descriptionScrollPane, buttonHBox);
            contentVBox.setPadding(new Insets(5, 5, 5, 5));
            root.setCenter(contentVBox);

            stage.setScene(new Scene(root, 400, 400));
            stage.setResizable(false);
            stage.setTitle(title);
            stage.show();
        });

        return button;
    }

    public void populate(ArrayList<Med> medList) throws SQLException {


        ArrayList<Button> buttonArrayList = new ArrayList<>();

        for(Med med : medList) {
            buttonArrayList.add(generateProduct(med.name, 100, 120, med.mid, med.firm, med.substActiva, med.medType, med.madeFor, med.adm, med.description, med.imgLink, new Font(14)));
        }

        savedFlowPane.getChildren().addAll(buttonArrayList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populate(medList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
