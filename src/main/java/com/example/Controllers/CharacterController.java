package com.example.Controllers;

import com.example.Entities.CharacterHero;
import com.example.Exceptions.FavoriteListEmptyException;
import com.example.Exceptions.InvalidQuantityException;
import com.example.Exceptions.NoCharacterFoundException;
import com.example.Services.CharacterService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.transaction.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marvel")
public class CharacterController {

  private final CharacterService characterService;

  public CharacterController(CharacterService characterService) {
    this.characterService = characterService;
  }

  @GetMapping(produces = "application/json")
  @ApiOperation(value = "Returns the first 20 results of a "
      + "query to the Marvel API without any type of parameter.")
  public Iterable<CharacterHero> getAllCharacters() throws IOException {
    return characterService.getAllCharacters();
  }

  @GetMapping(value = "/quantity/{quantity}", produces = "application/json")
  @ApiOperation(value = "Returns the number of results according to the {quantity} parameter.")
  public Iterable<CharacterHero> getAllCharacters(@PathVariable("quantity") String quantity)
      throws InvalidQuantityException {
    return characterService.getAllCharacters(quantity);
  }

  @GetMapping(value = "/{name}", produces = "application/json")
  @ApiOperation(value = "Returns the heroes information that begin with the letters in {name}")
  public Iterable<CharacterHero> getHeros(@PathVariable("name") String name) throws
      NoCharacterFoundException {
    return characterService.getCharacters(name);
  }

  @GetMapping(value = "/favs", produces = "application/json")
  @ApiOperation(value = "Returns the list of favorite heroes")
  public Iterable<CharacterHero> getFavorites() throws FavoriteListEmptyException {
    return characterService.getFavorites();
  }

  @PutMapping(value = "/favs/{name}")
  @ApiOperation(value = "Add a hero to the favorites list")
  public void insertFav(@PathVariable String name) throws NoCharacterFoundException {
    this.characterService.insertFav(name);
  }

  @DeleteMapping(value = "/favs/{name}")
  @ApiOperation(value = "Remove a hero from the favorites list")
  @Transactional
  public void deleteFav(@PathVariable("name") String name) throws NoCharacterFoundException {
    this.characterService.removeFav(name);
  }
}
