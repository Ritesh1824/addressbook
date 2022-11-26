package com.jemmic.addressbook.model;

import lombok.*;

import java.util.UUID;
/**
 * POJO class to hold information for Family Contact
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 * @see com.jemmic.addressbook.model.Contact
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FamilyContact extends Contact {

    private static final long serialVersionUID = 123423467L;
    private FamilyDescription familyDescription;

    @Builder
    public FamilyContact(UUID contactId, String name,
                               String surName, String telephoneNumber,
                               String email, int age, String hairColor, FamilyDescription familyDescription) {
        super(contactId, name, surName, telephoneNumber, email, age, hairColor);
        this.familyDescription = familyDescription;
    }
}
