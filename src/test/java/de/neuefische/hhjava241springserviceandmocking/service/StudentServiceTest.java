package de.neuefische.hhjava241springserviceandmocking.service;

import de.neuefische.hhjava241springserviceandmocking.model.Student;
import de.neuefische.hhjava241springserviceandmocking.model.dtos.StudentDto;
import de.neuefische.hhjava241springserviceandmocking.repository.StudentRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    private final StudentRepo currywurst = mock(StudentRepo.class);
    private final IdService idMock = mock(IdService.class);
    StudentService service = new StudentService(currywurst, idMock);

    @Test
    void getAllStudents_shouldReturnEmptyList_WhenCalledInitially() {
        //GIVEN
        List<Student> expected = new ArrayList<>();
        when(currywurst.findAll()).thenReturn(new ArrayList<>());
        //WHEN
        List<Student> actual = service.getAllStudents();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllFriends() {
        //GIVEN
        Student student1 = new Student("1", "Test1", "jhgvkjvjh", "hmj", "kjhlk");
        Student student2 = new Student("2", "Test2", "jhgvkjvjh", "hmj", "kjhlk");
        List<Student> students = List.of(student1, student2);
        when(currywurst.findAll()).thenReturn(students);

        StudentDto studentDto1 = new StudentDto("1", "Test1");
        StudentDto studentDto2 = new StudentDto("2", "Test2");
        List<StudentDto> expected = new ArrayList<>();
        expected.add(studentDto1);
        expected.add(studentDto2);

        //WHEN
        List<StudentDto> actual = service.getAllFriends();
        //THEN
        assertEquals(expected, actual);
        verify(currywurst).findAll();
    }

    @Test
    void saveNewStudent() {
        //GIVEN
        Student studentToSave = new Student("jhg", "Test1", "jhgvkjvjh", "hmj", "kjhlk");
        Student expected = new Student("1", "Test1", "jhgvkjvjh", "hmj", "kjhlk");
        when(idMock.generateUUID()).thenReturn("1");
        when(currywurst.findById("1")).thenReturn(Optional.of(expected));
        //WHEN
        Student actual = service.saveNewStudent(studentToSave);
        //THEN
        assertEquals(expected, actual);
        verify(idMock).generateUUID();
        verify(currywurst).findById("1");
    }
}