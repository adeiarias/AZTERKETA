package ehu.isad;

import ehu.isad.controllers.ui.PhpMyAdminKud;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Parent azterketaUI;
    private Stage stage;
    private PhpMyAdminKud controller;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pantailaHasieratu();
        stage.setTitle("AZTERKETAKO ARIKETA");
        stage.setScene(scene);
        stage.show();
    }

    public void pantailaHasieratu() throws IOException {
        FXMLLoader loaderMezuak = new FXMLLoader(getClass().getResource("/phpMyAdmin.fxml"));
        azterketaUI = (Parent)loaderMezuak.load();
        controller = loaderMezuak.getController();
        scene = new Scene(azterketaUI);

    }

    public static void main(String[] args) {
        launch(args);
    }
}