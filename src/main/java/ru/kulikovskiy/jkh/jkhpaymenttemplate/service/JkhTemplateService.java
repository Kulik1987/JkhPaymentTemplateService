package ru.kulikovskiy.jkh.jkhpaymenttemplate.service;

import com.sun.istack.NotNull;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.JkhTemplate;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;

import java.util.List;

public interface JkhTemplateService {
    List<JkhTemplatesClientResponseDto> getJkhTemplatesList(@NotNull String clientId) throws NotFoundException;

    void addJkhTemplate(AddJkhTemplateRequestDto request);

    JkhTemplateResponseDto getJkhTemplate(@NotNull String clientId, String fullAddress) throws NotFoundException;

    void updateJkhTemplate(ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto) throws NotFoundException;

    void deleteJkhTemplate(ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto) throws NotFoundException;

    JkhTemplate getJkhTemplateModifyProvider(String clientId, Integer jkhTemplateId) throws NotFoundException;
}
