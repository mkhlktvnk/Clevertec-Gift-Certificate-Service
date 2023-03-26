package ru.clevertec.ecl.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.web.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;
import ru.clevertec.ecl.web.model.GiftCertificateModel;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper mapper = Mappers.getMapper(GiftCertificateMapper.class);

    @GetMapping("/certificates")
    public List<GiftCertificateModel> getGiftCertificates(
            @PageableDefault Pageable pageable, @Valid GiftCertificateCriteria criteria) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificateModel getGiftCertificateById(@PathVariable Long id) {
        return mapper.mapToModel(giftCertificateService.getById(id));
    }

    @PostMapping("/certificates")
    public GiftCertificateModel saveGiftCertificate(@Valid @RequestBody GiftCertificateModel giftCertificateModel) {
        return mapper.mapToModel(giftCertificateService.save(mapper.mapToEntity(giftCertificateModel)));
    }

    @PutMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGiftCertificate(@PathVariable Long id,
                                      @Valid @RequestBody GiftCertificateModel giftCertificateModel) {
        giftCertificateService.updateById(id, mapper.mapToEntity(giftCertificateModel));
    }

    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificateById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}
