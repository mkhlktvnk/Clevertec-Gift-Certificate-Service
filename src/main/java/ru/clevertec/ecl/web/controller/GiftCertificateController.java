package ru.clevertec.ecl.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.ecl.domain.entity.GiftCertificate;
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
    public List<GiftCertificateModel> findAllBySortAndCriteria(
            @PageableDefault Pageable pageable, @Valid GiftCertificateCriteria criteria) {
        List<GiftCertificate> certificates = giftCertificateService.findAllByPageableAndCriteria(pageable, criteria);
        return mapper.mapToModel(certificates);
    }

    @GetMapping("/certificates/{id}")
    public GiftCertificateModel findById(@PathVariable Long id) {
        return mapper.mapToModel(giftCertificateService.getById(id));
    }

    @PostMapping("/certificates")
    public GiftCertificateModel save(@Valid @RequestBody GiftCertificateModel giftCertificateModel) {
        return mapper.mapToModel(giftCertificateService.save(mapper.mapToEntity(giftCertificateModel)));
    }

    @PutMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateById(@PathVariable Long id, @Valid @RequestBody GiftCertificateModel giftCertificateModel) {
        giftCertificateService.updateById(id, mapper.mapToEntity(giftCertificateModel));
    }

    @DeleteMapping("/certificates/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
    }
}
