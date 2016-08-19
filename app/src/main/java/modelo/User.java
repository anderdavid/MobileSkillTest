package modelo;

import android.util.Log;

/**
 * Created by Anderdavid on 13/08/2016.
 */
public class User {

    private static final String TAG="User";

    private String login;
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlRegister(String url){

        StringBuilder urlRegister = new StringBuilder(url);
        urlRegister.append("?");
        urlRegister.append("username="+this.login+"&");
        urlRegister.append("password="+this.password);


        Log.d(TAG, "url registro de usuario");
        return urlRegister.toString();
    }

}
