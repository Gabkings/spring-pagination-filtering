package com.gabkings.contacts.contact.dao;

import com.gabkings.contacts.contact.model.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
public interface CustomerRepo  extends JpaRepository<CustomerEntity, Integer> {

    Page<CustomerEntity> findAll(Pageable pageable);

//    List<CustomerEntity> findByCountryCode(String code);
//
//    List<CustomerEntity> findByName(String title);
}
