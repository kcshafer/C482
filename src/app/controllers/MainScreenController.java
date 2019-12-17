package app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {
    public Label screenTitle;
    public Button exitButton;
    public TextField partsSearchText;
    public TableView partsTable;
    public TableColumn partIdColumn;
    public TableColumn partNameColumn;
    public TableColumn partInvLevelStockCol;
    public TableColumn partPriceCol;
    public TextField productsSearchField;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInvLevelCol;
    public TableColumn productPriceCol;
    public TableView productsTable;

    //private static AbstractPart selectedPart;

    @FXML
    public void onSearchProductClick(ActionEvent actionEvent) throws IOException {
        //use inventory search to find part by id

        //if part is found set to selected part otherwise show error

        this.openProductScreen(actionEvent);
    }

    @FXML
    public void onAddProductClick(ActionEvent actionEvent) throws IOException {
        this.openProductScreen(actionEvent);
    }

    @FXML
    public void onModifyProductClick(ActionEvent actionEvent) throws IOException {
        //get selected product and assign to selectedProduct

        this.openProductScreen(actionEvent);
    }

    @FXML
    public void onDeleteProductClick(ActionEvent actionEvent) {
        //delete part using inventory interface

        System.out.println("Delete PRODUCT");
    }

    @FXML
    public void onSearchPartClick(ActionEvent actionEvent) throws IOException {
        //use inventory search to find part by id

        //if part is found set to selected part otherwise show error

        this.openPartScreen(actionEvent);
    }

    @FXML
    public void onAddPartClick(ActionEvent actionEvent) throws IOException {
        this.openPartScreen(actionEvent);
    }

    @FXML
    public void onModifyPartClick(ActionEvent actionEvent) throws IOException {
        //get selected part

        this.openPartScreen(actionEvent);
    }

    @FXML
    public void onDeletePartClick(ActionEvent actionEvent) {
        //delete part using inventory interface

        System.out.println("Delete PART");
    }

    private void openPartScreen(ActionEvent actionEvent) throws IOException {
        final String PART_SCREEN_PATH = "../views/PartScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(PART_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }

    private void openProductScreen(ActionEvent actionEvent) throws IOException {
        final String PRODUCT_SCREEN_PATH = "../views/ProductScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(PRODUCT_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }
}
