package com.test1.pluto1;
import java.io.Serializable;
public class User implements Serializable {
 private static final long serialVersionUID = 1L;

    private String id,name,language,city,description,event_type;

    public User() {
        super();
    }

    public User(String id, String name,String language,String city,String description,String event_type) {
        super();
        this.id = id;
        this.name = name;
        this.language=language;
        this.city=city;
        this.description=description;
        this.event_type=event_type;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.name = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

}