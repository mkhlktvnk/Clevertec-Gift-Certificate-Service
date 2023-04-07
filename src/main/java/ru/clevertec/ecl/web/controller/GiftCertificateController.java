package ru.clevertec.ecl.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
import ru.clevertec.ecl.service.GiftCertificateService;
import ru.clevertec.ecl.web.criteria.GiftCertificateCriteria;
import ru.clevertec.ecl.web.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.web.model.GiftCertificateModel;
import ru.clevertec.ecl.web.request.GiftCertificateUpdateRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper mapper = Mappers.getMapper(GiftCertificateMapper.class);

    @GetMapping("/certificates")
    public List<GiftCertificateModel> findAllByPageableAndCriteria(
            @PageableDefault Pageable pageable, @Valid GiftCertificateCriteria criteria) {
        List<GiftCertificate> certificates = giftCertificateService.findAllByPageableAndCriteria(pageable, criteria);
        return mapper.mapToModel(certificates);
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificateModel findById(@PathVariable Long id) {
        GiftCertificate giftCertificate = giftCertificateService.findById(id);
        return mapper.mapToModel(giftCertificate);
    }

    @PostMapping("/certificates")
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateModel save(@Valid @RequestBody GiftCertificateModel giftCertificateModel) {
        GiftCertificate giftCertificate = mapper.mapToEntity(giftCertificateModel);
        return mapper.mapToModel(giftCertificateService.save(giftCertificate));
    }

    @PatchMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody GiftCertificateUpdateRequest updateRequest) {
        giftCertificateService.updateById(id, updateRequest);
    }

    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}
