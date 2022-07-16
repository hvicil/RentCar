package com.greenrent.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tbl_cmessage")
public class ContactMessage implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=1,max=50,message="Your Name '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message="Please provie your Name")
    @Column(length=50, nullable = false)
    private String name;

    @Size(min=1,max=50,message="Your subject '$validatedValue' must be between {min} and {max} chars long")
    @NotNull(message="Please provie your subject")
    @Column(length=50, nullable = false)
    private String subject;

    @Size(min=10,max=200,message="Your Message Body '$validatedValue' must be between {min} and {max} chars long")
    @NotNull(message="Please provie message body")
    @Column(length=200, nullable = false)
    private String body;


    @Email(message = "Provide correct an email")
    @Column(length=50, nullable = false)
    private String email;


}
