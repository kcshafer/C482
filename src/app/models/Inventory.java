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

    public static AbstractPart lookupPart(int partId) {
        AbstractPart targetPart = null;
        for(AbstractPart part : allParts) {
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

    public static void updatePart(int index, AbstractPart part) {
        allParts.set(index, part);
    }

    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    public static boolean deletePart(int partId) {
        boolean isSuccess =  false;
        for(AbstractPart part : allParts) {
            if(part.getId() == partId) {
                allParts.remove(part);
                isSuccess = true;
            }
        }

        return isSuccess;
    }

    public static boolean deleteProduct(Product product) {
        return true;
    }

    public static ObservableList<AbstractPart> getAllParts() {
        return allParts;
    }

    public static Integer getPartsCount() {
        return allParts.size();
    }

    public static Integer getProductCount() {
        return allProducts.size();
    }

    public static ObservableList<AbstractPart> getParts() {
        return allParts;
    }

    public static ObservableList<Product> getProducts() {
        return allProducts;
    }
}
