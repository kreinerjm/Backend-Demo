package com.kreiner.demo;

import org.springframework.data.jpa.repository.JpaRepository;

interface ContactRepository extends JpaRepository<ContactDBO, Long> {

}
