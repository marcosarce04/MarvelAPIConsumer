package com.example.Dao;

import com.example.Entities.CharacterHero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroesRepository extends CrudRepository<CharacterHero, Integer> {


}
