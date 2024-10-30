package com.blog.cesmusic.services;

import com.blog.cesmusic.data.DTO.v1.output.TagDTO;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class TagService {
    private final Logger LOGGER = Logger.getLogger(TagService.class.getName());

    private final TagRepository repository;
    private final Mapper mapper;

    @Autowired
    public TagService(
            TagRepository repository,
            Mapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TagDTO findById(UUID id) {
        LOGGER.info("Finding tag by id");
        return mapper.map(
                repository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Nenhuma tag encontrada para este id (%s)", id))),
                TagDTO.class
        );
    }

    public List<TagDTO> findByPostId(UUID postId) {
        LOGGER.info("Finding tags by post id");
        return mapper.map(
                repository.findByPostId(postId),
                TagDTO.class
        );
    }
}
