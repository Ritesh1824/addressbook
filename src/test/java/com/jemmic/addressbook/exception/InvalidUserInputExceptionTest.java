package com.jemmic.addressbook.exception;

import com.jemmic.addressbook.controller.UserActionController;
import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.Contact;
import com.jemmic.addressbook.repository.ContactInfoRepository;
import com.jemmic.addressbook.service.UserActionServiceHelper;
import com.jemmic.addressbook.service.UserActionServiceImpl;
import com.jemmic.addressbook.utility.InputValidation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvalidUserInputExceptionTest {


    UserActionController userActionController;
    UserActionServiceImpl userActionService;
    InputValidation inputValidation;
    ContactInfoRepository contactInfoRepository;
    UserActionServiceHelper userActionServiceHelper;

    @Before
    public void setup(){
        userActionController = new UserActionController();
        userActionService = new UserActionServiceImpl();
        inputValidation = new InputValidation();
        contactInfoRepository = new ContactInfoRepository();
        userActionServiceHelper = new UserActionServiceHelper();
        contactInfoRepository.setContactRepositoryPath("src/test/resources/AddressBook.txt");

        userActionController.setUserActionService(userActionService);
        userActionController.setInputValidation(inputValidation);

        userActionService.setUserActionServiceHelper(userActionServiceHelper);
        userActionService.setContactInfoRepository(contactInfoRepository);
        userActionServiceHelper.setInputValidation(inputValidation);

        try{
            Files.copy(
                    Paths.get("src/test/resources/AutomatedTestCase/AddressBook.txt"),
                    Paths.get("src/test/resources/AddressBook.txt"),
                    StandardCopyOption.REPLACE_EXISTING );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void destroy(){
        try {
            Files.delete(Paths.get("src/test/resources/AddressBook.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = InvalidUserInputException.class)
    public void testInvalidInputException() throws Exception {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh4333");
        contactInfo.setSurName("kumar433");
        contactInfoRepository.findContactByFirstAndSurName(contactInfo);
    }
}
