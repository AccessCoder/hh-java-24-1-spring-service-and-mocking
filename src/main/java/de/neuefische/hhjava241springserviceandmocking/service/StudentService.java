package de.neuefische.hhjava241springserviceandmocking.service;

import de.neuefische.hhjava241springserviceandmocking.model.Student;
import de.neuefische.hhjava241springserviceandmocking.model.dtos.StudentDto;
import de.neuefische.hhjava241springserviceandmocking.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo repo;
    private final IdService idService;



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
        Student temp = student.withId(idService.generateUUID());
        repo.save(temp);
        return repo.findById(temp.getId()).orElseThrow();
    }
}
