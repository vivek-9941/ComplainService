package org.vivek.m5cs.complaintservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vivek.m5cs.complaintservice.Model.Incidence;

public interface IncidenceRepository extends JpaRepository<Incidence, Integer> {

}
