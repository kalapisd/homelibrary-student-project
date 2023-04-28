package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
@Tag(name = "Here you can make operations on genre")
public class GenreController {

    private GenreService genreService;

    @Autowired
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
            description = "Here you can a genre by it's ID.")
    public ResponseEntity<GenreDTO> findGenreById(
            @Parameter(description = "Id of the genre", example = "1")
            @PathVariable("id") long id){
        GenreDTO genreDto = genreService.findGenreById(id);
        return genreDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(genreDto);

    }

    @GetMapping("/findbytype")
    @Operation(summary = "Find genre by id",
            description = "Here you can a genre by it's genretype .")
    public ResponseEntity<GenreDTO> findGenreByType(
            @Parameter(description = "genre type", example = "KIDS")
            @RequestParam("genretype") String genretype){
        GenreDTO genreDto = genreService.findGenreByGenreType(genretype);
        return genreDto == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(genreDto);
    }

}
