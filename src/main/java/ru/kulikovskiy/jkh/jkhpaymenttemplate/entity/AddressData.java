package ru.kulikovskiy.jkh.jkhpaymenttemplate.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressData {
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("region_fias_id")
    private String regionFiasId;

    @JsonProperty("region_kladr_id")
    private String regionKladrId;

    @JsonProperty("region_with_type")
    private String regionWithType;

    @JsonProperty("region_type")
    private String regionType;

    @JsonProperty("region_type_full")
    private String regionTypeFull;

    @JsonProperty("region")
    private String region;

    @JsonProperty("area_fias_id")
    private String areaFiasId;

    @JsonProperty("area_kladr_id")
    private String areaKladrId;

    @JsonProperty("area_with_type")
    private String areaWithType;

    @JsonProperty("area_type")
    private String areaType;

    @JsonProperty("area_type_full")
    private String areaTypeFull;

    @JsonProperty("area")
    private String area;

    @JsonProperty("city_fias_id")
    private String cityFiasId;

    @JsonProperty("city_kladr_id")
    private String cityKladrId;

    @JsonProperty("city_with_type")
    private String cityWithType;

    @JsonProperty("city_type")
    private String cityType;

    @JsonProperty("city_type_full")
    private String cityTypeFull;

    @JsonProperty("city")
    private String city;

    @JsonProperty("city_area")
    private String cityArea;

    @JsonProperty("city_district_fias_id")
    private String cityDistrictFiasId;

    @JsonProperty("city_district_kladr_id")
    private String cityDistrictKladrId;

    @JsonProperty("city_district_with_type")
    private String cityDistrictWithType;

    @JsonProperty("city_district_type")
    private String cityDistrictType;

    @JsonProperty("city_district_type_full")
    private String cityDistrictTypeFull;

    @JsonProperty("city_district")
    private String cityDistrict;

    @JsonProperty("settlement_fias_id")
    private String settlementFiasId;

    @JsonProperty("settlement_kladr_id")
    private String settlementKladrId;

    @JsonProperty("settlement_with_type")
    private String settlementWithType;

    @JsonProperty("settlement_type")
    private String settlementType;

    @JsonProperty("settlement_type_full")
    private String settlementTypeFull;

    @JsonProperty("settlement")
    private String settlement;

    @JsonProperty("street_fias_id")
    private String streetFiasId;

    @JsonProperty("street_kladr_id")
    private String streetKladrId;

    @JsonProperty("street_with_type")
    private String streetWithType;

    @JsonProperty("street_type")
    private String streetType;

    @JsonProperty("street_type_full")
    private String streetTypeFull;

    @JsonProperty("street")
    private String street;

    @JsonProperty("house_fias_id")
    private String houseFiasId;

    @JsonProperty("house_kladr_id")
    private String houseKladrId;

    @JsonProperty("house_type")
    private String houseType;

    @JsonProperty("house_type_full")
    private String houseTypeFull;

    @JsonProperty("house")
    private String house;

    @JsonProperty("block_type")
    private String blockType;

    @JsonProperty("block_type_full")
    private String blockTypeFull;

    @JsonProperty("block")
    private String block;

    @JsonProperty("flat_type")
    private String flatType;

    @JsonProperty("flat_type_full")
    private String flatTypeFull;

    @JsonProperty("flat")
    private String flat;

    @JsonProperty("flat_area")
    private String flatArea;

    @JsonProperty("square_meter_price")
    private String squareMeterPrice;

    @JsonProperty("flat_price")
    private String flatPrice;

    @JsonProperty("postal_box")
    private String postalBox;

    @JsonProperty("fias_id")
    private String fiasId;

    @JsonProperty("fias_code")
    private String fiasCode;

    @JsonProperty("fias_level")
    private String fiasLevel;

    @JsonProperty("fias_actuality_state")
    private String fiasActualityState;

    @JsonProperty("kladr_id")
    private String kladrId;

    @JsonProperty("geoname_id")
    private String geonameId;

    @JsonProperty("capital_marker")
    private String capitalMarker;

    @JsonProperty("okato")
    private String okato;

    @JsonProperty("oktmo")
    private String oktmo;

    @JsonProperty("tax_office")
    private String taxOffice;

    @JsonProperty("tax_office_legal")
    private String taxOfficeLegal;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("geo_lat")
    private String geoLat;

    @JsonProperty("geo_lon")
    private String geoLon;

    @JsonProperty("beltway_hit")
    private String beltwayHit;

    @JsonProperty("beltway_distance")
    private String beltwayDistance;

    @JsonProperty("metro")
    private String metro;

    @JsonProperty("qc_geo")
    private String qcGeo;

    @JsonProperty("qc_complete")
    private String qcComplete;

    @JsonProperty("qc_house")
    private String qcHouse;

    @JsonProperty("history_values")
    private String historyValues;

    @JsonProperty("unparsed_parts")
    private String unparsedParts;

    @JsonProperty("source")
    private String source;

    @JsonProperty("qc")
    private String qc;
}
