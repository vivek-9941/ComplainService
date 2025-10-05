package org.vivek.m5cs.complaintservice.Service;


import org.vivek.m5cs.complaintservice.DTO.ComplaintPageResponse;
import org.vivek.m5cs.complaintservice.Model.AppUser;
import org.vivek.m5cs.complaintservice.Model.Complaint;

import java.util.List;

public interface ComplaintService {
    Complaint saveComplaint(Complaint complaint, AppUser user) throws Exception;
    Complaint updateComplaint(String verdict,int id);
    ComplaintPageResponse getAllComplaints(Integer page, Integer size);
    List<Complaint> findComplaintsByUser(String username);
}
