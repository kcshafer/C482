package app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private final static ObservableList<Part> allParts;
    private final static ObservableList<Product> allProducts;


    static {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    public Inventory() {}

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int partId) {
        Part targetPart = null;
        for(Part part : allParts) {
            if(part.getId() == partId) {
                targetPart = part;
            }
        }

        return targetPart;
    }

    public static Product lookupProduct(int productId) {
        Product targetProduct = null;
        for(Product product : allProducts) {
            if(product.getId() == productId) {
                targetProduct = product;
            }
        }

        return targetProduct;
    }

    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }

    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static Integer getPartsCount() {
        return allParts.size();
    }

    public static Integer getProductCount() {
        return allProducts.size();
    }

    public static ObservableList<Part> getParts() {
        return allParts;
    }

    public static ObservableList<Product> getProducts() {
        return allProducts;
    }
}
