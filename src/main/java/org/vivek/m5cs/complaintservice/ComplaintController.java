package org.vivek.m5cs.complaintservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vivek.m5cs.complaintservice.Model.AppUser;
import org.vivek.m5cs.complaintservice.Model.Complaint;
import org.vivek.m5cs.complaintservice.Service.AppuserClient;
import org.vivek.m5cs.complaintservice.Service.ComplaintService;

import java.util.List;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintService service;
    @Autowired
    private AppuserClient userService;

 // Properly injected utility class

    @PostMapping("/save")
    public ResponseEntity<?> saveComplaint(@RequestHeader("Authorization") String Auth,@RequestBody Complaint complaint) throws Exception {
        System.out.println(complaint);
        String token=Auth.substring(7);
        AppUser entity =userService.getUser(token);
        Complaint savedComplaint = service.saveComplaint(complaint,entity);
        if(savedComplaint != null) {
            return new ResponseEntity<>(savedComplaint, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchComplaint(@RequestHeader("Authorization") String Auth) throws Exception {
//     String username = util.getCurrentUsername(); // Call method on the injected instance
       String token=Auth.substring(7);
        AppUser entity =userService.getUser(token);
        List<Complaint> complaints = service.findComplaintsByUser(entity.getUsername());
        return new ResponseEntity<>(complaints, HttpStatus.OK);
    }




}