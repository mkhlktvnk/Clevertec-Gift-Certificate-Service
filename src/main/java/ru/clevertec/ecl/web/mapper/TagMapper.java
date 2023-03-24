package ru.clevertec.ecl.web.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.web.model.TagModel;

import java.util.Collection;
import java.util.List;

@Mapper
public interface TagMapper {
    Tag mapToEntity(TagModel model);
    TagModel mapToModel(Tag entity);
    List<TagModel> mapToModel(Collection<Tag> entities);
}
