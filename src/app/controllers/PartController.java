package app.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import app.models.AbstractPart;
import app.models.InHousePart;
import app.models.Inventory;
import app.models.OutsourcedPart;
import static app.controllers.MainScreenController.getSelectedPart;


public class PartController {
    public RadioButton outsourcedRadioBtn;
    public RadioButton inHouseRadioBtn;
    public TextField idField;
    public TextField nameField;
    public TextField inventoryCountField;
    public TextField priceField;
    public TextField minField;
    public TextField maxField;
    public Label sourceTypeConditionalLabel;
    public TextField sourceTypeConditionalField;
    public ToggleGroup sourceTypeToggleGroup;

    @FXML
    public void initialize() {
        AbstractPart part = getSelectedPart();
        if(part == null) {
            int partId = Inventory.getPartsCount();
            idField.setText(String.valueOf(partId));
        }
        else {
            this.setModifiedPartFields(part);
        }
        this.setFieldToMachineId();
    }

    public void onSelectInHouse(ActionEvent actionEvent) {
        this.setFieldToMachineId();
    }

    public void onSelectOutsourced(ActionEvent actionEvent) {
        this.setFieldToCompanyName();
    }

    private void setFieldToMachineId() {
        this.sourceTypeConditionalLabel.setText("Machine Id");
        this.sourceTypeConditionalField.setPromptText("Mach Id");
    }

    private void setFieldToCompanyName() {
        this.sourceTypeConditionalLabel.setText("Company Name");
        this.sourceTypeConditionalField.setPromptText("Comp Nm");
    }

  public void onSaveClick(ActionEvent actionEvent) throws IOException {
        // get data
        String name = this.nameField.getText();
        Double price = Double.parseDouble(this.priceField.getText());
        Integer inventoryCount = Integer.parseInt(this.inventoryCountField.getText());
        Integer min = Integer.parseInt(this.minField.getText());
        Integer max = Integer.parseInt(this.maxField.getText());

        AbstractPart part;

        // determine what type of part we are creating
        if (inHouseRadioBtn.isSelected()) {
            InHousePart inHousePart = new InHousePart();
            inHousePart.setName(name);
            inHousePart.setPrice(price);
            inHousePart.setStock(inventoryCount);
            inHousePart.setMin(min);
            inHousePart.setMax(max);

            Integer machineId = Integer.parseInt(this.sourceTypeConditionalField.getText());
            inHousePart.setMachineId(machineId);
            part = inHousePart;
        } else {
            OutsourcedPart outsourcedPart = new OutsourcedPart();
            outsourcedPart.setName(name);
            outsourcedPart.setPrice(price);
            outsourcedPart.setStock(inventoryCount);
            outsourcedPart.setMin(min);
            outsourcedPart.setMax(max);

            outsourcedPart.setCompanyName(this.sourceTypeConditionalField.getText());
            part = outsourcedPart;
        }

        // set part id if not modified
        AbstractPart selectedPart = getSelectedPart();

        if (selectedPart == null) {
            part.setId(Inventory.getPartsCount());
            Inventory.addPart(part);
        } else {
            int partId = selectedPart.getId();
            part.setId(partId);
            Inventory.updatePart(part.getId(), part);
        }

        this.openMainScreen(actionEvent);
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        final String PART_SCREEN_PATH = "../views/MainScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(PART_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }


    private void openMainScreen(ActionEvent actionEvent) throws IOException {
        final String MAIN_SCREEN_PATH = "../views/MainScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(MAIN_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }

    private void setModifiedPartFields(AbstractPart part) {
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        inventoryCountField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
    }
}