package com.lilblue.demo.documents;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "player")
public class Player {
    @Id

    private String id;

    @NotEmpty
    private String name;

    private String lastname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String photo;

    public Player(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }
}
