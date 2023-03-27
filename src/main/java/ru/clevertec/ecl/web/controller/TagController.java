package ru.clevertec.ecl.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.TagService;
import ru.clevertec.ecl.web.mapper.TagMapper;
import ru.clevertec.ecl.web.model.TagModel;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper mapper = Mappers.getMapper(TagMapper.class);

    @GetMapping("/tags")
    public List<TagModel> getTags(@PageableDefault Pageable pageable) {
        return mapper.mapToModel(tagService.findAllByPageable(pageable));
    }

    @GetMapping("/tags/{id}")
    public TagModel getTagById(@PathVariable Long id) {
        return mapper.mapToModel(tagService.findById(id));
    }

    @PostMapping("/tags")
    public TagModel saveTag(@Valid @RequestBody TagModel tagModel) {
        return mapper.mapToModel(tagService.insert(mapper.mapToEntity(tagModel)));
    }

    @PutMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTagById(@PathVariable Long id, @Valid @RequestBody TagModel tagModel) {
        tagService.updateById(id, mapper.mapToEntity(tagModel));
    }

    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
