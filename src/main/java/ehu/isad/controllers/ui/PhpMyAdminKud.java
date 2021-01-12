package ehu.isad.controllers.ui;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import ehu.isad.controllers.db.PhpMyAdminDB;
import ehu.isad.model.PhpMyAdminModel;
import ehu.isad.utils.Sarea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

    public class PhpMyAdminKud {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TextField textField;

        @FXML
        private Button check;

        @FXML
        private TableView<PhpMyAdminModel> table;

        @FXML
        private TableColumn<PhpMyAdminModel, String> urlCol;

        @FXML
        private TableColumn<PhpMyAdminModel, String> md5Col;

        @FXML
        private TableColumn<PhpMyAdminModel, String> versionCol;

        @FXML
        private Label label;

        private ObservableList<PhpMyAdminModel> list = FXCollections.observableArrayList();

        private PhpMyAdminDB phpMyAdminDB = PhpMyAdminDB.getInstance();

        @FXML
        void onClick(ActionEvent event) {
            if(textField.getText().equals("")){
                label.setText("Ez duzu url-rik sartu");
            }else{
                String url = textField.getText()+"/README";
                if(netIsAvailable(url)) {
                    String hash = md5Kalkulatu(url);
                    if(hash!=null){
                        if(phpMyAdminDB.dagoHasha(hash)){
                            PhpMyAdminModel model = phpMyAdminDB.modelaHartu(hash);
                            model.setUrl(textField.getText());
                            list.add(model);
                            label.setText("Datubasean zegoen");
                        }else{
                            list.add(new PhpMyAdminModel(textField.getText(),hash,""));
                            label.setText("Ez da datubasean aurkitu");
                        }
                        table.setItems(list);
                    }
                }else{
                    label.setText("URL ez da existitzen");
                }
            }
        }

        private boolean netIsAvailable(String ur){
            try {
                final URL url = new URL(ur);
                final URLConnection conn = url.openConnection();
                conn.connect();
                conn.getInputStream().close();
                return true;
            } catch (MalformedURLException e) {
                //throw new RuntimeException(e);
                return false;
            } catch (IOException e) {
                return false;
            }

        }

        private String md5Kalkulatu(String url) {
            String text="";
            try {
                text = Sarea.getNireSarea().urlarenTextuaLortu(url);
                return md5Calc(text);
            } catch (IOException ioException) {
                label.setText("URL ez da existitzen");
            }
            return null;
        }

        private String md5Calc(String text){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(text.getBytes());
                BigInteger number = new BigInteger(1, messageDigest);
                String hashtext = number.toString(16);

                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
                return hashtext;
            }
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        private void taulaHasieratu(){
            urlCol.setCellValueFactory(new PropertyValueFactory<>("url"));
            md5Col.setCellValueFactory(new PropertyValueFactory<>("md5"));
            versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
            table.setEditable(true);
            versionCol.setCellFactory(TextFieldTableCell.forTableColumn());
            versionCol.setOnEditCommit(t -> {
                PhpMyAdminModel model = table.getSelectionModel().getSelectedItem();
                model.setVersion(t.getNewValue());
                phpMyAdminDB.addToDB(model);
                label.setText("md5 eta bertsio berria datubasean sartu dira");
            });
        }
        @FXML
        void initialize() {
            taulaHasieratu();
        }
    }

