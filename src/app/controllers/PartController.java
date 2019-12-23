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

import app.models.Part;
import app.models.InHouse;
import app.models.Inventory;
import app.models.Outsourced;
import static app.controllers.MainScreenController.getSelectedPart;
import static app.controllers.MainScreenController.setSelectedPart;


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
        Part part = getSelectedPart();
        if(part == null) {
            int partId = Inventory.getPartsCount();
            idField.setText(String.valueOf(partId));
            this.setFieldToMachineId();
        }
        else {
            this.setModifiedPartFields(part);
        }
    }

    public void onSelectInHouse(ActionEvent actionEvent) {
        this.setFieldToMachineId();
    }

    public void onSelectOutsourced(ActionEvent actionEvent) {
        this.setFieldToCompanyName();
    }

    private void setFieldToMachineId() {
        this.inHouseRadioBtn.setSelected(true);
        this.outsourcedRadioBtn.setSelected(false);
        this.sourceTypeConditionalLabel.setText("Machine Id");
        this.sourceTypeConditionalField.setPromptText("Mach Id");
    }

    private void setFieldToCompanyName() {
        this.outsourcedRadioBtn.setSelected(true);
        this.inHouseRadioBtn.setSelected(false);
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

        Part part;

        // determine what type of part we are creating
        if (inHouseRadioBtn.isSelected()) {
            Integer machineId = Integer.parseInt(this.sourceTypeConditionalField.getText());

            InHouse inHouse = new InHouse(Inventory.getPartsCount(), name, price, inventoryCount, min, max, machineId);

            part = inHouse;
        } else {
            Outsourced outsourced = new Outsourced(Inventory.getPartsCount(), name, price, inventoryCount, min, max,
                    this.sourceTypeConditionalField.getText());
            outsourced.setName(name);
            outsourced.setPrice(price);
            outsourced.setStock(inventoryCount);
            outsourced.setMin(min);
            outsourced.setMax(max);

            part = outsourced;
        }

        // set part id if not modified
        Part selectedPart = getSelectedPart();

        if(selectedPart != null) {
            int partId = selectedPart.getId();
            part.setId(partId);
            Inventory.updatePart(part.getId(), part);
        }
        else {
            Inventory.addPart(part);
        }

        this.openMainScreen(actionEvent);
    }

    public void onCancelClick(ActionEvent actionEvent) throws IOException {
        this.openMainScreen(actionEvent);
    }


    private void openMainScreen(ActionEvent actionEvent) throws IOException {
        setSelectedPart(null);

        final String MAIN_SCREEN_PATH = "../views/MainScreen.fxml";
        Parent partsScreenLoader = FXMLLoader.load(getClass().getResource(MAIN_SCREEN_PATH));

        Scene partsScene = new Scene(partsScreenLoader);
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(partsScene);
        window.show();
    }

    private void setModifiedPartFields(Part part) {
        idField.setText(String.valueOf(part.getId()));
        nameField.setText(part.getName());
        inventoryCountField.setText(String.valueOf(part.getStock()));
        priceField.setText(String.valueOf(part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));

        String conditionalText;
        if(part instanceof Outsourced) {
            Outsourced outsourced = (Outsourced)part;
            conditionalText = outsourced.getCompanyName();
            this.setFieldToCompanyName();
        }
        else {
            InHouse inHouse = (InHouse)part;
            conditionalText = String.valueOf(inHouse.getMachineId());
            this.setFieldToMachineId();
        }

        sourceTypeConditionalField.setText(conditionalText);
    }
}