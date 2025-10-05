package org.vivek.m5cs.complaintservice.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vivek.m5cs.complaintservice.Model.AppUser;
import org.vivek.m5cs.complaintservice.Model.Complaint;

import java.util.List;

@FeignClient(name="UserService")
public interface AppuserClient {
    @GetMapping("/user/getuser")
    public AppUser getuser(@RequestParam String username);
//    @GetMapping("/getallcomplaints")
//    List<Complaint> getComplaints(String username);
    @GetMapping("/user/get")
    public AppUser getUser(@RequestParam String token );
}

