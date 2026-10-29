package kz.zhumabek.studentapi;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository repository;

    @Value("${app.version}")
    private String version;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    // Info: shows the author and version of the project
    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("author", "Zhumabek Abylai", "studentId", "38304", "version", version);
    }

    // READ all
    @GetMapping("/students")
    public List<Student> getAll() {
        return repository.findAll();
    }

    // READ one
    @GetMapping("/students/{id}")
    public Student getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> notFound(id));
    }

    // CREATE
    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student student) {
        student.setId(null);
        return repository.save(student);
    }

    // UPDATE
    @PutMapping("/students/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student data) {
        Student student = repository.findById(id)
                .orElseThrow(() -> notFound(id));
        student.setFirstName(data.getFirstName());
        student.setLastName(data.getLastName());
        student.setEmail(data.getEmail());
        student.setAge(data.getAge());
        return repository.save(student);
    }

    // DELETE
    @DeleteMapping("/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw notFound(id);
        }
        repository.deleteById(id);
    }

    private ResponseStatusException notFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Student with id " + id + " not found");
    }
}
