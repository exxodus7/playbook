package com.schroetech.playbook.controller;

import com.jfoenix.controls.JFXButton;
import com.schroetech.playbook.persistence.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author lauren
 */
public class ProjectsController implements Initializable {

    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateProjects();
    }

    private void populateProjects() {

        List<Project> projects = Project.findAll();
        int i = 0;
        for (Project project : projects) {
            displayProject(i, project.getProjectName(), project.getProjectId(), project.getMainImageUrl(), project.getLastActivity(), "ima-form-id");
            i++;
        }
    }

    private void displayProject(int column, String projectTitle, String projectId, String imagePath, Date lastActivityDate, String quickFormId) {
        grid.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setPrefSize(270, 340);
        vbox.setStyle("-fx-background-color: white;");
        GridPane.setMargin(vbox, new Insets(10, 10, 10, 10));

        ImageView image = new ImageView(getClass().getClassLoader().getResource(imagePath).toExternalForm());
        image.setFitWidth(250);
        image.setFitHeight(250);
        image.setPickOnBounds(true);
        image.setPreserveRatio(true);
        image.setX(50);

        Label projectTitleLbl = new Label(projectTitle);
        projectTitleLbl.setPrefSize(270, 20);
        projectTitleLbl.setAlignment(Pos.CENTER);
        projectTitleLbl.setTextFill(Paint.valueOf("#1fa9b5"));
        projectTitleLbl.setFont(Font.font(16));
        projectTitleLbl.setPadding(new Insets(10, 10, 10, 10));

        Label lastActivityLabel = new Label("Last Activity: " + lastActivityDate.toString());
        lastActivityLabel.setAlignment(Pos.CENTER);
        lastActivityLabel.setPrefSize(300, 17);
        lastActivityLabel.setFont(Font.font(11));
        lastActivityLabel.setPadding(new Insets(10, 10, 10, 5));

        HBox hbox = new HBox();
        hbox.setPrefSize(200, 100);

        JFXButton quickFormBtn = new JFXButton("Form");
        quickFormBtn.setUserData(quickFormId);
        quickFormBtn.setButtonType(JFXButton.ButtonType.RAISED);
        quickFormBtn.setStyle("-fx-background-color: #00C0EF;");
        quickFormBtn.setTextFill(Paint.valueOf("WHITE"));
        quickFormBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JFXButton formButton = (JFXButton) e.getSource();
                launchForm((String) formButton.getUserData());
            }
        });
        HBox.setMargin(quickFormBtn, new Insets(10, 5, 10, 10));

        JFXButton quickNoteBtn = new JFXButton("Quick Note");
        quickNoteBtn.setButtonType(JFXButton.ButtonType.RAISED);
        quickNoteBtn.setStyle("-fx-background-color: #5cb85c;");
        quickNoteBtn.setTextFill(Paint.valueOf("WHITE"));
        quickNoteBtn.setUserData(projectId);
        quickNoteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JFXButton formButton = (JFXButton) e.getSource();
                launchQuickNote((String) formButton.getUserData());
            }
        });
        HBox.setMargin(quickNoteBtn, new Insets(10, 5, 10, 5));

        JFXButton openProjectBtn = new JFXButton("Open Project");
        openProjectBtn.setButtonType(JFXButton.ButtonType.RAISED);
        openProjectBtn.setStyle("-fx-background-color: #FF4500;");
        openProjectBtn.setTextFill(Paint.valueOf("WHITE"));
        openProjectBtn.setUserData(projectId);
        openProjectBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JFXButton formButton = (JFXButton) e.getSource();
                System.out.println("Opening project with id: " + formButton.getUserData());
            }
        });
        HBox.setMargin(openProjectBtn, new Insets(10, 10, 10, 5));

        FontAwesomeIconView settingsIcon = new FontAwesomeIconView();
        settingsIcon.setFill(Paint.valueOf("#1fa9b5"));
        settingsIcon.setGlyphName("GEAR");
        settingsIcon.setGlyphSize(20);
        GridPane.setValignment(settingsIcon, VPos.TOP);
        GridPane.setHalignment(settingsIcon, HPos.RIGHT);
        GridPane.setMargin(settingsIcon, new Insets(15, 15, 0, 0));

        grid.add(vbox, column, 0);
        vbox.getChildren().add(image);
        vbox.getChildren().add(projectTitleLbl);
        vbox.getChildren().add(lastActivityLabel);

        vbox.getChildren().add(hbox);
        hbox.getChildren().add(quickFormBtn);
        hbox.getChildren().add(quickNoteBtn);
        hbox.getChildren().add(openProjectBtn);

        grid.add(settingsIcon, column, 0);

    }

    private void launchForm(String formId) {
        System.out.println("Launching form: " + formId);
    }

    private void launchQuickNote(String projectId) {
        System.out.println("Launching quick note for project: " + projectId);
    }
}
