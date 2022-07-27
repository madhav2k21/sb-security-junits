package com.techleads.app.service;

import com.techleads.app.exception.DuplicateEmailIdException;
import com.techleads.app.exception.UserNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper modelMapper;


    public UsersDTO saveUser(UsersDTO user) {
        usersRepository.findByEmail(user.getEmail()).ifPresent(ex-> {
            throw new DuplicateEmailIdException("Duplicate email id");
        });

        user.setUserId(UUID.randomUUID().toString());
        Users userEntity = modelMapper.map(user, Users.class);
        userEntity.setEncryptedPassword(user.getPassword());
        Users savedUser = usersRepository.save(userEntity);

        UsersDTO usersDTO = modelMapper.map(savedUser, UsersDTO.class);
        return usersDTO;
    }

    public List<Users> findAllUsersByPageNumberAndPageSize(int pageNumber, int pageSize) {

        Pageable pageableRequest = PageRequest.of(pageNumber, pageSize);
        Page<Users> usersPage = usersRepository.findAll(pageableRequest);
        if (Objects.isNull(usersPage)) {

            throw new UserNotFoundException("Users are not available");
        }
        return usersPage.getContent();

    }

    public UsersDTO findUserByEmail(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        UsersDTO usersDTO = modelMapper.map(user, UsersDTO.class);
        return usersDTO;
    }

    public List<UsersDTO> findAllUsers() {
//        List<UsersDTO> usersDTOs
//                = usersRepository.findAll().stream().map(user -> modelMapper.map(user, UsersDTO.class)).collect(Collectors.toList());

        List<Users> userEntities = usersRepository.findAll();
        Type listType = new TypeToken<List<UsersDTO>>() {}.getType();
        List<UsersDTO> usersDTOs = modelMapper.map(userEntities, listType);
        return usersDTOs;
    }

    public List<UsersDTO> findAllUsers(int pageNumber, int pageSize) {
        if (pageNumber > 0) {
            pageNumber -= 1;
        }
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<Users> userPage = usersRepository.findAll(pageRequest);
        if (CollectionUtils.isEmpty(userPage.getContent())) {
            throw new UserNotFoundException("Users not found for given page number");
        }
        Type listType = new TypeToken<List<UsersDTO>>() {}.getType();
        List<UsersDTO> usersDTOs = modelMapper.map(userPage.getContent(), listType);

//        List<UsersDTO> usersDTOs
//                = userPage.getContent().stream().map(user -> modelMapper.map(user, UsersDTO.class)).collect(Collectors.toList());

        return usersDTOs;
    }
}
