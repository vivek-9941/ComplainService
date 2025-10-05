package org.vivek.m5cs.complaintservice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Incidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String time;
    private String  date;
    @Embedded
    private Address address;
    @Column(length = 2000)
    private String description;
    @Column(nullable = true)
    private String crimetype;
}

