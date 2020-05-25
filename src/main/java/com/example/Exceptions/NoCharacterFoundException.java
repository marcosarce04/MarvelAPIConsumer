package com.example.Exceptions;

public class NoCharacterFoundException extends Exception {

    public NoCharacterFoundException() { super("Heroe inexistente, por favor" +
            " ingresar un nombre de superheroe valido"); }
}
