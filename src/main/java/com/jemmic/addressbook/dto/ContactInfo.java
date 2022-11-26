package com.jemmic.addressbook.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * DTO class to hold information for contact all category
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 * */
@Getter
@Setter
@NoArgsConstructor
public class ContactInfo {

    private String name;

    private String surName;

    private String telephoneNumber;

    private String email;

    private int age;

    private String hairColor;

    private String familyDescription;

    private int numFriendshipYear;
}
