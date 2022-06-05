public class Cargo {

    private String good;
    private String formatted;
    private Integer quantity;
    private Integer totalVolume;

    public Cargo(String good, Integer quantity, Integer totalVolume){
        this.good = good;
        this.quantity = quantity;
        this.totalVolume = totalVolume;
    }

    public String getFormatted(){
        String newString = good.replace('_', '\n');
        return newString;
    }

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Integer totalVolume) {
        this.totalVolume = totalVolume;
    }

    @Override
    public String toString(){

        String newString = good.replace('_', '\n');
        return newString + "\nQuantity: " + quantity + "\nVolume: " + totalVolume + "\n";
    }
}
