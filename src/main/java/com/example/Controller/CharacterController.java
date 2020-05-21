package com.example.Controller;

import com.example.Entities.CharacterHero;
import com.example.Service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping
    public Iterable<CharacterHero> getAllHeroes() throws IOException {
        return characterService.getAllHeroes();
    }
}
