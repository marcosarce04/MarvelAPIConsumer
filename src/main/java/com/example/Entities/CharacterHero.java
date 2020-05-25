package com.example.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

@Entity
public class CharacterHero {

    @Id
    private int id;
    private String name;
    @Column(length=5000)
    private String description;
    private URL pic;
    public CharacterHero() {
    }
    public CharacterHero(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CharacterHero(int id, String name, String description, URL pic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public URL getPic() {
        return pic;
    }

    public void setPic(URL pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Character id= " + id + "Character Name= " + name +
                ", Character description= " + description;
    }
}
