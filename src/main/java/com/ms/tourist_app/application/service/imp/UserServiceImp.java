package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.DestinationRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.AddFavoriteDestinationInput;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.input.users.GetListUserInput;
import com.ms.tourist_app.application.mapper.DestinationMapper;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.destinations.DestinationDataOutput;
import com.ms.tourist_app.application.output.users.UserDataOutput;
import com.ms.tourist_app.application.service.UserService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Destination;
import com.ms.tourist_app.domain.entity.User;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImp implements UserService {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final DestinationRepository destinationRepository;
    private final JwtUtil jwtUtil;
    private final DestinationMapper destinationMapper = Mappers.getMapper(DestinationMapper.class);

    public UserServiceImp(UserRepository userRepository, AddressRepository addressRepository,
                          DestinationRepository destinationRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.destinationRepository = destinationRepository;
        this.jwtUtil = jwtUtil;
    }


    private boolean checkUserEmailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;

    }

    private boolean checkUserTelephoneExists(String telephone) {
        User user = userRepository.findByTelephone(telephone);
        return user != null;
    }

    private boolean checkUserExists(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent();
    }

    @Override
    @Transactional
    public UserDataOutput createUser(UserDataInput input) {
        if (checkUserTelephoneExists(input.getTelephone())) {
            throw new BadRequestException(AppStr.User.telephone + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        if (checkUserEmailExists(input.getEmail())) {
            throw new BadRequestException(AppStr.User.email + AppStr.Base.whiteSpace + AppStr.Exception.duplicate);
        }
        User user = userMapper.toUser(input,null);
        Optional<Address> address = addressRepository.findById(input.getIdAddress());
        user.setAddress(address.get());
        userRepository.save(user);
        return new UserDataOutput(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(),address.get(), user.getTelephone(), user.getEmail(), user.getPassword());
    }
    @Override
    @Transactional
    public List<UserDataOutput> getListUserOutPut(GetListUserInput input) {

        List<User> users = new ArrayList<>();
        if(input.getKeyword().trim().isBlank()){
            users = userRepository.findAll(PageRequest.of(input.getPage(), input.getSize())).getContent();
        }
        if(!input.getKeyword().trim().isBlank()){
            users = userRepository.search(input.getKeyword().trim(),PageRequest.of(input.getPage(), input.getSize()));
        }
        List<UserDataOutput> userDataOutputs = new ArrayList<>();
        for (User u : users) {
            UserDataOutput userDataOutput = userMapper.toUserDataOutput(u);
            userDataOutput.setAddress(u.getAddress());
            userDataOutputs.add(userDataOutput);
        }
        return userDataOutputs;
    }

    @Override
    @Transactional
    public UserDataOutput getUserDataOutput(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!checkUserExists(id)) {
            throw new NotFoundException(AppStr.User.user + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        UserDataOutput userDataOutput = userMapper.toUserDataOutput(user.get());
        userDataOutput.setAddress(user.get().getAddress());
        return userDataOutput;
    }

    @Override
    @Transactional
    public UserDataOutput editUser(Long id, UserDataInput input) {
        if (!checkUserExists(id)) {
            throw new NotFoundException(AppStr.User.user + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        User user = userMapper.toUser(input,id);
        if (input.getIdAddress() != null) {
            Optional<Address> address = addressRepository.findById(input.getIdAddress());
            if (address.isEmpty()){
                throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
            }
            address.ifPresent(user::setAddress);
        }
        user.setId(id);

        userRepository.save(user);
        UserDataOutput userDataOutput = userMapper.toUserDataOutput(user);
        userDataOutput.setAddress(user.getAddress());
        return userDataOutput;
    }

    @Override
    @Transactional
    public UserDataOutput deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!checkUserExists(id)) {
            throw new NotFoundException(AppStr.User.user + AppStr.Base.whiteSpace + AppStr.Exception.notFound);
        }
        userRepository.delete(user.get());
        UserDataOutput userDataOutput = userMapper.toUserDataOutput(user.get());
        Address address = user.get().getAddress();
        userDataOutput.setAddress(address);
        return userDataOutput;
    }

    @Override
    @Transactional
    public DestinationDataOutput addFavoriteDestination(AddFavoriteDestinationInput input) {
        Long userId = jwtUtil.getUserIdFromToken();
        User user = userRepository.findById(userId).get();
        List<Destination> favoriteDest = user.getFavoriteDestination();
        Destination destination = destinationRepository.findById(input.getDestinationId()).get();
        if (favoriteDest.contains(destination)) {
            throw new BadRequestException(AppStr.User.userFavDestination);
        }
        List<Destination> newFavDestination = new ArrayList<>(favoriteDest);
        newFavDestination.add(destination);
        user.setFavoriteDestination(newFavDestination);
        userRepository.save(user);
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destination);
        return output;
    }

    @Override
    @Transactional
    public List<DestinationDataOutput> viewFavoriteDestinations() {
        Long userId = jwtUtil.getUserIdFromToken();
        User user = userRepository.findById(userId).get();
        List<Destination> listFavDests = user.getFavoriteDestination();
        List<DestinationDataOutput> outputs = new ArrayList<>();
        for (Destination dest : listFavDests) {
            DestinationDataOutput output = destinationMapper.toDestinationDataOutput(dest);
            outputs.add(output);
        }
        return outputs;
    }

    @Override
    @Transactional
    public DestinationDataOutput deleteFavoriteDestination(Long id) {
        Long userId = jwtUtil.getUserIdFromToken();
        User user = userRepository.findById(userId).get();
        List<Destination> listFavDests = user.getFavoriteDestination();
        Destination destinationToDelete = destinationRepository.findById(id).get();
        if (!listFavDests.contains(destinationToDelete)) {
            throw new BadRequestException(AppStr.User.notFoundFavoriteDestination);
        }
        List<Destination> newFavDestination = new ArrayList<>(listFavDests);
        newFavDestination.remove(destinationToDelete);
        user.setFavoriteDestination(newFavDestination);
        userRepository.save(user);
        DestinationDataOutput output = destinationMapper.toDestinationDataOutput(destinationToDelete);
        return output;
    }
}
