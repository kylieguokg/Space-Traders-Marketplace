public class Order {

    private String good;
    private Integer pricePerUnit;
    private Integer quantity;
    private Integer total;

    public Order(String good, Integer pricePerUnit, Integer quantity, Integer total){
        this.good = good;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.total = total;
    }

    @Override
    public String toString(){
        return "Good: " + good  + " | Price per unit: " + pricePerUnit + " | Quantity: " + quantity
                +  " | Total: " + total + "\n";
    }
}
