package app.controllers;

import java.io.IOException;

import app.exceptions.ModelValidationException;
import app.models.Part;
import app.models.Inventory;
import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static app.controllers.MainScreenController.getSelectedProduct;
import static app.controllers.MainScreenController.setSelectedProduct;

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
    public TextField productPartsSearchField;

    private ObservableList<Part> productParts;
    private Product selectedProduct;

    public void initialize() {
        this.selectedProduct = getSelectedProduct();
        productParts = FXCollections.observableArrayList();

        //if selected part is null, this is a modification
        if(selectedProduct == null) {
            int productId = Inventory.getProductCount();
            idField.setText(String.valueOf(productId));
        }
        else {
            this.setModifiedProductFields(selectedProduct);

            productParts = selectedProduct.getAllAssociatedParts();
        }

        allPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        allPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        allPartsInStockColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        allPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        currentPartsIdColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        currentPartsNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        currentPartsInStockColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        currentPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

        this.populateAllPartsTable();

        this.currentPartsTable.setItems(productParts);
    }

    public void addPartToProduct() {
        Part part = (Part)allPartsTable.getSelectionModel().getSelectedItem();
        allPartsTable.getItems().remove(part);
        currentPartsTable.getItems().add(part);
    }

    public void removePartToProduct(ActionEvent actionEvent) {
        Part part = (Part)currentPartsTable.getSelectionModel().getSelectedItem();
        currentPartsTable.getItems().remove(part);
        allPartsTable.getItems().add(part);
    }

    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        String name = nameField.getText();
        int stock = Integer.parseInt(inStockField.getText().equals("") ? "0" : inStockField.getText());
        double price = Double.parseDouble(priceField.getText().equals("") ? "0"  : priceField.getText());
        int min = Integer.parseInt(minField.getText().equals("") ? "0" : minField.getText());
        int max = Integer.parseInt(minField.getText().equals("") ? "0" : minField.getText());

        Product product = new Product(Inventory.getProductCount(), name, price, stock, min, max);


        for(Part part : productParts) {
            product.addAssociatedPart(part);
        }

        try {
            product.isValid();;

            if (this.selectedProduct != null) {
                selectedProduct.deleteAllAssociatedParts();
                product.setId(selectedProduct.getId());
                Inventory.updateProduct(selectedProduct.getId(), product);
            } else {
                Inventory.addProduct(product);
            }

            this.openMainScreen(actionEvent);
        } catch (ModelValidationException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Model Validation Error");
            alert.setHeaderText("Product not valid");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        this.openMainScreen(actionEvent);
    }

    public void onProductPartSearch(ActionEvent actionEvent) {
        String partSearchText = productPartsSearchField.getText();
        if (partSearchText.length() > 0) {
            int partId = Integer.parseInt(partSearchText);
            Part part = Inventory.lookupPart(partId);

            ObservableList<Part> parts = FXCollections.observableArrayList(part);

            allPartsTable.setItems(parts);
        } else {
            this.populateAllPartsTable();
        }
    }

    private void setModifiedProductFields(Product product) {
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inStockField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
    }

    private void populateAllPartsTable() {
        // this assigns the parts to new memory so that the inventory parts array is separate memory assignment from
        // the product all parts table array
        ObservableList<Part> parts = FXCollections.observableArrayList();
        ObservableList<Part> associatedParts = this.selectedProduct != null ?
                                                       this.selectedProduct.getAllAssociatedParts() :
                                                       FXCollections.observableArrayList();

        //this prevents adding parts that are already associated to the product to the all parts table
        for(Part part : Inventory.getParts()) {
            if(!associatedParts.contains(part)) {
               parts.add(part);
            }
        }

        this.allPartsTable.setItems(parts);
    }

    private void openMainScreen(ActionEvent actionEvent) throws IOException {
        setSelectedProduct(null);

        final String MAIN_SCREEN_PATH = "../views/MainScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(MAIN_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }
}