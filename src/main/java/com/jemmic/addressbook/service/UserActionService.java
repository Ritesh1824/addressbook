package com.jemmic.addressbook.service;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.Contact;

import java.util.List;
import java.util.Scanner;

/**
 * Interface to hold method info of UserActionService
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 */
public interface UserActionService {

    void addContact(Contact addContact);

    void displayAllContact();

    void displayContact(List<Contact> contacts, boolean printHeader);

    void removeContact(ContactInfo removeContact, Scanner in);

    void updateContact(ContactInfo updateContactInfo, Scanner in);
}
