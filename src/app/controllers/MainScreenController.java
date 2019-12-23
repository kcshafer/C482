package app.controllers;

import app.models.Part;
import app.models.Inventory;
import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private static Part selectedPart;
    private static Product selectedProduct;

    @FXML
    public void initialize() {
        this.setCellValueFactories();
        this.loadPartsTable();
        this.loadProductsTable();
    }

    public static Part getSelectedPart() {
        return selectedPart;
    }

    public static void setSelectedPart(Part part) {
        selectedPart = part;
    }

    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    public static void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    @FXML
    public void onSearchProductClick(ActionEvent actionEvent) throws IOException {
        String productSearchText = productsSearchField.getText();
        if (productSearchText.length() > 0) {
            int productId = Integer.parseInt(productSearchText);
            Product product = Inventory.lookupProduct(productId);

            ObservableList<Product> products = FXCollections.observableArrayList(product);

            productsTable.setItems(products);
        } else {
            this.loadProductsTable();
        }
    }

    @FXML
    public void onAddProductClick(ActionEvent actionEvent) throws IOException {
        this.openProductScreen(actionEvent);
    }

    @FXML
    public void onModifyProductClick(ActionEvent actionEvent) throws IOException {
        selectedProduct = (Product)productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            this.openProductScreen(actionEvent);
        }
    }

    @FXML
    public void onDeleteProductClick(ActionEvent actionEvent) {
        Product product = (Product)productsTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(product);
        this.loadProductsTable();
    }

    @FXML
    public void onSearchPartClick(ActionEvent actionEvent) throws IOException {
        String partSearchText = partsSearchText.getText();
        if (partSearchText.length() > 0) {
            int partId = Integer.parseInt(partSearchText);
            Part part = Inventory.lookupPart(partId);

            ObservableList<Part> parts = FXCollections.observableArrayList(part);

            partsTable.setItems(parts);
        } else {
            this.loadPartsTable();
        }
    }

    @FXML
    public void onAddPartClick(ActionEvent actionEvent) throws IOException {
        this.openPartScreen(actionEvent);
    }

    @FXML
    public void onModifyPartClick(ActionEvent actionEvent) throws IOException {
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            this.openPartScreen(actionEvent);
        }
    }

    @FXML
    public void onDeletePartClick(ActionEvent actionEvent) {
        Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(part);
        this.loadPartsTable();
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

    private void setCellValueFactories() {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelStockCol.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInvLevelCol.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));
    }

    private void loadPartsTable() {
        this.partsTable.setItems(Inventory.getParts());
    }

    private void loadProductsTable() {
        this.productsTable.setItems(Inventory.getProducts());
    }

    public void onExitClick(ActionEvent actionEvent) {
        System.exit(0);
    }
}