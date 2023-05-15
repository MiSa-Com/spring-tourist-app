package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.destinations.*;
import com.ms.tourist_app.application.output.destinations.CommentDestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.destinations.GetListDestinationCenterRadiusOutput;
import com.ms.tourist_app.domain.entity.id.CommentDestinationId;

import java.util.List;

public interface DestinationService {
    DestinationDataOutput getDestinationDetail(Long id);
    List<DestinationDataOutput> getListDestinationByProvince(GetListDestinationByProvinceInput input);
    GetListDestinationCenterRadiusOutput getListDestinationCenterRadius(GetListDestinationCenterRadiusInput input);
    List<DestinationDataOutput> getListDestinationByKeyword(GetListDestinationByKeywordInput input);
    DestinationDataOutput createDestination(DestinationDataInput input);
    DestinationDataInput editDestination(DestinationDataInput input,Long id);
    DestinationDataOutput deleteDestination(Long id);
    CommentDestinationDataOutput createComment(Long idDestination, CommentDestinationDataInput input);
    CommentDestinationDataOutput editComment(CommentDestinationId commentDestinationId,CommentDestinationDataInput input);
    List<DestinationDataOutput> selectTopCreateAt(SelectTopCreateAtInput input);
}
