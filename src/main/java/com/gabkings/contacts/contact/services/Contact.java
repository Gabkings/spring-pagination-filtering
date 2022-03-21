package com.gabkings.contacts.contact.services;

import com.gabkings.contacts.contact.dao.CustomerRepo;
import com.gabkings.contacts.contact.model.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class Contact {

    @Autowired
    CustomerRepo customerRepo;

    public Page<CustomerEntity> readContacts(Pageable paging){
        return  customerRepo.findAll(paging);
    }
}
