package ru.kulikovskiy.jkh.jkhpaymenttemplate.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.Organization;
import ru.kulikovskiy.jkh.jkhpaymenttemplate.entity.OrganizationsList;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class OrganizationsMapper {
    @Autowired
    private ObjectMapper objectMapper;

    public List<Organization> organizationsFromJson(String json) {
        Organization[] organization = null;
        try {
            organization = objectMapper.readValue(json, Organization[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return CollectionUtils.arrayToList(organization);
    }

    public String jsonOrganizationsFromEntity(Set<Organization> organizationSet) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(organizationSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String jsonOrganizationsFromEntity(List<Organization> organizationSet) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(organizationSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
