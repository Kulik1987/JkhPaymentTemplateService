package ru.kulikovskiy.jkh.jkhpaymenttemplate.restcontroller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.dto.*;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.exception.NotFoundException;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.JkhTemplateService;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.service.ProviderService;

import java.util.List;


@RestController("jkhTemplateController")
@RequestMapping(value = "/jkhTemplate")
@AllArgsConstructor
public class JkhTemplateController {

    private final JkhTemplateService jkhTemplateService;
    private final ProviderService providerService;

    @RequestMapping(value = "/jkhTemplates", method = RequestMethod.GET)
    public ResponseEntity<List<JkhTemplatesClientResponseDto>> getJkhTemplates(@RequestParam("clientId") String clientId) {
        try {
            List<JkhTemplatesClientResponseDto> response = jkhTemplateService.getJkhTemplatesList(clientId);
            return ResponseEntity.ok().body(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createJkhTemplate(@RequestBody AddJkhTemplateRequestDto jkhTemplateResponseDto) {
        jkhTemplateService.addJkhTemplate(jkhTemplateResponseDto);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<JkhTemplateResponseDto> getJkhTemplate(@RequestParam("clientId") String clientId,
                                                                 @RequestParam("fullAddress") String fullAddress) {
        try {
            JkhTemplateResponseDto response = jkhTemplateService.getJkhTemplate(clientId, fullAddress);
            return ResponseEntity.ok().body(response);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateJkhTemplate(@RequestBody ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto) {
        try {
            jkhTemplateService.updateJkhTemplate(modifyJkhTemplateRequestDto);
            return ResponseEntity.ok().build();
        } catch (NotFoundException t) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteJkhTemplate(@RequestParam("clientId") String clientId,
                                                  @RequestParam("jkhTemplateId") String jkhTemplateId) {
        ModifyJkhTemplateRequestDto modifyJkhTemplateRequestDto = new ModifyJkhTemplateRequestDto(clientId, Integer.valueOf(jkhTemplateId));
        try {
            jkhTemplateService.deleteJkhTemplate(modifyJkhTemplateRequestDto);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/provider", method = RequestMethod.POST)
    public ResponseEntity<Void> addServiceJkhTemplate(ServiceRequestDto serviceRequestDto) {
        try {
            providerService.addProviderJkhTemplate(serviceRequestDto);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/provider", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteServiceJkhTemplate(@RequestParam("clientId") String clientId,
                                                         @RequestParam("jkhTemplateId") String jkhTemplateId,
                                                         @RequestParam("inn") String innProvider) {
        try {
            ServiceRequestDto serviceRequestDto = new ServiceRequestDto(clientId, Integer.valueOf(jkhTemplateId), innProvider);
            providerService.deleteServiceJkhTemplate(serviceRequestDto);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}