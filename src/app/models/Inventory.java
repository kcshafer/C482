package app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private final static ObservableList<AbstractPart> allParts;
    private final static ObservableList<Product> allProducts;


    static {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    public Inventory() {}

    public static void addPart(AbstractPart part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public AbstractPart lookupPart(int partId) {
        return null;
    }

    public Product lookupProduct(int productId) {
        return null;
    }

    public static void updatePart(int index, AbstractPart part) {
        allParts.set(index, part);
    }

    public void updateProduct(int index, Product newProduct) {

    }

    public boolean deletePart(AbstractPart selectedPart) {
        return true;
    }

    public boolean deleteProduct(Product product) {
        return true;
    }

    public static ObservableList<AbstractPart> getAllParts() {
        return allParts;
    }

    public static Integer getPartsCount() {
        return allParts.size();
    }

    public static ObservableList<AbstractPart> getParts() {
        return allParts;
    }

    public static ObservableList<Product> getProducts() {
        return allProducts;
    }
}
