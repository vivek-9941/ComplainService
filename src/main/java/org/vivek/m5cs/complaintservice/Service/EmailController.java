package org.vivek.m5cs.complaintservice.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="EmailSender")
public interface EmailController {
    @PostMapping("/email/send")
    boolean sendEmail(@RequestParam String email,
                      @RequestParam String subject,
                      @RequestParam String body);
}
