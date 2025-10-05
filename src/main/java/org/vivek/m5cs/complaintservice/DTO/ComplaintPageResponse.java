package org.vivek.m5cs.complaintservice.DTO;

import lombok.Getter;
import lombok.Setter;
import org.vivek.m5cs.complaintservice.Model.Complaint;

import java.util.List;

@Setter
@Getter
public class ComplaintPageResponse {
    private List<Complaint> complaints;
    private long total;

    public ComplaintPageResponse(List<Complaint> complaints, long total) {
        this.complaints = complaints;
        this.total = total;
    }

}
