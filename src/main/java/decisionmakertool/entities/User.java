package decisionmakertool.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
public class User implements Serializable {
    @Id
    @Column(name = "UNAME")
    private String uname;

    @Column(name = "UPASSWORD")
    private  String upassword;

    @OneToMany(mappedBy = "user")
    private List<User> users = new ArrayList<>();

    public User(){

    }

    public User(String uname, String upassword){
        this.uname = uname;
        this.upassword = upassword;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
