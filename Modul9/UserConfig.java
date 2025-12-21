package Modul9;

import java.io.Serializable;

public class UserConfig implements Serializable {
    private String username;
    private int fontsize;

    // Konstruktor default
    public UserConfig() {
        this.username = "Default User";
        this.fontsize = 14;
    }

    // Konstruktor dengan parameter
    public UserConfig(String username, int fontsize) {
        this.username = username;
        this.fontsize = fontsize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    @Override
    public String toString() {
        return "UserConfig{username='" + username + "', fontsize=" + fontsize + "}";
    }
}