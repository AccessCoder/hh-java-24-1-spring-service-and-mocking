package de.neuefische.hhjava241springserviceandmocking.repository;

import de.neuefische.hhjava241springserviceandmocking.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends MongoRepository<Student, String> {
}
