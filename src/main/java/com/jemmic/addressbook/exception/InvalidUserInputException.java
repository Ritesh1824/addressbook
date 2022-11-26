package com.jemmic.addressbook.exception;

public class InvalidUserInputException extends RuntimeException{

    public InvalidUserInputException(String msg){
        super(msg);
    }
}
