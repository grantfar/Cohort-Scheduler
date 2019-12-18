package database.entities;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.data.annotation.Id;

public class User {

    @Id
    public String id;
    public String username;
    public String passwordHash;
    public boolean admin;

    public User(String username, String passwordHash, boolean admin) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.admin = admin;
    }

    @Override
    public String toString(){
        return String.format(
                "User[id=%s, username=%s, passwordHash=%s, accountType=%b]",
                id, username, passwordHash, admin
        );
    }
}
