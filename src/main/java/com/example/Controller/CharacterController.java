package com.example.Controller;

import com.example.Entities.CharacterHero;
import com.example.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping
    public Iterable<CharacterHero> getAllCharacters() throws IOException {
        return characterService.getAllCharacters();
    }

    @GetMapping(value = "/{name}")
    public CharacterHero getHero(@PathVariable("name") String name) throws IOException {
        return characterService.getCharacter(name);
    }

    @GetMapping(value = "/favs")
    public Iterable<CharacterHero> getFavorites() {
        return characterService.getFavorites();
    }

    @PutMapping(value = "/favs",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertFav(@RequestBody CharacterHero hero) {
        this.characterService.insertFav(hero);
    }

    @DeleteMapping(value = "/{name}")
    public void deleteStudentById(@PathVariable("name") String name) {
        this.characterService.removeFavByName(name);
    }
}
