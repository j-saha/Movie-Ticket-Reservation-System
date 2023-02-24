package sample;

public class moviehistory {
    private String id;
    private String name;
    private String date;
    private String time;
    private String number;

    public moviehistory(String id, String name, String date, String time, String number) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
