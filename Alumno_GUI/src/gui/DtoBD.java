/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;


public class DtoBD {
    
    private String UrlBD;

    private String User;
    
    private char[] Password;

    
    
    public DtoBD() {
    }

        
    public DtoBD(String UrlBd, String User, char[] Password) {
        this.UrlBD = UrlBd;
        this.User = User;
        this.Password = Password;
    }
   
    
    
    
    public String getUrlBD() {
        return UrlBD;
    }

    public void setUrlBD(String UrlBD) {
        this.UrlBD = UrlBD;
    }
        
    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public char[] getPassword() {
        return Password;
    }

    public void setPassword(char[] Password) {
        this.Password = Password;
    }
}
