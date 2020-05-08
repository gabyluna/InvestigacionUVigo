package decisionmakertool.entities;

import java.util.Date;

//@Entity(name = "historial")
public class Historial  {
    //@Id
    //@Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "PATH")
    private  String path;

    //@Column(name = "TYPE")
    private  String type;

    //@Column(name = "STATE")
    private  boolean state;

    //@JoinColumn(name="UNAME")
    private String uname;

    private Date date;

    private String description;

    private int quickFix;

    public Historial(){}

    public Historial(String path, String type, boolean state, String uname){
        this.path = path;
        this.type = type;
        this.state = state;
        this.setUname(uname);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuickFix() {
        return quickFix;
    }

    public void setQuickFix(int quickFix) {
        this.quickFix = quickFix;
    }

    /*public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/

}


