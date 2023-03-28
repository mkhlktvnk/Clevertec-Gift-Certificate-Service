package ru.clevertec.ecl.web.mapper;

import builder.impl.TagTestDataBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.domain.entity.Tag;
import ru.clevertec.ecl.web.model.TagModel;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.assertThat;

class TagMapperTest {
    private final TagMapper mapper = Mappers.getMapper(TagMapper.class);

    private static Stream<Tag> provideTags() {
        return Stream.of(
                TagTestDataBuilder.aTag().withId(1L).withName("name-1").build(),
                TagTestDataBuilder.aTag().withId(2L).withName("name-2").build(),
                TagTestDataBuilder.aTag().withId(3L).withName("name-3").build(),
                TagTestDataBuilder.aTag().withId(4L).withName("name-4").build(),
                TagTestDataBuilder.aTag().withId(5L).withName("name-5").build()
        );
    }

    @ParameterizedTest
    @MethodSource("provideTags")
    void mapToModel(Tag tag) {
        TagModel mappedModel = mapper.mapToModel(tag);

        assertAll(
                () -> assertThat(mappedModel.getId()).isEqualTo(tag.getId()),
                () -> assertThat(mappedModel.getName()).isEqualTo(tag.getName())
        );
    }
}