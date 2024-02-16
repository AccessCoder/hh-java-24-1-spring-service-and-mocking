package de.neuefische.hhjava241springserviceandmocking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("Students")
public class Student {


    private String id;
    private String name;

    private String email;
    private String password;
    private String socialSecurityNumber;
}
