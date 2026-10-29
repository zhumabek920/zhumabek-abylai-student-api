package kz.zhumabek.studentapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private StudentRepository repository;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        repository = mock(StudentRepository.class);
        controller = new StudentController(repository);
        ReflectionTestUtils.setField(controller, "version", "test");
    }

    private Student abylai() {
        Student s = new Student();
        s.setId(1L);
        s.setFirstName("Abylai");
        s.setLastName("Zhumabek");
        s.setEmail("abylai.38304@student.kz");
        s.setAge(20);
        return s;
    }

    @Test
    void infoShowsStudentName() {
        Map<String, String> info = controller.info();
        assertEquals("Wrong Name", info.get("author"));
    }

    @Test
    void getByIdReturnsStudent() {
        when(repository.findById(1L)).thenReturn(Optional.of(abylai()));
        assertEquals("Zhumabek", controller.getById(1L).getLastName());
    }

    @Test
    void getByIdReturns404ForMissingStudent() {
        when(repository.findById(999L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> controller.getById(999L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void createSavesStudent() {
        when(repository.save(any(Student.class))).thenReturn(abylai());
        assertEquals("Abylai", controller.create(abylai()).getFirstName());
    }

    @Test
    void updateMissingStudentReturns404() {
        when(repository.findById(42L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> controller.update(42L, abylai()));
    }
}
