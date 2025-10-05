package org.vivek.m5cs.complaintservice.Service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vivek.m5cs.complaintservice.Model.Person;
import org.vivek.m5cs.complaintservice.Repository.PersonRepository;
import org.vivek.m5cs.complaintservice.Service.PersonService;

@Service
public class PersonServiceImplementation implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
