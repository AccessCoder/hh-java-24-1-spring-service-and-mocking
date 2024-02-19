package de.neuefische.hhjava241springserviceandmocking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.hhjava241springserviceandmocking.model.Student;
import de.neuefische.hhjava241springserviceandmocking.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
//MockMVC benötigt viel Config, diese Annotation nimmt uns diese ab!
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    //Simuliert Anfragen aus einer Fake UI
    private MockMvc mvc;

    @Autowired
    private StudentRepo repo;

    @Test
    void getAllStudents_shouldReturnEmptyList_WhenCalledInitially() throws Exception {
        //GIVEN
        List<Student> studentList = List.of();
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get("/api/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(studentList.toString()));

    }

    @Test
    @DirtiesContext
    void addStudent_shouldReturnNewStudent_WhenCalledWithValidJSON() throws Exception {
        //GIVEN
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.post("/api/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "Alex", 
                            "email" : "alex@alex.de",
                            "password" : "1234",
                            "socialSecurityNumber": "0001"                       
                        
                        }
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                         {
                             "name": "Alex",
                             "email" : "alex@alex.de",
                             "password" : "1234",
                             "socialSecurityNumber": "0001"                       
                         }
                         """
                ))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DirtiesContext
    void getById_shouldReturnAlex_WhenCalledWithValidId()throws Exception{
        //GIVEN
        Student student = new Student("1", "Alex", "alex@alex.de", "1234", "0001");
        repo.save(student);
        //WHEN & THEN
        mvc.perform(MockMvcRequestBuilders.get("/api/student/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                           {
                               "id": "1",
                               "name": "Alex",
                               "email" : "alex@alex.de",
                               "password" : "1234",
                               "socialSecurityNumber": "0001"                       
                           }
                           """
                ));

    }

    @Test
    @DirtiesContext
    void getById_shouldReturnAlex_WhenCalledWithValidId_LongVersion()throws Exception{
        //GIVEN
        //Objectmapper hilft uns gleich ein Objekt umzuwandeln
        ObjectMapper objectMapper = new ObjectMapper();
        //Zwischenspeichern des Ergebnisses mit ".andReturn" in einem MvcResult Objekt
        MvcResult temp = mvc.perform(MockMvcRequestBuilders.post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Alex", 
                            "email" : "alex@alex.de",
                            "password" : "1234",
                            "socialSecurityNumber": "0001"                       
                        
                        }
                        """))
                .andReturn();
        //Umwandeln des Ergebnisses in einen String
        String jsonContent = temp.getResponse().getContentAsString();
        //Dank es Objectmappers umwandeln des Strings in ein Student Objekt, damit wir an die ID kommen!
        Student returnedStudent = objectMapper.readValue(jsonContent, Student.class);

        //WHEN & THEN
        //Anfrage an die URL + die ID, welche wir zuvor mit dem Objectmapper in einem Objekt hatten
        mvc.perform(MockMvcRequestBuilders.get("/api/student/"+returnedStudent.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                           {
                               "id": "%s",
                               "name": "Alex",
                               "email" : "alex@alex.de",
                               "password" : "1234",
                               "socialSecurityNumber": "0001"                       
                           }
                           """.formatted(returnedStudent.getId())
                ));

    }

}