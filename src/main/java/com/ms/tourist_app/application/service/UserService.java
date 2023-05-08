package com.ms.tourist_app.application.service;

import com.ms.tourist_app.application.input.users.AddFavoriteDestinationInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.users.UserDataOutput;

import java.util.List;

public interface UserService {
    UserDataOutput createUser(UserDataInput input);

    List<UserDataOutput> getListUserOutPut(GetListUserInput input);

    UserDataOutput getUserDataOutput(Long id);

    UserDataOutput editUser(Long id, UserDataInput input);

    UserDataOutput deleteUser(Long id);

    DestinationDataOutput addFavoriteDestination(AddFavoriteDestinationInput input);

    List<DestinationDataOutput> viewFavoriteDestinations();

    DestinationDataOutput deleteFavoriteDestination(Long id);
}