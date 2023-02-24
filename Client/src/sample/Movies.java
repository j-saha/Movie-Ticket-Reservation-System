package sample;
public class Movies {
    private String name;
    private String startdate;
    private String enddate;
    private String income;

    public Movies(String name, String startdate, String enddate, String income) {
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}

