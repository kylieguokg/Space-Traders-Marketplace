public class Loan {

    private Integer amount;
    private boolean collateralRequired;
    private Integer rate;
    private Integer termInDays;
    private String type;

    public Loan(Integer amount, boolean collateralRequired, Integer rate, Integer termInDays, String type){
        this.amount = amount;
        this.collateralRequired = collateralRequired;
        this.rate = rate;
        this.termInDays = termInDays;
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public boolean isCollateralRequired() {
        return collateralRequired;
    }

    public Integer getRate() {
        return rate;
    }

    public Integer getTermInDays() {
        return termInDays;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Amount: " + amount
                + "\nCollateral Required: " + collateralRequired
                + "\nRate: " + rate + " | Term in Days: " + termInDays
                + "\nType: " +  type;
    }
}
