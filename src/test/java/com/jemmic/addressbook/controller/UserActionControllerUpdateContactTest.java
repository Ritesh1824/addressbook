package com.jemmic.addressbook.controller;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.model.AcquaintanceContact;
import com.jemmic.addressbook.model.Contact;
import com.jemmic.addressbook.model.FriendContact;
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

public class UserActionControllerUpdateContactTest {


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
    public void testUpdateFamilyContact() throws Exception {
        String userInputAddContact = String.
                format("1%s1%sRitesh6%sKumar6%s77330022%sritesh@gmail.com%s30%sBlack%s4%sN",
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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInputAddContact.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh6");
        contactInfo.setSurName("kumar6");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar6",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());

        String userInputUpdateContact = String.
                format("4%skumar6%sritesh6%sY%s1%schoudhary%sy%s6%sbrown%sN%sN",
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
        ByteArrayInputStream byteArrayInputStreamUpdate = new ByteArrayInputStream(userInputUpdateContact.getBytes());
        System.setIn(byteArrayInputStreamUpdate);
        userActionController.run(new String[]{});

        ContactInfo contactInfoUpdated = new ContactInfo();
        contactInfoUpdated.setName("Ritesh6");
        contactInfoUpdated.setSurName("choudhary");
        List<Contact> contactListUpdated = contactInfoRepository.findContactByFirstAndSurName(contactInfoUpdated);
        assertEquals("choudhary",contactListUpdated.get(0).getSurName());
        assertEquals("brown",contactListUpdated.get(0).getHairColor());
    }

    @Test
    public void testUpdateFriendContact() throws Exception {
        String userInputAddContact = String.
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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInputAddContact.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh3");
        contactInfo.setSurName("kumar1");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar1",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());
        assertEquals(true ,contactList.get(0) instanceof FriendContact);

        String userInputUpdateContact = String.
                format("4%skumar1%sritesh3%sY%s1%schoudhary%sy%s7%s50%sN%sN",
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
        ByteArrayInputStream byteArrayInputStreamUpdate = new ByteArrayInputStream(userInputUpdateContact.getBytes());
        System.setIn(byteArrayInputStreamUpdate);
        userActionController.run(new String[]{});

        ContactInfo contactInfoUpdated = new ContactInfo();
        contactInfoUpdated.setName("Ritesh3");
        contactInfoUpdated.setSurName("choudhary");
        List<Contact> contactListUpdated = contactInfoRepository.findContactByFirstAndSurName(contactInfoUpdated);
        assertEquals("choudhary",contactListUpdated.get(0).getSurName());
        assertEquals(true,(contactListUpdated.get(0) instanceof FriendContact
                && ((FriendContact) contactListUpdated.get(0)).getNumFriendshipYear() == 50));
    }

    @Test
    public void testUpdateAcquaintanceContact() throws Exception {
        String userInputAddContact = String.
                format("1%s3%sRitesh4%sKumar4%s77330022%sritesh@gmail.com%s30%sBlack%sN",
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
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(userInputAddContact.getBytes());
        System.setIn(byteArrayInputStream);
        userActionController.run(new String[]{});

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("Ritesh4");
        contactInfo.setSurName("kumar4");
        List<Contact> contactList = contactInfoRepository.findContactByFirstAndSurName(contactInfo);
        assertEquals("Kumar4",contactList.get(0).getSurName());
        assertEquals("Black",contactList.get(0).getHairColor());
        assertEquals(true ,contactList.get(0) instanceof AcquaintanceContact);

        String userInputUpdateContact = String.
                format("4%skumar4%sritesh4%sY%s1%schoudhary%sy%s3%s7730065222%sN%sN",
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
        ByteArrayInputStream byteArrayInputStreamUpdate = new ByteArrayInputStream(userInputUpdateContact.getBytes());
        System.setIn(byteArrayInputStreamUpdate);
        userActionController.run(new String[]{});

        ContactInfo contactInfoUpdated = new ContactInfo();
        contactInfoUpdated.setName("Ritesh4");
        contactInfoUpdated.setSurName("choudhary");
        List<Contact> contactListUpdated = contactInfoRepository.findContactByFirstAndSurName(contactInfoUpdated);
        assertEquals("choudhary",contactListUpdated.get(0).getSurName());
        assertEquals("7730065222", contactListUpdated.get(0).getTelephoneNumber() );
    }
}
