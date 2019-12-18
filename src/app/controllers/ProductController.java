package app.controllers;

import java.io.IOException;

import app.models.AbstractPart;
import app.models.Inventory;
import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static app.controllers.MainScreenController.getSelectedProduct;

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

    private ObservableList<AbstractPart> productParts;

    public void initialize() {
        Product selectedProduct = getSelectedProduct();
        productParts = FXCollections.observableArrayList();

        //if selected part is null, this is a modification
        if(selectedProduct == null) {
            int productId = Inventory.getProductCount();
            idField.setText(String.valueOf(productId));
        }
        else {
            idField.setText(String.valueOf(selectedProduct.getId()));
            nameField.setText(selectedProduct.getName());
            inStockField.setText(String.valueOf(selectedProduct.getStock()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
            minField.setText(String.valueOf(selectedProduct.getMin()));
            maxField.setText(String.valueOf(selectedProduct.getMax()));

            this.setModifiedProductFields(selectedProduct);
            productParts = selectedProduct.getAllAssociatedParts();
        }

        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<AbstractPart, String>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<AbstractPart, String>("name"));
        allPartsInStockColumn.setCellValueFactory(new PropertyValueFactory<AbstractPart, String>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<AbstractPart, String>("price"));

        currentPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        currentPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        currentPartsInStockColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        currentPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

        allPartsTable.setItems(Inventory.getParts());

        currentPartsTable.setItems(productParts);
    }

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

    private void setModifiedProductFields(Product product) {
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inStockField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
    }
}