package com.crud.crudmongoback.global.utils;

public class Operations {

    //se crea metodo estatico para nmo crear una instancia en globral exception
    public static String trimBrackets(String message){
        //para quitar los brackets del mensaje "[product name is mandatory, product price is mandatory]"
        return message.replaceAll("[\\[\\]]", "");
    }
}
