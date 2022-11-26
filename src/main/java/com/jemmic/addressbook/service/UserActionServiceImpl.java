package com.jemmic.addressbook.service;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.exception.InvalidUserInputException;
import com.jemmic.addressbook.model.*;
import com.jemmic.addressbook.repository.ContactInfoRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation  class for UserActionService
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 */
@Service
@Setter
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    ContactInfoRepository ContactInfoRepository;
    @Autowired
    UserActionServiceHelper userActionServiceHelper;

    private static final String ROW_FORMAT = "|%1$-5s%2$-20s%3$-20s%4$-20s%5$-20s%6$-10s%7$-20s%8$-15s%9$-18s%10$-15s|\n";

    /**
     * Method to add contact
     * @param addContact
     */
    @Override
    public void addContact(Contact addContact) {
        List<Contact> addContactList = new ArrayList<>();
        addContactList.add(addContact);
        ContactInfoRepository.saveContactToFile(addContactList);
    }

    /**
     * Method to display all contacts
     */
    @Override
    public void displayAllContact(){
        List<Contact> allContact = ContactInfoRepository.getAllContact();
        Collections.sort(allContact, (final Contact c1,final Contact c2) ->
                c1.getSurName().compareToIgnoreCase(c2.getSurName()));

        printHeader();
        displayContact(allContact,false);

    }
    /**
     * Mrthod to display single contact to console
     * @param contacts
     * @param printHeader
     */
    @Override
    public void displayContact(List<Contact> contacts, boolean printHeader) {
        if(printHeader) {
            printHeader();
        }
        int srNo = 0;
        for(Contact contact : contacts) {
            srNo++;
            String contactCat = null;
            String familyDesc = "";
            int friendshipYear = 0;

            if (contact instanceof FamilyContact) {
                contactCat = "FAMILY";
                familyDesc = ((FamilyContact) contact).getFamilyDescription().relation;
            } else if (contact instanceof FriendContact) {
                contactCat = "FRIEND";
                friendshipYear = ((FriendContact) contact).getNumFriendshipYear();
            } else {
                contactCat = "ACQUAINTANCE";
            }

            System.out.format(ROW_FORMAT, srNo, contact.getSurName(), contact.getName(),
                    contact.getEmail(), contact.getTelephoneNumber(), contact.getAge(),
                    contact.getHairColor(), familyDesc, friendshipYear, contactCat);
        }
    }

    /**
     * Method to print header
     */
    private void printHeader(){
        System.out.format(ROW_FORMAT, "S.No", "Sur Name", "Name","Email", "Telephone Number", "Age","Hair Color", "Family Desc", "Friendship Year","Contact Type");
        System.out.format(ROW_FORMAT, "----", "--------", "----","------", "------------","-----","--------", "--------", "-----------","----------");

    }

    @Override
    public void removeContact(ContactInfo removeContact, Scanner in) {
        Contact contactRemove = null;
        try{
            List<Contact> contactListToRemove =
                    ContactInfoRepository.findContactByFirstAndSurName(removeContact);
            if(contactListToRemove.size()>1){
                System.out.println("Found multiple entries , Please select contact to remove (1-"+contactListToRemove.size()+")");
                displayContact(contactListToRemove,true);
                int choice = in.nextInt();
                if(choice <= contactListToRemove.size()){
                    contactRemove = contactListToRemove.get(choice - 1);
                }else{
                    throw new InvalidUserInputException("Please enter valid input (1-"+contactListToRemove.size()+")");
                }
            }else {
                contactRemove = contactListToRemove.get(0);
            }
            System.out.println("Selected contact to remove:");
            System.out.println("Are you sure you want to remove below contact(y/n)?");
            displayContact(Arrays.asList(contactRemove),true);
            String choice = in.next();
            if("Y".equalsIgnoreCase(choice)){
                ContactInfoRepository.deleteContact(contactRemove);
                System.out.println("Contact remove successfully");
            }

        }catch(InvalidUserInputException iue){
            System.err.println(iue.getMessage()+"Please enter correct input");
        }
    }

    @Override
    public void updateContact(ContactInfo updateContactInfo, Scanner in) {
        Contact contactToUpdate = null;
        try {
            List<Contact> contactListToUpdate =
                    ContactInfoRepository.findContactByFirstAndSurName(updateContactInfo);
            if(contactListToUpdate.size() > 1){
                System.out.println("Multipe Contact found with provided Sur Name and Name ," +
                        "Please select contact to update (1-"+contactListToUpdate.size()+")");
                displayContact(contactListToUpdate,true);
                int choice = in.nextInt();
                if(choice <= contactListToUpdate.size()){
                    contactToUpdate = contactListToUpdate.get(choice - 1);
                }else{
                    throw new InvalidUserInputException("Please enter valid input (1-"+contactListToUpdate.size()+")");
                }
            }else{
                contactToUpdate = contactListToUpdate.get(0);
            }
            System.out.println("Selected contact to Update:");
            System.out.println("Are you sure you want to update below contact(y/n)?");
            displayContact(Arrays.asList(contactToUpdate),true);
            String choice = in.next();
            if("Y".equalsIgnoreCase(choice)){
                System.out.println("Please enter updated contact info :");
                Contact updatedContact = userActionServiceHelper.updatedContactInfo(contactToUpdate, in);
                ContactInfoRepository.updateContact(updatedContact);
                System.out.println("Contact updated  successfully");
                displayContact(Arrays.asList(updatedContact), true);
            }

        }catch(InvalidUserInputException iue){
            System.err.println(iue.getMessage()+"Please enter correct input");
        }
    }

}
