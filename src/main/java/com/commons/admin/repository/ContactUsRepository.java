package com.commons.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.commons.admin.models.ContactsForm;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactsForm, Long> {
	
	Optional<String> findByEmailAddress(String emailAddress); 
	
	Optional<String> findByfullname(String fullname); 
	
	Optional<String> findByPhoneNumber(String phoneNumber); 
	
	Optional<String> findByMessage(String message);
	
//	String findByfullname(String fullname);

}
