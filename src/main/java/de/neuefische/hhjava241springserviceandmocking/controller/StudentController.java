package de.neuefische.hhjava241springserviceandmocking.controller;

import de.neuefische.hhjava241springserviceandmocking.model.Student;
import de.neuefische.hhjava241springserviceandmocking.model.dtos.StudentDto;
import de.neuefische.hhjava241springserviceandmocking.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public List<Student> getAllStudents(){
        return service.getAllStudents();
    }

    @GetMapping("/friends")
    public List<StudentDto> getAllFriends(){
        return service.getAllFriends();
    }

    @PostMapping
    public Student saveNewStudent(@RequestBody Student student){
        return service.saveNewStudent(student);
    }
}
