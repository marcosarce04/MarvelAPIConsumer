package com.example.Service;

import com.example.Dao.HeroesRepository;
import com.example.Entities.CharacterHero;
import com.example.Exceptions.FavoriteListEmptyException;
import com.example.Exceptions.InvalidQuantityException;
import com.example.Exceptions.NoCharacterFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

import static com.example.Utilities.StringUtil.cleanString;
import static com.example.Utilities.StringUtil.stringIsNotNullOrEmpty;
import static com.example.Utilities.UrlFormatter.getAuthParameters;
import static com.example.Utilities.UrlFormatter.getImageUrl;

@Service
public class CharacterService {

    private final HeroesRepository repository;

    private static final String CHARACTERS_URL = "https://gateway.marvel.com/v1/public/characters?";
    private static final Logger LOGGER = Logger.getLogger("com.example.Service.CharacterService");
    private static final String NAME_STARTS_WITH = "&nameStartsWith=";
    private static final String LIMIT = "&limit=";

    public CharacterService(HeroesRepository repository) {
        this.repository = repository;
    }

    public Iterable<CharacterHero> getAllCharacters(String cant) throws MalformedURLException, JsonProcessingException,
            InvalidQuantityException {
        StringBuffer url = new StringBuffer();
        if (stringIsNotNullOrEmpty(cant)) {
            url.append(CHARACTERS_URL).append(getAuthParameters()).append(LIMIT).append(cant);
        } else {
            url.append(CHARACTERS_URL).append(getAuthParameters());
        }
        ArrayList<CharacterHero> aL;
        try {
            aL = getListOfCharacters(url.toString());
        } catch(Exception e) {
            throw new InvalidQuantityException();
        }
        return aL;
    }

    public Iterable<CharacterHero> getAllCharacters() throws IOException {
        String url = CHARACTERS_URL + getAuthParameters();
        return getListOfCharacters(url);
    }

    public CharacterHero getCharacter(String characterName) throws JsonProcessingException, MalformedURLException,
            NoCharacterFoundException {
        String url = CHARACTERS_URL + getAuthParameters() + NAME_STARTS_WITH + characterName;
        ArrayList<CharacterHero> aL = getListOfCharacters(url);
        if (aL.size() == 0) {
            throw new NoCharacterFoundException();
        } else {
            return aL.get(0);
        }
    }

    public Iterable<CharacterHero> getCharacters(String characterName) throws JsonProcessingException, MalformedURLException,
            NoCharacterFoundException {
        String url = CHARACTERS_URL + getAuthParameters() + NAME_STARTS_WITH + characterName;
        ArrayList<CharacterHero> aL = getListOfCharacters(url);
        if (aL.size() == 0) {
            throw new NoCharacterFoundException();
        } else {
            return aL;
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

    public void insertFav(String name) throws MalformedURLException, JsonProcessingException,
            NoCharacterFoundException {
        repository.save(getCharacter(name));
    }

    public void removeFav(String name) throws MalformedURLException, JsonProcessingException,
            NoCharacterFoundException {
        repository.delete(getCharacter(name));
    }

    private ArrayList<CharacterHero> getListOfCharacters(String url) throws JsonProcessingException,
            MalformedURLException {
        ArrayList<CharacterHero> characters = new ArrayList<>();
        StringBuffer imageUrl = new StringBuffer();
        for (JsonNode jN: getResults(url)) {
            String name = cleanString(jN.findValue("name").toString());
            String desc = cleanString(jN.findValue("description").toString());
            characters.add(new CharacterHero(jN.findValue("id").asInt(), name , desc, getImageUrl(jN,imageUrl)));
            imageUrl.setLength(0);
        }
        return characters;
    }

    private JsonNode getResults(String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(responseEntity.getBody()).path("data").path("results");

    }

    private Image getImage( StringBuffer sB) throws MalformedURLException {
        URL url = new URL(sB.toString());
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
