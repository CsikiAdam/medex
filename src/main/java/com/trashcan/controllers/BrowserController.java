package com.trashcan.controllers;

import com.trashcan.account.User;
import com.trashcan.filemanager.FileManager;
import com.trashcan.med.Med;
import javafx.fxml.FXML;
import com.trashcan.database.DB;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class BrowserController implements Initializable {
    @FXML
    private FlowPane browserFlowPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private CheckBox dangerousCheckBox;

    private final DB db = new DB();

    private final FileManager fileManager = new FileManager();

    private User user = fileManager.getUser();

    private ArrayList<Med> medList = fileManager.getCurentMeds(user.name);

    public BrowserController() throws IOException {
    }

    /**
     * <h1>generateProduct()</h1>
     * <p>It creates a button from the elements of a Med type object</p>
     * @param title
     * @param x
     * @param y
     * @param mid
     * @param firm
     * @param substActiva
     * @param medType
     * @param madeFor
     * @param adm
     * @param description
     * @param imgLink
     * @param font
     * @return
     */
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

            Button saveButton = new Button("Save");
            HBox.setMargin(saveButton, new Insets(0, 0, 0, 5));

            saveButton.setOnAction(e2 -> {
                if(!medList.contains(med)) {
                    medList.add(med);
                }

                try {
                    fileManager.saveMeds(medList, user.name);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            buttonHBox.getChildren().addAll(closeButton, saveButton);

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

    public void search() throws SQLException {
        browserFlowPane.getChildren().clear();
        if(Objects.equals(searchTextField.getText(), "") || Objects.equals(searchTextField.getText(), null)) {
            PreparedStatement statement = db.dataBase.prepareStatement("select * from magmed.meds where danger=?");

            statement.setInt(1, dangerousCheckBox.isSelected() ? 1 : 0);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            populate(resultSet);

            System.out.println("Search empty\n");
        }
        else {
            String sql = String.format("select * from magmed.meds where danger=? and ((medName like %s) or (firm like %s) or (substActv like %s) or (medType like %s) or (madeFor like %s) or (adm like %s))", String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()), String.format("'%%%s%%'", searchTextField.getText()));
            PreparedStatement statement = db.dataBase.prepareStatement(sql);
            statement.setInt(1, dangerousCheckBox.isSelected() ? 1 : 0);
            statement.executeQuery();

            statement.executeQuery();
            System.out.println(searchTextField.getText());

            ResultSet resultSet = statement.getResultSet();
            if(resultSet != null) {
                populate(resultSet);
            }
            else {
                System.out.println("Result set is null\n");
            }

            statement.close();
            assert resultSet != null;
            resultSet.close();
        }
    }

    public void reset() throws SQLException {
        browserFlowPane.getChildren().clear();
        populate();
    }

    public void populate() throws SQLException {
        Statement statement = db.dataBase.createStatement();
        ResultSet rs = statement.executeQuery("select * from magmed.meds");

        ArrayList<Button> buttonArrayList = new ArrayList<>();

        while(rs.next()) {
            for(int i=0; i<1; i++) {
                buttonArrayList.add(generateProduct(rs.getString("medName"),
                        100,
                        120,
                        rs.getInt("mid"),
                        rs.getString("firm"),
                        rs.getString("substActv"),
                        rs.getString("medType"),
                        rs.getString("madeFor"),
                        rs.getString("adm"),
                        rs.getString("description"),
                        rs.getString("img"),
                        new Font(14)));
            }
        }

        browserFlowPane.getChildren().addAll(buttonArrayList);

        rs.close();
        statement.close();
    }

    public void populate(ResultSet rs) throws SQLException {
        ArrayList<Button> buttonArrayList = new ArrayList<>();

        while(rs.next()) {
            for(int i=0; i<1; i++) {
                buttonArrayList.add(generateProduct(rs.getString("medName"),
                        100,
                        120,
                        rs.getInt("mid"),
                        rs.getString("firm"),
                        rs.getString("substActv"),
                        rs.getString("medType"),
                        rs.getString("madeFor"),
                        rs.getString("adm"),
                        rs.getString("description"),
                        rs.getString("img"),
                        new Font(14)));
            }
        }

        browserFlowPane.getChildren().addAll(buttonArrayList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
