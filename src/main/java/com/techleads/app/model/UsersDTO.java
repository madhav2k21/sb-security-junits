package com.techleads.app.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class UsersDTO extends RepresentationModel<UsersDTO> {


    private Long id;


    private String userId;


    private String firstName;


    private String lastName;


    private String email;


    private String encryptedPassword;
}
