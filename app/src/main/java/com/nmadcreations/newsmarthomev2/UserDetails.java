package com.nmadcreations.newsmarthomev2;

public class UserDetails {
    private String id,name,date;
    private Boolean isAdmin,isDisable;

    public UserDetails(String id, String name, String date, Boolean isAdmin,Boolean isDisable) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.isAdmin = isAdmin;
        this.isDisable=isDisable;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }
}
