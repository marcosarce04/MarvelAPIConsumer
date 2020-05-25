package com.example.Controller;

import com.example.Entities.CharacterHero;
import com.example.Exceptions.FavoriteListEmptyException;
import com.example.Exceptions.InvalidQuantityException;
import com.example.Exceptions.NoCharacterFoundException;
import com.example.Service.CharacterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/marvel")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping
    public Iterable<CharacterHero> getAllCharacters() throws IOException {
        return characterService.getAllCharacters();
    }

    @GetMapping(value = "/cant/{cant}")
    public Iterable<CharacterHero> getAllCharacters(@PathVariable("cant") String cant) throws IOException,
            InvalidQuantityException {
        return characterService.getAllCharacters(cant);
    }

    @GetMapping(value = "/{name}")
    public Iterable<CharacterHero> getHeros(@PathVariable("name") String name) throws IOException,
            NoCharacterFoundException {
        return characterService.getCharacters(name);
    }

    @GetMapping(value = "/favs")
    public Iterable<CharacterHero> getFavorites() throws FavoriteListEmptyException {
        return characterService.getFavorites();
    }

    @PutMapping(value = "/favs/{name}")
    public void insertFav(@PathVariable String name) throws MalformedURLException, JsonProcessingException,
            NoCharacterFoundException {
        this.characterService.insertFav(name);
    }

    @DeleteMapping(value = "/favs/{name}")
    @Transactional
    public void deleteFav(@PathVariable("name") String name) throws MalformedURLException,
            JsonProcessingException, NoCharacterFoundException {
        this.characterService.removeFav(name);
    }
}
