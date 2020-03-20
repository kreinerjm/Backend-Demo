package com.kreiner.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final ContactRepository contactRepository;
    private final PhoneRepository phoneRepository;

    public ContactController(ContactRepository contactRepository, PhoneRepository phoneRepository){
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
    }

    @GetMapping("/contacts")
    List<Contact> all(){
        List<Contact> toReturn = new ArrayList<>();
        List<ContactDBO> contacts = contactRepository.findAll();
        List<PhoneDBO> phones = phoneRepository.findAll();
        contacts.forEach(contact -> {
           List<PhoneDBO> contactPhones = phones.stream().filter(it -> it.getContactId() == contact.getId()).collect(Collectors.toList());
           Phone[] returnPhones = new Phone[contactPhones.size()];
           for(int i = 0; i < returnPhones.length; i++){
               returnPhones[i] = new Phone(contactPhones.get(i));
           }
           toReturn.add(combine(contact,returnPhones));
        });
        return toReturn;
    }

    @PostMapping("/contacts")
    Contact newContact(@RequestBody Contact newContact){
        ContactDBO contactDBO = new ContactDBO(newContact);
        List<PhoneDBO> phoneDBOS = new ArrayList<>();
        for(int i = 0; i < newContact.getPhone().length; i++){
            phoneDBOS.add(new PhoneDBO(newContact.getPhone()[i]));
        }
        ContactDBO responseContact = contactRepository.save(contactDBO);
        phoneDBOS.forEach(it -> it.setContactId(responseContact.getId()));
        PhoneDBO[] responsePhones = listToArray(phoneRepository.saveAll(phoneDBOS));
        Contact combine = combine(responseContact, responsePhones);
        return combine;
    }

    private PhoneDBO[] listToArray(List<PhoneDBO> list){
        PhoneDBO[] toRet = new PhoneDBO[list.size()];
        return list.toArray(toRet);
    }

    @GetMapping("/contacts/{id}")
    Contact one(@PathVariable Long id){
        ContactDBO contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException());
        PhoneDBO[] phones = listToArray(phoneRepository.findByContactId(contact.getId()));
        return combine(contact,phones);
    }

    @PutMapping("/contacts/{id}")
    Contact replaceContact(@RequestBody Contact newContact, @PathVariable Long id){
        return contactRepository.findById(id)
            .map(contact -> {
                logger.info("we are in side the map lambda");

                contact.setFirstName(newContact.getName().getFirst());
                contact.setMiddleName(newContact.getName().getMiddle());
                contact.setLastName(newContact.getName().getLast());
                contact.setStreet(newContact.getAddress().getStreet());
                contact.setCity(newContact.getAddress().getCity());
                contact.setState(newContact.getAddress().getState());
                contact.setZip(newContact.getAddress().getZip());
                contact.setEmail(newContact.getEmail());

                List<PhoneDBO> phoneNumbers = phoneRepository.findByContactId(contact.getId());
                phoneRepository.deleteAll(phoneNumbers);
                List<PhoneDBO> phoneDBOS = new ArrayList<>();
                for(int i = 0; i < newContact.getPhone().length; i++){
                    phoneDBOS.add(new PhoneDBO(newContact.getPhone()[i]));
                    phoneDBOS.get(i).setContactId(contact.getId());
                }
                return combine(contactRepository.save(contact), listToArray(phoneRepository.saveAll(phoneDBOS)));

            })
            .orElseGet(() -> {
                logger.info("we are inside the or else get lambda");
                ContactDBO dbo = new ContactDBO(newContact);
                ContactDBO saved = contactRepository.save(dbo);
                List<PhoneDBO> phoneDBOS = new ArrayList<>();
                for(int i = 0; i < newContact.getPhone().length; i++){
                    phoneDBOS.add(new PhoneDBO(newContact.getPhone()[i]));
                    phoneDBOS.get(i).setContactId(saved.getId());
                }
                return combine(saved, listToArray(phoneRepository.saveAll(phoneDBOS)));
            });
    }

    @DeleteMapping("/contacts/{id}")
    void deleteContact(@PathVariable Long id){
        contactRepository.deleteById(id);
        List<PhoneDBO> phoneNumbers = phoneRepository.findByContactId(id);
        phoneRepository.deleteAll(phoneNumbers);
    }

    private Contact combine(ContactDBO dbo, Phone[] phones){
        return new Contact(dbo,phones);
    }

    private Contact combine(ContactDBO dbo, PhoneDBO[] phones){
        Phone[] newPhones = new Phone[phones.length];
        for(int i = 0; i < phones.length; i++){
            newPhones[i] = new Phone(phones[i]);
        }
        return combine(dbo, newPhones);
    }
}
