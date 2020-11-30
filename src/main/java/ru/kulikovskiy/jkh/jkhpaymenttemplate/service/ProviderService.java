package ru.kulikovskiy.jkh.jkhpaymenttemplate.service;

import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.ServiceRequestDto;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;

public interface ProviderService {
    void addProviderJkhTemplate(ServiceRequestDto serviceRequestDto) throws NotFoundException;

    void deleteServiceJkhTemplate(ServiceRequestDto serviceRequestDto) throws NotFoundException;

}
