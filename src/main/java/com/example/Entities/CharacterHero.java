package com.example.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterHero {

  @Id
  private int id;
  private String name;
  @Column(length = 5000)
  private String description;
  private String pic;

  @Override
  public String toString() {
    return "Character id= " + id + "Character Name= " + name +
        ", Character description= " + description;
  }
}
