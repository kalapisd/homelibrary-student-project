package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<GenreDTO> getAuthors() {
        return genreService.findAllGenre();
    }

    @GetMapping("{id}")
    public GenreDTO findGenreById(@PathVariable("id") long id){
        return genreService.findGenreById(id);
    }

}
