package org.vivek.m5cs.complaintservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vivek.m5cs.complaintservice.Model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
