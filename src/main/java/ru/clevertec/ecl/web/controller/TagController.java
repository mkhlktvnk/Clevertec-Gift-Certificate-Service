package ru.clevertec.ecl.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.domain.entity.Tag;
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
    public List<TagModel> findAllByPageable(@PageableDefault Pageable pageable) {
        List<Tag> tags = tagService.findAllByPageable(pageable);
        return mapper.mapToModel(tags);
    }

    @GetMapping("/tags/{id}")
    public TagModel findById(@PathVariable Long id) {
        Tag tag = tagService.findById(id);
        return mapper.mapToModel(tag);
    }

    @GetMapping("/tags/popular")
    public TagModel findUserMostPopularTagWithTheHighestCostOfAllOrders() {
        Tag tag = tagService.findUserMostPopularTagWithTheHighestCostOfAllOrders();
        return mapper.mapToModel(tag);
    }

    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagModel save(@Valid @RequestBody TagModel tagModel) {
        Tag tag = mapper.mapToEntity(tagModel);
        return mapper.mapToModel(tagService.insert(tag));
    }

    @PatchMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody TagModel updateTagModel) {
        Tag updateTag = mapper.mapToEntity(updateTagModel);
        tagService.updateById(id, updateTag);
    }

    @DeleteMapping("/tags/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
