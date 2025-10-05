package org.vivek.m5cs.complaintservice.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vivek.m5cs.complaintservice.Model.Incidence;
import org.vivek.m5cs.complaintservice.Repository.IncidenceRepository;
import org.vivek.m5cs.complaintservice.Service.IncidenceService;

@Service
public class IncidenceServiceImplementation implements IncidenceService {
    @Autowired
    IncidenceRepository incidenceRepository;

    @Override
    public Incidence save(Incidence incidence) {
        return incidenceRepository.save(incidence);
    }
}
