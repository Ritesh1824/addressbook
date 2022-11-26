package com.jemmic.addressbook.controller;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.*;
import com.jemmic.addressbook.service.UserActionService;
import com.jemmic.addressbook.utility.InputValidation;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

/**
 * Controller class , Program starts with run method
 */
@Component
@Setter
public class UserActionController implements CommandLineRunner {

    @Autowired
    UserActionService userActionService;
    @Autowired
    InputValidation inputValidation;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("-------------- Welcome To Address Book Management System -----------");
        System.out.println("--------------------------------------------------------------------");
        action();
    }

    public void action(){
        Scanner in = new Scanner(System.in);
        String userSelectionText = String.join("\n","Please enter your choice (1-5)",
                                                "1. Add Contact", "2. Display All Contact", "3. Remove Contact",
                                                "4. Update Contact", "5. Exit ");
        String cont;
        do{
            System.out.println(userSelectionText);
            int userInput = in.nextInt();
            switch(userInput){
                case 1:
                    Contact addContact = contactInfoToAdd( in);
                    userActionService.addContact(addContact);
                    System.out.println("Below Contact added successfully: ");
                    userActionService.displayContact(Arrays.asList(addContact),true);
                    break;
                case 2:
                    userActionService.displayAllContact();
                    break;
                case 3:
                    ContactInfo removeContact = contactInfoToRemove(in);
                    userActionService.removeContact(removeContact, in);
                    break;
                case 4:
                    ContactInfo updateContact = contactInfoToUpdate(in);
                    userActionService.updateContact(updateContact,in);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.err.println("Please enter valid input..(1-5)");
            }
            System.out.println("Do you want to continue(Y/N) ?");
            cont = in.next();
        }while(cont.equalsIgnoreCase("Y"));
    }



    /**
     * This method is to collect user input for adding contact
     * @param in
     * @return
     */
    Contact contactInfoToAdd(Scanner in){

        System.out.println(String.join("\n","Select contact category (1-3)",
                                        "1. Family", "2. Friends", "3. Acquaintance"));
        int catChoice = inputValidation.validateIntegerInput(in,1,3,
                                                    "Please enter valid category input (1-3)");
        ContactInfo contactInfo = new ContactInfo();
        Contact addContact = null;
        switch(catChoice){
            case 1:
                populateCommonContactInfo(contactInfo, in);
                System.out.println("Please select relationship description 1-11\n");
                System.out.println(FamilyDescription.getAllRelation());
                int choice = inputValidation.validateIntegerInput(in,1,11,
                        "Please select valid family description (1-11)");
                addContact = FamilyContact.builder()
                                .contactId(UUID.randomUUID())
                                .name(contactInfo.getName())
                                .surName(contactInfo.getSurName())
                                .email(contactInfo.getEmail())
                                .familyDescription(FamilyDescription.getFamilyDesc(choice))
                                .telephoneNumber(contactInfo.getTelephoneNumber())
                                .age(contactInfo.getAge())
                                .hairColor(contactInfo.getHairColor())
                                .build();
                break;
            case 2:
                populateCommonContactInfo(contactInfo, in);
                System.out.println("Please enter number of Friendship Year (1-100)");
                contactInfo.setNumFriendshipYear(inputValidation.validateIntegerInput(in,1,100,
                        "Please enter valid input (1-100)"));
                addContact = FriendContact.builder()
                                .contactId(UUID.randomUUID())
                                .name(contactInfo.getName())
                                .surName(contactInfo.getSurName())
                                .email(contactInfo.getEmail())
                                .numFriendshipYear(contactInfo.getNumFriendshipYear())
                                .telephoneNumber(contactInfo.getTelephoneNumber())
                                .age(contactInfo.getAge())
                                .hairColor(contactInfo.getHairColor())
                                .build();

                break;
            case 3:
                populateCommonContactInfo(contactInfo, in);
                addContact = AcquaintanceContact.builder()
                                .contactId(UUID.randomUUID())
                                .name(contactInfo.getName())
                                .surName(contactInfo.getSurName())
                                .email(contactInfo.getEmail())
                                .telephoneNumber(contactInfo.getTelephoneNumber())
                                .age(contactInfo.getAge())
                                .hairColor(contactInfo.getHairColor())
                                .build();
                break;
            default:
                System.out.println("Something went wrong Please restart the application and try again");

        }

        return addContact;
    }

    /**
     * Method to populate common contact details for all category
     * @param contactInfo
     * @param in
     */
    private void populateCommonContactInfo(ContactInfo contactInfo, Scanner in) {
        System.out.println("Please enter Name (Mandatory)");
        contactInfo.setName(inputValidation.validateMandatoryField(in, "Please enter name (Mandatory)"));

        System.out.println("Please enter Sur Name (Mandatory)");
        contactInfo.setSurName(inputValidation.validateMandatoryField(in, "Please enter Sur Name *(Mandatory)"));

        System.out.println("Please enter Telephone Number (Mandatory)");
        contactInfo.setTelephoneNumber(inputValidation.validateMandatoryField(in, "Please enter Telephone Number (Mandatory)"));

        System.out.println("Please enter Email Id (Mandatory)");
        contactInfo.setEmail(inputValidation.validateMandatoryField(in, "Please enter Email Id (Mandatory)"));
        in.skip("\r\n|\n");

        System.out.println("Please enter Age 1- 100 (Optional), Press Enter to skip");
        String ageInput = in.nextLine();
        if(StringUtils.isNotBlank(ageInput)){
            try {
                contactInfo.setAge(Integer.parseInt(ageInput));
            }catch( NumberFormatException nfe){
                System.err.println("Invalid age format, Skipping age information");
            }
        }

        System.out.println("Please enter hair Color (Optional), Press Enter to skip");
        String hairColor = in.nextLine();
        if(StringUtils.isNotBlank(hairColor)){
            contactInfo.setHairColor(hairColor);
        }else{
            contactInfo.setHairColor("");
        }

    }

    /**
     * Method to remove contact
     * @param in
     * @return
     */
    private ContactInfo contactInfoToRemove(Scanner in) {
        ContactInfo removeContact = new ContactInfo();
        System.out.println("Please enter Sur Name : ");
        removeContact.setSurName(inputValidation.validateMandatoryField(in, "Please enter Sur Name to remove"));

        System.out.println("Please enter First Name : ");
        removeContact.setName(inputValidation.validateMandatoryField(in, "Please enter First Name to remove"));

        return removeContact;
    }

    /**
     * Method to update contact
     * @param in
     * @return
     */
    private ContactInfo contactInfoToUpdate(Scanner in) {
        ContactInfo updateContact = new ContactInfo();
        System.out.println("Please enter Sur Name : ");
        updateContact.setSurName(inputValidation.validateMandatoryField(in, "Please enter Sur Name to search contact to  update"));

        System.out.println("Please enter First Name : ");
        updateContact.setName(inputValidation.validateMandatoryField(in, "Please enter First Name to search contact to update"));

        return updateContact;
    }


}
