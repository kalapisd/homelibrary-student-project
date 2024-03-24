package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@RequestMapping("/genre")
@Tag(name = "Here you can make operations on genre.")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @Operation(summary = "Find all genre",
            description = "Here you can get all genre stored in the database.")
    public List<GenreDTO> getAllGenre() {
        return genreService.findAllGenre();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find genre by id",
            description = "Here you can find a genre by it's ID.")
    public ResponseEntity<GenreDTO> findGenreById(
            @Parameter(description = "Id of the genre", example = "1")
            @PathVariable("id") long id) {
        GenreDTO genreDto = genreService.findGenreById(id);
        return genreDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(genreDto);

    }

    @GetMapping("/type/{genretype}")
    @Operation(summary = "Find genre by type",
            description = "Here you can find a genre by it's genre type.")
    public ResponseEntity<GenreDTO> findGenreByType(
            @Parameter(description = "genre type", example = "KIDS")
            @PathVariable("genretype") String genreType) {
        try {
            GenreDTO genreDto = genreService.findGenreByGenreType(genreType);
            return ResponseEntity.ok(genreDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
