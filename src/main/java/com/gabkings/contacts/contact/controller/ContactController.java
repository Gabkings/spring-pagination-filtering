package com.gabkings.contacts.contact.controller;

import com.gabkings.contacts.contact.model.CustomerEntity;
import com.gabkings.contacts.contact.services.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ContactController {

    Contact contact;

    @Autowired
    public ContactController(Contact contact) {
        this.contact = contact;
    }

    @GetMapping("/contacts")
    public ResponseEntity getAllContacts(@RequestParam(defaultValue = "") String country,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "40") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        List<CustomerEntity> allMatches = new ArrayList<>();
        System.out.println(country);

        try {
            Page<CustomerEntity> contacts = contact.readContacts(paging);
            System.out.println(country);
            if(country.equals("")){
                return new ResponseEntity(contacts, HttpStatus.OK);
            }else {
                String countryCode = null;
                if (country.equalsIgnoreCase("cameroon")) {
                    countryCode = "\\(237\\)\\ ?[2368]\\d{7,8}$";
                } else if (country.equalsIgnoreCase("ethiopia")) {
                    countryCode = "\\(251\\)\\ ?[1-59]\\d{8}$";
                } else if (country.equalsIgnoreCase("Morocco")) {
                    countryCode = "\\(212\\)\\ ?[5-9]\\d{8}$";
                } else if (country.equalsIgnoreCase("Mozambique")) {
                    countryCode = "\\(258\\)\\ ?[28]\\d{7,8}$";
                } else if (country.equalsIgnoreCase("Uganda")) {
                    countryCode = "\\(256\\)\\ ?\\d{9}$";
                }

                Pattern pattern = Pattern.compile(countryCode, Pattern.CASE_INSENSITIVE);

                for (CustomerEntity node : contacts) {
                    Matcher matcher = pattern.matcher(node.getPhone());
                    if (matcher.matches()) {
                        allMatches.add(node);
                    }
                }
                return new ResponseEntity(allMatches, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
