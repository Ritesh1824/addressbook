package com.jemmic.addressbook.controller;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.AcquaintanceContact;
import com.jemmic.addressbook.model.Contact;
import com.jemmic.addressbook.model.FriendContact;
import com.jemmic.addressbook.repository.ContactInfoRepository;
import com.jemmic.addressbook.service.UserActionService;
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

public class UserActionControllerAddContactTest {

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
    public void testAddFamilyContact_AllInfoPresent() throws Exception {
        String userInput = String.
                format("1%s1%sRitesh3%sKumar1%s77330022%sritesh@gmail.com%s30%sBlack%s3%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh3");
        contactInfo.setSurName("kumar1");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar1",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());

    }

    @Test
    public void testAddFamilyContact_OnlyMandatoryInfo() throws Exception {
        String userInput = String.
                format("1%s1%sRitesh4%sKumar4%s77330022%sritesh@gmail.com%s\n%s\n%s3%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh4");
        contactInfo.setSurName("kumar4");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar4",contactList.get(0).getSurName());
        assertEquals("",contactList.get(0).getHairColor());

    }

    @Test
    public void testAddFriendContact_AllInfoPresent() throws Exception {
        String userInput = String.
                format("1%s2%sRitesh3%sKumar1%s77330022%sritesh@gmail.com%s30%sBlack%s14%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh3");
        contactInfo.setSurName("kumar1");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar1",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());
        assertEquals(true,contactList.get(0) instanceof FriendContact);

    }

    @Test
    public void testAddFriendContact_OnlyMandatoryInfo() throws Exception {
        String userInput = String.
                format("1%s2%sRitesh4%sKumar4%s77330022%sritesh@gmail.com%s\n%s\n%s14%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh4");
        contactInfo.setSurName("kumar4");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar4",contactList.get(0).getSurName());
        assertEquals("",contactList.get(0).getHairColor());

    }


    @Test
    public void testAddAcquaintanceContact_AllInfoPresent() throws Exception {
        String userInput = String.
                format("1%s3%sRitesh3%sKumar1%s77330022%sritesh@gmail.com%s30%sBlack%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh3");
        contactInfo.setSurName("kumar1");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar1",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());
        assertEquals(true,contactList.get(0) instanceof AcquaintanceContact);

    }

    @Test
    public void testAddAcquaintanceContact_OnlyMandatoryInfo() throws Exception {
        String userInput = String.
                format("1%s3%sRitesh4%sKumar4%s77330022%sritesh@gmail.com%s\n%s\n%sN",
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator(),
                        System.lineSeparator());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh4");
        contactInfo.setSurName("kumar4");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar4",contactList.get(0).getSurName());
        assertEquals("",contactList.get(0).getHairColor());

    }

}