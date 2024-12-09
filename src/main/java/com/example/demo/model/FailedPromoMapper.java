package com.example.demo.model;

import com.example.demo.form.FailedPromoCreationForm;
import com.example.demo.protos.FailedPromo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FailedPromoMapper {
    FailedPromoMapper INSTANCE = Mappers.getMapper(FailedPromoMapper.class);

    @Mapping(source = "errorType", target = "errorType")
    @Mapping(source = "errorMessage", target = "errorMessage")
    @Mapping(source = "promocodeId", target = "promocodeId")
    @Mapping(source = "promocodeCode", target = "promocodeCode")
    @Mapping(source = "promotionId", target = "promotionId")
    @Mapping(source = "orderUuid", target = "orderUuid")
    @Mapping(source = "userUuid", target = "userUuid")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FailedPromoRecord failedPromoCreationFormToFailedPromoRecord(FailedPromoCreationForm form);

    @Mapping(source = "error.type", target = "errorType")
    @Mapping(source = "error.message", target = "errorMessage")
    @Mapping(source = "promocodeId", target = "promocodeId")
    @Mapping(source = "promocodeCode", target = "promocodeCode")
    @Mapping(source = "promotionId", target = "promotionId")
    @Mapping(source = "orderUuid", target = "orderUuid")
    @Mapping(source = "userUuid", target = "userUuid")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FailedPromoRecord failedPromoProtoToFailedPromoRecord(FailedPromo message);
}
