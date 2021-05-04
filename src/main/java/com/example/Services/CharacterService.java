package com.example.Services;

import static com.example.Utilities.AuthUtil.getAuthParameters;
import static com.example.Utilities.StringUtil.cleanString;
import static com.example.Utilities.StringUtil.stringIsNotNullOrEmpty;

import com.example.Entities.CharacterHero;
import com.example.Exceptions.FavoriteListEmptyException;
import com.example.Exceptions.InvalidQuantityException;
import com.example.Exceptions.NoCharacterFoundException;
import com.example.Repositories.HeroesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CharacterService {

  private final HeroesRepository repository;

  private static final String CHARACTERS_URL = "https://gateway.marvel.com/v1/public/characters?";
  private static final String NAME_STARTS_WITH = "&nameStartsWith=";
  private static final String LIMIT = "&limit=";
  private final static String IMAGE_SIZE_PORTRAIT_INCREDIBLE = "portrait_incredible";
  private final static String IMAGE_FORMAT_JPG = ".jpg";

  public CharacterService(HeroesRepository repository) {
    this.repository = repository;
  }

  public Iterable<CharacterHero> getAllCharacters(String quantity) throws InvalidQuantityException {
    try {
      return getListOfCharacters(setUrlWithQuantity(quantity).toString());
    } catch (Exception e) {
      throw new InvalidQuantityException();
    }
  }

  public Iterable<CharacterHero> getAllCharacters() throws IOException {
    return getListOfCharacters(setUrl().toString());
  }

  public CharacterHero getCharacter(String characterName) throws NoCharacterFoundException {
    try {
      return getListOfCharacters(setUrlWithNameStartWith(characterName)).get(0);
    } catch (Exception e) {
      throw new NoCharacterFoundException();
    }
  }

  public Iterable<CharacterHero> getCharacters(String characterName)
      throws NoCharacterFoundException {
    try {
      return getListOfCharacters(setUrlWithNameStartWith(characterName));
    } catch (Exception e) {
      throw new NoCharacterFoundException();
    }
  }

  public Iterable<CharacterHero> getFavorites() throws FavoriteListEmptyException {
    Iterable<CharacterHero> it = repository.findAll();
    if (it.iterator().hasNext()) {
      return it;
    } else {
      throw new FavoriteListEmptyException();
    }
  }

  public void insertFav(String name) throws NoCharacterFoundException {
    repository.save(getCharacter(name));
  }

  public void removeFav(String name) throws NoCharacterFoundException {
    repository.delete(getCharacter(name));
  }

  private ArrayList<CharacterHero> getListOfCharacters(String url) throws JsonProcessingException {
    ArrayList<CharacterHero> characters = new ArrayList<>();
    StringBuffer imageUrl = new StringBuffer();
    for (JsonNode node : getResults(url)) {
      characters.add(
          CharacterHero.builder()
              .id(Integer.parseInt(getValue(node, "id")))
              .name(getValue(node, "name"))
              .description(getValue(node, "description"))
              .pic(getImageUrl(node, imageUrl))
              .build());
      imageUrl.setLength(0);
    }
    return characters;
  }

  private JsonNode getResults(String url) throws JsonProcessingException {
    ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
    return new ObjectMapper().readTree(responseEntity.getBody()).path("data").path("results");
  }

  private Image getImage(StringBuffer sB) throws MalformedURLException {
    URL url = new URL(sB.toString());
    Image image = null;
    try {
      image = ImageIO.read(url);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return image;
  }

  private StringBuffer setUrlWithQuantity(String quantity) {
    return stringIsNotNullOrEmpty(quantity) ?
        new StringBuffer(CHARACTERS_URL).append(getAuthParameters()).append(LIMIT).append(quantity)
        : setUrl();
  }

  private StringBuffer setUrl() {
    return new StringBuffer(CHARACTERS_URL).append(getAuthParameters());
  }

  private String setUrlWithNameStartWith(String characterName) {
    return CHARACTERS_URL + getAuthParameters() + NAME_STARTS_WITH + characterName;
  }

  private String getValue(JsonNode node, String value) {
    return cleanString(node.findValue(value).toString());
  }

  private String getImageUrl(JsonNode node, StringBuffer imageUrl) {
    return imageUrl.append(cleanString(node.findValue("thumbnail").findValue("path").toString()))
        .append("/").append(IMAGE_SIZE_PORTRAIT_INCREDIBLE).append(IMAGE_FORMAT_JPG)
        .toString();
  }
}
