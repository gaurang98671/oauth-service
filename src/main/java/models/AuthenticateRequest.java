package models;

public class AuthenticateRequest {
    private String username;
    private String password;

    public AuthenticateRequest(String name, String password)
    {
        this.username= name;
        this.password=password;
    }

    public AuthenticateRequest(){}
    
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }
    
}
