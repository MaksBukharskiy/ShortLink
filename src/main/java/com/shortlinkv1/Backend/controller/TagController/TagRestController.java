package com.shortlinkv1.Backend.controller.TagController;

import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import com.shortlinkv1.Backend.repository.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public ResponseEntity <List<String>> getAll() {
        List<Tag> tags = tagRepository.findAll();

        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .toList();

        return ResponseEntity.ok(tagNames);
    }

}
