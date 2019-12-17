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

    public void onSaveClick(ActionEvent actionEvent) {
        System.out.println("Save PART");
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