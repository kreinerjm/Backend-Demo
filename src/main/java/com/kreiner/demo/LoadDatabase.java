package com.kreiner.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ContactRepository contactRepository, PhoneRepository phoneRepository){
        ContactDBO contact = new ContactDBO();
        contact.setFirstName("John");
        contact.setMiddleName("Michael");
        contact.setLastName("Watson");
        contact.setCity("Richmond");
        contact.setStreet("123 Broad St");
        contact.setState("Virginia");
        contact.setZip("23220");
        contact.setEmail("jwat@gmail.com");

        List<PhoneDBO> phones = new ArrayList<>();

        phones.add(new PhoneDBO(1L,"804-958-1638","Cell"));
        phones.add(new PhoneDBO(1L,"804-123-4567","Home"));

        return args -> {
            log.info("Preloading " + contactRepository.save(contact));
            log.info("Preloading " + phoneRepository.saveAll(phones));
        };
    }
}
