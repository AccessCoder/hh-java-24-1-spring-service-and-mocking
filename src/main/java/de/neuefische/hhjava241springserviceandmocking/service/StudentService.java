package de.neuefische.hhjava241springserviceandmocking.service;

import de.neuefische.hhjava241springserviceandmocking.model.Student;
import de.neuefische.hhjava241springserviceandmocking.model.dtos.StudentDto;
import de.neuefische.hhjava241springserviceandmocking.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo repo;

    public List<Student> getAllStudents(){
        return repo.findAll();
    }

    public List<StudentDto> getAllFriends() {
        List<Student> temp = repo.findAll();
        List<StudentDto> dtoList = new ArrayList<>();
        for (Student s: temp) {
            StudentDto sdto = new StudentDto(s.getId(), s.getName());
            dtoList.add(sdto);
        }
        return dtoList;
    }

    public Student saveNewStudent(Student student) {
        repo.save(student);
        return repo.findById(student.getId()).orElseThrow();
    }
}
