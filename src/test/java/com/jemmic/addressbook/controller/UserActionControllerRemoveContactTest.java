package com.jemmic.addressbook.controller;

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

public class UserActionControllerRemoveContactTest {


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

    @Test
    public void testRemoveContact() throws Exception {
        List<Contact> contactList = contactInfoRepository.getAllContact();
        int beforeSize = contactList.size();
        String userInput = String.
                format("3%skumar%sritesh%s1%sY%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});
        int afterSize  = contactInfoRepository.getAllContact().size();
        assertEquals(beforeSize -1,afterSize);

    }
}
