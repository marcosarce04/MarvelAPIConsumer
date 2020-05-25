package com.example.Controller;

import com.example.Entities.CharacterHero;
import com.example.Exceptions.FavoriteListEmptyException;
import com.example.Exceptions.InvalidQuantityException;
import com.example.Exceptions.NoCharacterFoundException;
import com.example.Service.CharacterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
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

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Devuelve los primeros 20 resultados de una consulta a la API de Marvel " +
            "sin ningun tipo de parametro")
    public Iterable<CharacterHero> getAllCharacters() throws IOException {
        return characterService.getAllCharacters();
    }

    @GetMapping(value = "/cant/{cant}", produces = "application/json")
    @ApiOperation(value = "Devuelve la cantidad de resultados de acuerdo al parametro {cant}")
    public Iterable<CharacterHero> getAllCharacters(@PathVariable("cant") String cant) throws IOException,
            InvalidQuantityException {
        return characterService.getAllCharacters(cant);
    }

    @GetMapping(value = "/{name}", produces = "application/json")
    @ApiOperation(value = "Devuelve la informacion de los heroes que comienzan con las letras en {name}")
    public Iterable<CharacterHero> getHeros(@PathVariable("name") String name) throws IOException,
            NoCharacterFoundException {
        return characterService.getCharacters(name);
    }

    @GetMapping(value = "/favs", produces = "application/json")
    @ApiOperation(value = "Devuelve la lista de heroes favoritos")
    public Iterable<CharacterHero> getFavorites() throws FavoriteListEmptyException {
        return characterService.getFavorites();
    }

    @PutMapping(value = "/favs/{name}")
    @ApiOperation(value = "Agrega un heroe a la lista de favoritos")
    public void insertFav(@PathVariable String name) throws MalformedURLException, JsonProcessingException,
            NoCharacterFoundException {
        this.characterService.insertFav(name);
    }

    @DeleteMapping(value = "/favs/{name}")
    @ApiOperation(value = "Elimina un heroe de la lista de favoritos")
    @Transactional
    public void deleteFav(@PathVariable("name") String name) throws MalformedURLException,
            JsonProcessingException, NoCharacterFoundException {
        this.characterService.removeFav(name);
    }
}
