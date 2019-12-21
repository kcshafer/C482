package app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Formatter;

public class Product {
    private ObservableList<AbstractPart> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product() {
        this.associatedParts = FXCollections.observableArrayList();
    }

    public void addAssociatedPart(AbstractPart part) {
        this.associatedParts.add(part);
    }

    public boolean deleteAssociatedPart(AbstractPart part) {
        return this.associatedParts.remove(part);
    }

    public ObservableList<AbstractPart> getAllAssociatedParts() {
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
}
