package com.ms.tourist_app.adapter.web.v1.controller;

import com.ms.tourist_app.adapter.web.base.ResponseUtil;
import com.ms.tourist_app.adapter.web.base.RestApiV1;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.user.AddFavoriteDestinationParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.user.UserDataParameter;
import com.ms.tourist_app.adapter.web.v1.transfer.parameter.user.GetAllUserParameter;
import com.ms.tourist_app.application.constants.UrlConst;
import com.ms.tourist_app.application.input.users.AddFavoriteDestinationInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.type_destination.DestinationTypeDataOutput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestApiV1
public class UserController {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(UrlConst.User.users)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDataParameter parameter) {
        UserDataInput input = userMapper.createUserInput(parameter);

        UserDataOutput output = userService.createUser(input);

        return ResponseUtil.restSuccess(output);
    }

    @GetMapping(UrlConst.User.users)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getAllUser(@Valid GetAllUserParameter parameter){
        GetListUserInput input = new GetListUserInput(parameter.getPage(), parameter.getSize(),parameter.getKeyword());
        List<UserDataOutput> output = userService.getListUserOutPut(input);
        return ResponseUtil.restSuccess(output);
    }

    @GetMapping(UrlConst.User.getUserById)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getUserData(@PathVariable(UrlConst.id)Long id){
        UserDataOutput userDataOutput = userService.getUserDataOutput(id);
        return ResponseUtil.restSuccess(userDataOutput);
    }

    @PutMapping(UrlConst.User.getUserById)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> updateUser(@PathVariable(UrlConst.id)Long id,@Valid @RequestBody UserDataParameter parameter){
        UserDataInput input = userMapper.createUserInput(parameter);
        UserDataOutput userDataOutput = userService.editUser(id,input);
        return ResponseUtil.restSuccess(userDataOutput);
    }

    @DeleteMapping(UrlConst.User.getUserById)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> deleteUser(@PathVariable(UrlConst.id)Long id){
        UserDataOutput userDataOutput = userService.deleteUser(id);
        return ResponseUtil.restSuccess(userDataOutput);
    }

    @PostMapping(UrlConst.User.addFavoriteDestination)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> addFavoriteDestination(@Valid AddFavoriteDestinationParameter parameter){
        AddFavoriteDestinationInput input = userMapper.toAddFavoriteDestinationInput(parameter);
        DestinationDataOutput output = userService.addFavoriteDestination(input);
        return ResponseUtil.restSuccess(output);
    }

    @GetMapping(UrlConst.User.viewFavoriteDestination)
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> viewFavoriteDestination(){
        List<DestinationDataOutput> output = userService.viewFavoriteDestinations();
        return ResponseUtil.restSuccess(output);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping(UrlConst.User.deleteFavoriteDestination)
    public ResponseEntity<?> deleteFavoriteDestination(@PathVariable(UrlConst.id) Long id) {
        DestinationDataOutput output = userService.deleteFavoriteDestination(id);
        return ResponseUtil.restSuccess(output);
    }
}


