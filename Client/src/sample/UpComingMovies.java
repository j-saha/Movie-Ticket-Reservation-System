package sample;

public class UpComingMovies {
    private String name;
    private String startdate;
    private String enddate;

    public UpComingMovies(String name, String startdate, String enddate) {
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
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
}
