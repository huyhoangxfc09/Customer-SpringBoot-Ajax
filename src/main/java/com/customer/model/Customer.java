package com.customer.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String imagePath;
    @Column(columnDefinition = "boolean default 1")
    private boolean status = true;
    @Transient
    private MultipartFile image;
}
