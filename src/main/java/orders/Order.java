package orders;

public class Order {
    private int id;
    private String firstName;
    private String lastName;
    private  String address;
    private  int metroStation;
    private  String phone;
    private  int rentTime;
    private  String deliveryDate;
    private int track;
    private int status;
    private String[] colour;
    private String comment;
    private boolean cancelled;
    private boolean finished;
    private boolean inDelivery;
    private String courierFirstName;
    private String createdAt;
    private String updatedAt;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
