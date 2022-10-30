import model.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Supermarket {
    private final HashMap<Product, Integer> products;
    private static final ReentrantLock lock = new ReentrantLock();

    public Supermarket() {
        this.products = new HashMap<>();
    }

    public Set<Product> getProducts() {
        return products.keySet();
    }

    public boolean containsProduct(Product product) {
        return products.containsKey(product);
    }

    public int getQuantityOfProduct(Product product) {
        return products.get(product);
    }

    public double computeValue() {
        double totalPrice = 0.0;
        for (Map.Entry<Product, Integer> productQuantityPair : this.products.entrySet()) {
            totalPrice += productQuantityPair.getKey().getPrice() * productQuantityPair.getValue();
        }
        return totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        products.putIfAbsent(product, quantity);
    }

    public void removeProduct(Product product, int quantity) {
        if (!this.products.containsKey(product)) {
            throw new RuntimeException("Not enough products in the supermarket!");
        }

        int previousQuantity = this.products.get(product);
        if (previousQuantity < quantity) {
            throw new RuntimeException("Product does not exist in the supermarket!");
        }
        if (previousQuantity == quantity) {
            this.products.remove(product);
        }
        else {
            this.products.replace(product, previousQuantity - quantity);
        }
    }
}
