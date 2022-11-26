package com.jemmic.addressbook.model;

import lombok.*;

import java.util.UUID;

/**
 * POJO class to hold information for Acquaintance Contact
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 * @see com.jemmic.addressbook.model.Contact
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AcquaintanceContact extends Contact{
    private static final long serialVersionUID = 12348567L;

    @Builder
    public AcquaintanceContact(UUID contactId, String name,
                               String surName, String telephoneNumber,
                               String email, int age, String hairColor){
        super(contactId, name, surName, telephoneNumber, email, age, hairColor);
    }
}

