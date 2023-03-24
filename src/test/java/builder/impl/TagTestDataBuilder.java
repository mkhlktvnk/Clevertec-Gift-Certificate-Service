package builder.impl;

import builder.TestDataBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.domain.entity.Tag;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aTag")
public class TagTestDataBuilder implements TestDataBuilder<Tag> {
    private Long id = 0L;
    private String name = "";

    @Override
    public Tag build() {
        return Tag.builder()
                .id(id)
                .name(name)
                .build();
    }
}
