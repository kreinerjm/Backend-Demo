package com.kreiner.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PhoneRepository extends JpaRepository<PhoneDBO, Long> {

    public List<PhoneDBO> findByContactId(Long contactId);

}
