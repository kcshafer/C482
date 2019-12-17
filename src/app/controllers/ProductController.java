package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ProductController {
    public TextField idField;
    public TextField nameField;
    public TextField inStockField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public TextField ProductPartsSearchField;
    public TableView allPartsTable;
    public TableColumn allPartsIdColumn;
    public TableColumn allPartsNameColumn;
    public TableColumn allPartsInStockColumn;
    public TableColumn allPartsPriceColumn;
    public TableView currentPartsTable;
    public TableColumn currentPartsIdColumn;
    public TableColumn currentPartsNameColumn;
    public TableColumn currentPartsInStockColumn;
    public TableColumn currentPartsPriceColumn;

    public void onSaveClick(ActionEvent actionEvent) {
        System.out.println("Save PRODUCT");
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        final String PART_SCREEN_PATH = "../views/MainScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(PART_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }
}