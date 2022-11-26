package com.jemmic.addressbook.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Contact implements Serializable {
    private static final long serialVersionUID = 1234567L;

    private UUID contactId;

    private String name;

    private String surName;

    private String telephoneNumber;

    private String email;

    private int age;

    private String hairColor;

}
