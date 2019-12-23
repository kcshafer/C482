package app.models;

import app.exceptions.ModelValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

        this.associatedParts = FXCollections.observableArrayList();
    }

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(Part part) {
        return this.associatedParts.remove(part);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    public void deleteAllAssociatedParts() {
        this.associatedParts = FXCollections.observableArrayList();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void isValid() throws ModelValidationException {
        if(this.associatedParts.size() == 0) {
            throw new ModelValidationException("Product needs at least one associated part");
        }

        if(this.name.equals("")) {
            throw new ModelValidationException("Product must have a name");
        }

        if(this.price == 0.0) {
            throw new ModelValidationException("Product must have a price");
        }

        if(this.stock == 0) {
            throw new ModelValidationException("Product must have an inventory level");
        }
    }
}
