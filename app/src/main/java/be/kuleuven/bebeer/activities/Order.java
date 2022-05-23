package be.kuleuven.bebeer.activities;

public class Order {
    String orderID, orderQuantity, orderDate, orderAddress;

    public Order(String orderID, String orderQuantity, String orderDate, String orderAddress) {
        this.orderID = orderID;
        this.orderQuantity = orderQuantity;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }
}
