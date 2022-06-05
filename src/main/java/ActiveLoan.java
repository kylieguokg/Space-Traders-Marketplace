
public class ActiveLoan {


    private String due;
    private String id;
    private Integer repaymentAmount;

    enum Status {
        CURRENT,
        PAID
    }

    private Status status;
    private String type;


    public ActiveLoan(String due, String id,  Integer repaymentAmount, Status status, String type){
        this.due = due;
        this.id = id;
        this.repaymentAmount = repaymentAmount;
        this.status = status;
        this.type = type;
    }

    public String getDue() {
        return due;
    }

    public String getId() {
        return id;
    }

    public Integer getRepaymentAmount() {
        return repaymentAmount;
    }

    public Status getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Due: " + due
                + "\nID: " + id +
                "\nRepayment Amount: " + repaymentAmount
                + "\nStatus: " +  status + " | Type: " +  type;
    }
}
