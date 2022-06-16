package ro.ubb.ordersJSP.Domain;

public class Order {
    private String user;
    private String product;
    private int quantity;

    public Order(String user, String product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
