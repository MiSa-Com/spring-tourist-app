package com.ms.tourist_app.application.mapper;


import com.ms.tourist_app.adapter.web.v1.transfer.parameter.user.AddFavoriteDestinationParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.user.UserDataParameter;
import com.ms.tourist_app.application.input.users.AddFavoriteDestinationInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(target = "firstName",source = "input.firstName"),
            @Mapping(target = "lastName",source = "input.lastName"),
            @Mapping(target = "dateOfBirth",source = "input.dateOfBirth"),
            @Mapping(target = "telephone",source = "input.telephone"),
            @Mapping(target = "email",source = "input.email"),
            @Mapping(target = "id",source = "id")
    })
    User toUser(UserDataInput input, Long id);


    UserDataInput createUserInput(UserDataParameter parameter);


    @Mappings({
            @Mapping(target = "id",source = "id"),
            @Mapping(target = "firstName",source = "firstName"),
            @Mapping(target = "lastName",source = "lastName"),
            @Mapping(target = "dateOfBirth",source = "dateOfBirth"),
            @Mapping(target = "telephone",source = "telephone"),
            @Mapping(target = "email",source = "email"),
    })
    UserDataOutput toUserDataOutput(User user);

    @Mappings({
            @Mapping(target = "destinationId", source = "destinationId")
    })
    AddFavoriteDestinationInput toAddFavoriteDestinationInput(AddFavoriteDestinationParameter parameter);
}
