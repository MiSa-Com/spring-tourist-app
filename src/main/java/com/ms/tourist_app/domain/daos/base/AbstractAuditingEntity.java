package com.ms.tourist_app.domain.daos.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ms.tourist_app.application.constants.CommonConstant;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public abstract class AbstractAuditingEntity {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("createdBy")
    private Long createdBy;

    @JsonProperty("createdDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstant.DATE_FORMAT_YYYYMMDDHHMM)
    private String createdDate;

    @JsonProperty("lastModifiedBy")
    private Long lastModifiedBy;

    @JsonProperty("lastModifiedDate")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonConstant.DATE_FORMAT_YYYYMMDDHHMM)
    private String lastModifiedDate;

    @JsonProperty("status")
    private Boolean status;
}
