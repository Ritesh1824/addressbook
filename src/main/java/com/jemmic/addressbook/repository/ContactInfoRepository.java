package com.jemmic.addressbook.repository;

import com.jemmic.addressbook.dto.ContactInfo;
import com.jemmic.addressbook.exception.InvalidUserInputException;
import com.jemmic.addressbook.model.Contact;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Setter
public class ContactInfoRepository {
    @Value("${contact.repo.path}")
    private String contactRepositoryPath;

    /**
     * Method to save contact to file
     * @param contacts
     */
    public void saveContactToFile(List<Contact> contacts){
           List<Contact> allContact = getAllContact();
           allContact.addAll(contacts);
           persistContacts(allContact);
    }

    /**
     * Mrthod to persist contact to file
     * @param contacts
     */
    public void persistContacts(List<Contact> contacts){
        try (
                FileOutputStream fos = new FileOutputStream(contactRepositoryPath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                )  {
            oos.writeObject(contacts);
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Method to get all contact details
     * @return
     */
    public List<Contact> getAllContact(){
        try (
                FileInputStream fis = new FileInputStream(contactRepositoryPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ){
            return (ArrayList<Contact>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
    }

    public List<Contact> findContactByFirstAndSurName(ContactInfo removeContact) {
        List<Contact> listOfContact =
                getAllContact().stream().filter(con ->
                    con.getSurName().equalsIgnoreCase(removeContact.getSurName()) &&
                            con.getName().equalsIgnoreCase(removeContact.getName())
                ).collect(Collectors.toList());
        if(listOfContact.isEmpty() || listOfContact == null){
           throw new InvalidUserInputException( "No Contact found with Surname and Name");
        }
        return listOfContact;
    }

    /**
     * Method to remove contact
     * @param contactToremove
     */
    public void deleteContact(Contact contactToremove) {
        List<Contact> allContact = getAllContact();
        allContact.remove(contactToremove);
        persistContacts(allContact);
    }

    /**
     * Method to update contact info
     * @param updatedContact
     */
    public void updateContact(Contact updatedContact) {
        List<Contact> allContactList = getAllContact();
        Contact oldContact = allContactList.stream().filter(con ->
                con.getContactId().compareTo(updatedContact.getContactId()) == 0).findFirst().orElse(null);
        if(oldContact != null){
            allContactList.remove(oldContact);
            allContactList.add(updatedContact);
            persistContacts(allContactList);
        }else{
            System.err.println("Some unexcepted error occured");
        }
    }
}
