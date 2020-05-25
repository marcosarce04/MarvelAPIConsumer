package com.example.Controller;

import com.example.Entities.CharacterHero;
import com.example.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
