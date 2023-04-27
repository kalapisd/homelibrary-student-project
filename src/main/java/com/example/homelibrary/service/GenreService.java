package com.example.homelibrary.service;

import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.entity.GenreType;
import com.example.homelibrary.mapper.GenreMapper;
import com.example.homelibrary.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository genreRepository;
    private GenreMapper mapper;

    private Logger logger = LoggerFactory.getLogger(AuthorService.class);


    @Autowired
    public GenreService(GenreRepository genreRepository, GenreMapper mapper) {
        this.genreRepository = genreRepository;
        this.mapper = mapper;
    }

    public List<GenreDTO> findAllGenre() {
        return genreRepository.findAll().stream().map(mapper::toDTO).toList();
    }


    public Optional<Genre> getGenreByType(String type) {
        return genreRepository.findByGenreType(GenreType.valueOf(type));
    }

    public GenreDTO findGenreById(long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            return mapper.toDTO(genre.get());
        } else {
            logger.info("No such genre was found with id: {}!", id);
            return null;
        }
    }

    public GenreDTO findGenreByGenreType(String genreType) {
        Optional<Genre> genre = genreRepository.findByGenreType(GenreType.valueOf(genreType));
        if (genre.isPresent()) {
            return mapper.toDTO(genre.get());
        } else {
            logger.info("No such genre was found with id: {}!", genreType);
            return null;
        }
    }

    public void save(Genre genre) {
        genreRepository.save(genre);
    }

}
