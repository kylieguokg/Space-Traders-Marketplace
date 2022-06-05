public class Good {

    private String symbol;
    private Integer pricePerUnit;
    private Integer quantityAvailable;

    public Good(String symbol, Integer pricePerUnit, Integer quantityAvailable, Integer volumePerUnit, Integer purchasePricePerUnit, Integer spread, Integer sellPricePerUnit) {
        this.symbol = symbol;
        this.pricePerUnit = pricePerUnit;
        this.quantityAvailable = quantityAvailable;
        this.volumePerUnit = volumePerUnit;
        this.purchasePricePerUnit = purchasePricePerUnit;
        this.spread = spread;
        this.sellPricePerUnit = sellPricePerUnit;
    }

    private Integer volumePerUnit;
    private Integer purchasePricePerUnit;
    private Integer spread;
    private Integer sellPricePerUnit;


    public String getSymbol() {
        return symbol;
    }

    public String getFormatted(){
        String newString = symbol.replace("_", "\n");
        return newString;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getVolumePerUnit() {
        return volumePerUnit;
    }

    public void setVolumePerUnit(Integer volumePerUnit) {
        this.volumePerUnit = volumePerUnit;
    }

    public Integer getPurchasePricePerUnit() {
        return purchasePricePerUnit;
    }

    public void setPurchasePricePerUnit(Integer purchasePricePerUnit) {
        this.purchasePricePerUnit = purchasePricePerUnit;
    }

    public Integer getSpread() {
        return spread;
    }

    public void setSpread(Integer spread) {
        this.spread = spread;
    }

    public Integer getSellPricePerUnit() {
        return sellPricePerUnit;
    }

    public void setSellPricePerUnit(Integer sellPricePerUnit) {
        this.sellPricePerUnit = sellPricePerUnit;
    }

    @Override
    public String toString(){
        return symbol + " \n " + "$" + pricePerUnit + "/unit \n " + "volume: " + volumePerUnit + "/unit";
    }
//        return "Good: " + "Symbol: " + symbol
//                + "\nPrice per unit: " + pricePerUnit + " | Quantity Available: " + quantityAvailable
//                +  "\nVolume Per Unit: " + volumePerUnit + "\n";
//    }
}
