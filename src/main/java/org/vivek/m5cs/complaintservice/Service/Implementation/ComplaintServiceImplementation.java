package org.vivek.m5cs.complaintservice.Service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.vivek.m5cs.complaintservice.DTO.ComplaintPageResponse;

import org.vivek.m5cs.complaintservice.GenAi.GroqApiService;
import org.vivek.m5cs.complaintservice.GenAi.PromptDto;
import org.vivek.m5cs.complaintservice.Model.*;
import org.vivek.m5cs.complaintservice.Repository.ComplaintRepository;
import org.vivek.m5cs.complaintservice.Service.*;
import org.vivek.m5cs.complaintservice.utility.AESUtil;

import java.util.List;
import java.util.Objects;

@Service
public class ComplaintServiceImplementation implements ComplaintService {


    @Autowired
    private GroqApiService groqservice;

    @Autowired
    private EmailController emailcontroller;

    @Autowired
    private AppuserClient appUserService;

    @Autowired
    private IncidenceService incidenceService;

    @Autowired
    private PersonService personService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private AESUtil AESUtil;
    @Override
    public Complaint saveComplaint(Complaint complaint, AppUser usr) throws Exception {
        System.out.println(complaint);
        Person savedAccused = personService.save(complaint.getAccused());
        Person savedVictim = personService.save(complaint.getVictim());
        //description
        String incidence = AESUtil.decryptPlainText(complaint.getIncidence().getDescription());//decrypt here
        String summary = AESUtil.encryptPlainText(groqservice.callGroqApi(new PromptDto("Convert the given incident into a concise, grammatically correct, and legally appropriate description in formal language, suitable for the 'Incident Description' section of an FIR complaint. Respond with only the final description and nothing else." + " " + incidence).toString()));
        Incidence incidence1 = complaint.getIncidence();
        incidence1.setDescription(summary);
        //crime category;
        String prompt = AESUtil.decryptPlainText(summary) + "\n\n" +
                "Given the following list of crime categories:\n" +
                "[\n" +
                "  \"Cognizable Offenses\",\n" +
                "  \"Non-Cognizable Offenses\",\n" +
                "  \"Bailable Offenses\",\n" +
                "  \"Non-Bailable Offenses\",\n" +
                "  \"Compoundable Offenses\",\n" +
                "  \"Non-Compoundable Offenses\",\n" +
                "  \"Offenses against Women\",\n" +
                "  \"Offenses against Children\",\n" +
                "  \"Economic Offenses\",\n" +
                "  \"Cyber Crimes\",\n" +
                "  \"Drug Offenses\",\n" +
                "  \"Environmental Offenses\",\n" +
                "  \"Traffic Offenses\",\n" +
                "  \"Property Offenses\",\n" +
                "  \"Terrorism-related Offenses\",\n" +
                "  \"White-collar Crimes\",\n" +
                "  \"Corruption Offenses\",\n" +
                "  \"Fraudulent Practices\",\n" +
                "  \"Domestic Violence Offenses\",\n" +
                "  \"Sexual Harassment Offenses\",\n" +
                "  \"Human Trafficking Offenses\",\n" +
                "  \"Intellectual Property Crimes\",\n" +
                "  \"Hate Crimes\",\n" +
                "  \"Juvenile Offenses\",\n" +
                "  \"Organized Crime\",\n" +
                "  \"Money Laundering Offenses\",\n" +
                "  \"Forgery and Counterfeiting Offenses\",\n" +
                "  \"Alcohol-related Offenses\",\n" +
                "  \"Public Order Offenses\",\n" +
                "  \"Violation of Intellectual Property Rights\",\n" +
                "  \"Cyberbullying Offenses\",\n" +
                "  \"Religious Offenses\",\n" +
                "  \"Wildlife Crimes\",\n" +
                "  \"Labour Law Violations\",\n" +
                "  \"Immigration Offenses\"\n" +
                "]\n\n" +
                "Task:\n" +
                "1. Identify which categories best fit the provided summary.\n" +
                "2. only Return the identified categories as an array and nothing.\n" +
                "3. If no categories match, return [\"Not Identified\"].";
        String crimes = AESUtil.encryptPlainText(groqservice.callGroqApi(new PromptDto(prompt).toString()));
        incidence1.setCrimetype(crimes);
        System.out.println(incidence1);
        Incidence saved_incidence = incidenceService.save(incidence1);
        //user will onyl send the username . based on which whole user object will be
        //taken out from db and attached with complaint.

        System.out.println(usr);
        Complaint savedComplaint = new Complaint();

        //saving the complaint
        savedComplaint.setAccused(savedAccused);
        savedComplaint.setVictim(savedVictim);
        savedComplaint.setIncidence(saved_incidence);
        savedComplaint.setEvidenceLink(complaint.getEvidenceLink());
        savedComplaint.setStatus(ComplaintStatus.PROCESSING);
        savedComplaint.setUser(usr);

        Complaint complaint_from_DB = complaintRepository.save(savedComplaint);
        //email sending
        emailcontroller.sendEmail(
                usr.getEmail(),
                "FIR Complaint Registered Successfully – FIR ID: " + complaint_from_DB.getId(),
                "<html>" +
                        "<body>" +
                        "<p>Dear " + AESUtil.decryptPlainText(usr.getFirstName()) + " " + AESUtil.decryptPlainText(usr.getLastName()) + ",</p>" +
                        "<p>We acknowledge receipt of your complaint. Your FIR has been successfully registered in our system.</p>" +
                        "<p><strong>Your FIR Complaint ID is:</strong> " + "<b>"+complaint_from_DB.getId()+"<b>" + "</p>" +
                        "<p>Please keep this ID for future reference regarding your case. Authorities will review your complaint and take appropriate action as per legal procedures.</p>" +
                        "<p>If you have any further queries or additional information related to this complaint, please feel free to reply to this email or contact your nearest police station.</p>" +
                        "<br>" +
                        "<p>Thank you for bringing this to our attention.</p>" +
                        "<p>Regards,<br>Online FIR System</p>" +
                        "</body>" +
                        "</html>"
                );
        return complaint_from_DB;
    }

    @Override
    public Complaint updateComplaint(String verdict , int id) {
//        Complaint toSaved = new Complaint();
//        toSaved.setId(complaint.getId());
//        toSaved.setStatus(complaint.getStatus());
//        toSaved.setEvidenceLink(complaint.getEvidenceLink());
//        toSaved.setIncidence(incidenceService.save(complaint.getIncidence()));
//        toSaved.setAccused(personService.save(complaint.getAccused()));
//        toSaved.setVictim(personService.save(complaint.getVictim()));
//        toSaved.setUser(appUserService.save(complaint.getUser()));
      Complaint  complaint = complaintRepository.findById(id).orElse(null);
      if(complaint != null) {
          complaint.setStatus(Objects.equals(verdict, "SUCCEEDED") ? ComplaintStatus.SUCCEEDED: ComplaintStatus.REJECTED);
          return complaintRepository.save(complaint);
      }
        return null;
    }
    @Override
    public ComplaintPageResponse getAllComplaints(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Complaint> complaintPage = complaintRepository.findAll(pageable);
        return new ComplaintPageResponse(complaintPage.getContent(), complaintPage.getTotalElements());
    }
    @Override
    public List<Complaint> findComplaintsByUser(String username) {
        AppUser usr =  appUserService.getuser(username);
        return complaintRepository.findByUser(usr);
    }
}
