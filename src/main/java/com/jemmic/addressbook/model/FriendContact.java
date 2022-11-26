package com.jemmic.addressbook.model;

import lombok.*;

import java.util.UUID;

/**
 * POJO class to hold information for Friends Contact
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 * @see com.jemmic.addressbook.model.Contact
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FriendContact extends Contact{

    private static final long serialVersionUID = 12343567L;
    private int numFriendshipYear;

    @Builder
    public FriendContact(UUID contactId, String name,
                         String surName, String telephoneNumber,
                         String email, int age, String hairColor, int numFriendshipYear) {
        super(contactId, name, surName, telephoneNumber, email, age, hairColor);
        this.numFriendshipYear = numFriendshipYear;
    }
}
