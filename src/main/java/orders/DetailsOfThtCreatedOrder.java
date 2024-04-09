package order;

public class DetailsOfThtCreatedOrder {
    private Order order;

    public DetailsOfThtCreatedOrder(Order order) {
        this.order = order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
