package ru.hogwarts.school.Controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.Model.Faculty;
import ru.hogwarts.school.Repository.FacultyRepository;
import ru.hogwarts.school.Service.FacultyServiceImpl;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMocMVCTest {


    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @MockBean
    private FacultyRepository facultyRepository;
    @InjectMocks
    private FacultyController facultyController;

    private static Long id = 1L;
    private static String name = "Blowers";
    private static String color = "yellow";
    private static JSONObject studentObject;
    private static Faculty faculty;


    @BeforeEach
    void setUp() throws JSONException {
        studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("color", color);

        faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
    }

    @Test
    void testGetFacultyById() throws Exception {
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testAddFaculty() throws Exception {
        Faculty facultyToAdd = new Faculty();
        facultyToAdd.setName(name);
        facultyToAdd.setColor(color);

        Faculty returnedFaculty = new Faculty();
        returnedFaculty.setName(name);
        returnedFaculty.setColor(color);

        when(facultyService.createFaculty(facultyToAdd)).thenReturn(returnedFaculty);

        mockMvc.perform(post("/faculty")
                        .contentType("application/json")
                        .content("{\"name\":\"" + name + "\",\"color\":\"" + color + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testRemoveFaculty() throws Exception {

        doNothing().when(facultyService).removeFaculty(id);

        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName(name);
        updatedFaculty.setColor(color);

        when(facultyService.updateFaculty(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(put("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Faculty\", \"color\": \"Updated Color\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void testGetFacultySortByNameOrColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName(name);

        when(facultyService.getByColorOrName(color, name)).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty")
                        .param("color", color)
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(color))
                .andExpect(jsonPath("$[0].name").value(name));

        verify(facultyService).getByColorOrName(color, name);
    }

    @Test
    public void testGetAllFilteredByColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor(color);

        when(facultyService.getAllByColor(color)).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty")
                        .param("color", color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(color));

        verify(facultyService).getAllByColor(color);
    }

}