package com.ms.tourist_app.application.service.imp;

import com.ms.tourist_app.adapter.web.v1.transfer.parameter.auth.AuthenticationRequest;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.application.dai.AddressRepository;
import com.ms.tourist_app.application.dai.RoleRepository;
import com.ms.tourist_app.application.dai.UserRepository;
import com.ms.tourist_app.application.input.users.UserDataInput;
import com.ms.tourist_app.application.mapper.UserMapper;
import com.ms.tourist_app.application.output.auth.AuthenticationResponse;
import com.ms.tourist_app.application.service.AuthService;
import com.ms.tourist_app.application.service.imp.security.MyUserDetailService;
import com.ms.tourist_app.application.utils.JwtUtil;
import com.ms.tourist_app.config.exception.BadRequestException;
import com.ms.tourist_app.config.exception.NotFoundException;
import com.ms.tourist_app.domain.entity.Address;
import com.ms.tourist_app.domain.entity.Role;
import com.ms.tourist_app.domain.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImp implements AuthService {
    private final JwtUtil jwtUtil;

    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    private final MyUserDetailService myUserDetailService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public AuthServiceImp(JwtUtil jwtUtil, AddressRepository addressRepository, UserMapper userMapper, MyUserDetailService myUserDetailService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.jwtUtil = jwtUtil;
        this.addressRepository = addressRepository;
        this.userMapper = userMapper;
        this.myUserDetailService = myUserDetailService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadRequestException(AppStr.Auth.inCorrectLogin);
        }
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByEmail(authenticationRequest.getEmail());
        List<String> roles = new ArrayList<>();
        List<Role> roleSet = user.getRoles();
        if (roleSet.size() > 0) {
            roleSet.forEach(item -> roles.add(item.getName()));
        }
        return new AuthenticationResponse(jwt, user.getId(), user.getEmail(), roles);
    }

    @Override
    @Transactional
    public AuthenticationResponse signupUser(UserDataInput userDataInput) {
        //Tạo mới user, set các thuộc tính cơ bản cho user
        User oldUser = userRepository.findByEmail(userDataInput.getEmail());
        if (oldUser != null) {
            throw new BadRequestException(AppStr.Auth.emailDuplicate);
        }
        User user = userMapper.toUser(userDataInput,null);
        if (userDataInput.getIdAddress() != null) {
            Optional<Address> address = addressRepository.findById(userDataInput.getIdAddress());
            if (address.isEmpty()){
                throw new NotFoundException(AppStr.Address.address+AppStr.Base.whiteSpace+AppStr.Exception.notFound);
            }
            address.ifPresent(user::setAddress);
        }

        //Set password cho user
        user.setPassword(passwordEncoder.encode(userDataInput.getPassword()));
        userRepository.save(user);

        //Set role cho user
        Role role = roleRepository.findByName(AppStr.Role.userRole);
        user.setRoles(List.of(role));
        List<User> users = role.getUsers();
        users.add(user);
        role.setUsers(users);
        roleRepository.save(role);
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt, user.getId(), user.getEmail(), List.of(role.getName()));
    }

    @Override
    @Transactional
    public AuthenticationResponse signupAdmin(UserDataInput userDataInput) {
        User oldUser = userRepository.findByEmail(userDataInput.getEmail());
        if (oldUser != null) {
            throw new BadRequestException(AppStr.Auth.emailDuplicate);
        }
        User user = userMapper.toUser(userDataInput,null);

        user.setPassword(passwordEncoder.encode(userDataInput.getPassword()));
        Role role = roleRepository.findByName(AppStr.Role.adminRole);
        user.setRoles(List.of(role));
        userRepository.save(user);
        List<User> users = role.getUsers();
        users.add(user);
        role.setUsers(users);
        roleRepository.save(role);
        final UserDetails userDetails = myUserDetailService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return new AuthenticationResponse(jwt, user.getId(), user.getEmail(), List.of(role.getName()));
    }

    @Override
    @Transactional
    public AuthenticationResponse validateToken(AuthenticationResponse authenticationResponse) throws InvalidObjectException {
        try {
            String jwt = authenticationResponse.getJwt();
            String username = jwtUtil.extractUsername(jwt);
            UserDetails userDetails = myUserDetailService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            User user = userRepository.findByEmail(username);
            List<Role> roles = user.getRoles();
            return new AuthenticationResponse(
                    jwtUtil.generateToken(userDetails),
                    user.getId(),
                    user.getEmail(),
                    roles.stream().map(Role::getName).collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

}
