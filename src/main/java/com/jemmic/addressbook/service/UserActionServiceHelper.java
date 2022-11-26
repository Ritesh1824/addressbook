package com.jemmic.addressbook.service;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.*;
import com.jemmic.addressbook.utility.InputValidation;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/**
 * Helper class for UserActionServiceImpl
 * @author Ritesh Kumar (ritesh.kumar.j18@gmail.com)
 *
 */
@Component
@Setter
public class UserActionServiceHelper {

    @Autowired
    InputValidation inputValidation;

    public Contact updatedContactInfo(Contact oldContact, Scanner in) {
        Contact newContact = null;
        ContactInfo contactInfo = new ContactInfo();
        boolean isFamilyContact = false;
        int choice;
        String cont;
        do {
            System.out.println("Please select field you want to update:");
            System.out.println("\n 1. Sur Name \n 2. Name \n 3. Telephone Number \n 4. Email Address \n 5. Age" +
                    "\n 6. Hair Color");
            if(oldContact instanceof FamilyContact){
                System.out.println("7. Family Description");
                isFamilyContact = true;
            }else if(oldContact instanceof FriendContact){
                System.out.println(" 7. Number of Years ");
            }
            choice = in.nextInt();
            setContactInfo(choice, in, contactInfo, isFamilyContact);
            System.out.println("Do you want to Update more fields(Y/N) ?");
            cont = in.next();
        }while(cont.equalsIgnoreCase("Y"));
        if(isFamilyContact){
            newContact = FamilyContact.builder()
                    .contactId(oldContact.getContactId())
                    .name(StringUtils.isBlank(contactInfo.getName()) ? oldContact.getName() :contactInfo.getName())
                    .surName(StringUtils.isBlank(contactInfo.getSurName()) ? oldContact.getSurName() :contactInfo.getSurName())
                    .email(StringUtils.isBlank(contactInfo.getEmail()) ? oldContact.getEmail() :contactInfo.getEmail())
                    .familyDescription(StringUtils.isBlank(contactInfo.getFamilyDescription())
                            ? ((FamilyContact) oldContact).getFamilyDescription()
                            : FamilyDescription.getFamilyDescByName(contactInfo.getFamilyDescription()))
                    .telephoneNumber(StringUtils.isBlank(contactInfo.getTelephoneNumber())
                            ? oldContact.getTelephoneNumber() : contactInfo.getTelephoneNumber() )
                    .age(contactInfo.getAge() != 0 ? contactInfo.getAge() : oldContact.getAge())
                    .hairColor(StringUtils.isBlank(contactInfo.getHairColor()) ? oldContact.getHairColor() : contactInfo.getHairColor())
                    .build();
        }else if(oldContact instanceof FriendContact){
            newContact = FriendContact.builder()
                    .contactId(oldContact.getContactId())
                    .name(StringUtils.isBlank(contactInfo.getName()) ? oldContact.getName() :contactInfo.getName())
                    .surName(StringUtils.isBlank(contactInfo.getSurName()) ? oldContact.getSurName() :contactInfo.getSurName())
                    .email(StringUtils.isBlank(contactInfo.getEmail()) ? oldContact.getEmail() :contactInfo.getEmail())
                    .numFriendshipYear(contactInfo.getNumFriendshipYear() !=0
                            ? contactInfo.getNumFriendshipYear()
                            : ((FriendContact) oldContact).getNumFriendshipYear())
                    .telephoneNumber(StringUtils.isBlank(contactInfo.getTelephoneNumber())
                            ? oldContact.getTelephoneNumber() : contactInfo.getTelephoneNumber() )
                    .age(contactInfo.getAge() != 0 ? contactInfo.getAge() : oldContact.getAge())
                    .hairColor(StringUtils.isBlank(contactInfo.getHairColor()) ? oldContact.getHairColor() : contactInfo.getHairColor())
                    .build();
        }else{
            newContact = AcquaintanceContact.builder()
                    .contactId(oldContact.getContactId())
                    .name(StringUtils.isBlank(contactInfo.getName()) ? oldContact.getName() :contactInfo.getName())
                    .surName(StringUtils.isBlank(contactInfo.getSurName()) ? oldContact.getSurName() :contactInfo.getSurName())
                    .email(StringUtils.isBlank(contactInfo.getEmail()) ? oldContact.getEmail() :contactInfo.getEmail())
                    .telephoneNumber(StringUtils.isBlank(contactInfo.getTelephoneNumber())
                            ? oldContact.getTelephoneNumber() : contactInfo.getTelephoneNumber() )
                    .age(contactInfo.getAge() != 0 ? contactInfo.getAge() : oldContact.getAge())
                    .hairColor(StringUtils.isBlank(contactInfo.getHairColor()) ? oldContact.getHairColor() : contactInfo.getHairColor())
                    .build();
        }

        return newContact;
    }

    private void setContactInfo(int choice, Scanner in, ContactInfo contactInfo, boolean isFamilyContact) {
        switch (choice){
            case 1:
                System.out.println("Please enter updated Sur Name: ");
                contactInfo.setSurName(in.next());
                System.out.println("Sur Name set to :"+ contactInfo.getSurName());
                break;
            case 2:
                System.out.println("Please enter updated Name: ");
                contactInfo.setName(in.next());
                System.out.println("Name set to :"+ contactInfo.getName());
                break;
            case 3:
                System.out.println("Please enter updated Telephone Number: ");
                contactInfo.setTelephoneNumber(in.next());
                System.out.println("Telephone Number set to :"+ contactInfo.getTelephoneNumber());
                break;
            case 4:
                System.out.println("Please enter updated Email Address: ");
                contactInfo.setEmail(in.next());
                System.out.println("Email Address set to :"+ contactInfo.getEmail());
                break;
            case 5:
                System.out.println("Please enter updated Age: ");
                contactInfo.setAge(in.nextInt());
                System.out.println("Age set to :"+ contactInfo.getAge());
                break;
            case 6:
                System.out.println("Please enter updated Hair Color: ");
                contactInfo.setHairColor(in.next());
                System.out.println("Hair Color set to :"+ contactInfo.getHairColor());
                break;
            case 7:
                if(isFamilyContact){
                    System.out.println("Please select updated Relationship Description 1-11\n");
                    System.out.println(FamilyDescription.getAllRelation());
                    int relationChoice = inputValidation.validateIntegerInput(in,1,11,
                            "Please select valid family description (1-11)");
                    contactInfo.setFamilyDescription(FamilyDescription.getFamilyDesc(relationChoice).relation);
                    System.out.println("Family Description  set to :"+ contactInfo.getFamilyDescription());
                }else{
                    System.out.println("Please enter Updated Number of Friendship Year (1-100)");
                    contactInfo.setNumFriendshipYear(inputValidation.validateIntegerInput(in,1,100,
                            "Please enter valid input (1-100)"));
                }
                break;
            default:
                System.out.println("Please enter valid input ");
        }
    }
}
