package com.example.Service;

import com.example.Entities.CharacterHero;
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
import static com.example.Utilities.UrlFormatter.getAuthParameters;
import static com.example.Utilities.UrlFormatter.getImageUrl;

@Service
public class CharacterService {


    private final static Logger LOGGER = Logger.getLogger("com.example.Service.CharacterService");
    private final static String CHARACTERS_URL = "https://gateway.marvel.com/v1/public/characters?";
    private final static String NAME_STARTS_WITH = "&nameStartsWith=";

    public Iterable<CharacterHero> getAllCharacters() throws IOException {
        String url = CHARACTERS_URL + getAuthParameters();
        return getListOfCharacters(url);
    }

    public CharacterHero getCharacter(String characterName) throws JsonProcessingException, MalformedURLException {
        String url = CHARACTERS_URL + getAuthParameters() + NAME_STARTS_WITH + characterName;
        return getListOfCharacters(url).get(0);
    }

    private ArrayList<CharacterHero> getListOfCharacters(String url) throws JsonProcessingException, MalformedURLException {
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
        JsonNode results = mapper.readTree(responseEntity.getBody()).path("data").path("results");

        return results;
    }

    private Image getImage( StringBuffer sB) throws MalformedURLException {
        URL url = new URL(sB.toString());
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (image != null) ? image : null;
    }
}
