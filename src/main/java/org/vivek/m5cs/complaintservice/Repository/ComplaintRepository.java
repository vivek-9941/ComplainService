package org.vivek.m5cs.complaintservice.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vivek.m5cs.complaintservice.Model.AppUser;
import org.vivek.m5cs.complaintservice.Model.Complaint;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    List<Complaint> findByUser(AppUser user);


}
