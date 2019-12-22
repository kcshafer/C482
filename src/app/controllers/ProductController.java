package app.controllers;

import java.io.IOException;

import app.exceptions.ModelValidationException;
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

    private ObservableList<AbstractPart> productParts;
    private Product selectedProduct;

    public void initialize() {
        this.selectedProduct = getSelectedProduct();
        productParts = FXCollections.observableArrayList();

        System.out.println(selectedProduct);

        //if selected part is null, this is a modification
        if(selectedProduct == null) {
            int productId = Inventory.getProductCount();
            idField.setText(String.valueOf(productId));
        }
        else {
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

    public void addPartToProduct() {
        AbstractPart part = (AbstractPart)allPartsTable.getSelectionModel().getSelectedItem();
        System.out.println(Inventory.getProductCount());
        allPartsTable.getItems().remove(part);
        System.out.println(Inventory.getProductCount());
        currentPartsTable.getItems().add(part);
    }

    public void removePartToProduct(ActionEvent actionEvent) {
        AbstractPart part = (AbstractPart)currentPartsTable.getSelectionModel().getSelectedItem();
        currentPartsTable.getItems().remove(part);
        allPartsTable.getItems().add(part);
    }

    public void onSaveClick(ActionEvent actionEvent) throws IOException {
        String productName = nameField.getText();
        String productInv = inStockField.getText();
        String productPrice = priceField.getText();
        String productMin = minField.getText();
        String productMax = maxField.getText();

        Product product = new Product();
        product.setName(productName);
        product.setStock(Integer.parseInt(productInv.equals("") ? "0" : productInv));
        product.setPrice(Double.parseDouble(productPrice.equals("") ? "0"  : productPrice));
        product.setMin(Integer.parseInt(productMin.equals("") ? "0" : productMin));
        product.setMax(Integer.parseInt(productMax.equals("") ? "0" : productMax));

        for(AbstractPart part : productParts) {
            product.addAssociatedPart(part);
        }

        try {
            product.isValid();;

            if (this.selectedProduct != null) {
                selectedProduct.deleteAllAssociatedParts();
                product.setId(selectedProduct.getId());
                Inventory.updateProduct(selectedProduct.getId(), product);
            } else {
                product.setId(Inventory.getProductCount());
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

    private void setModifiedProductFields(Product product) {
        idField.setText(String.valueOf(product.getId()));
        nameField.setText(product.getName());
        inStockField.setText(String.valueOf(product.getStock()));
        priceField.setText(String.valueOf(product.getPrice()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
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