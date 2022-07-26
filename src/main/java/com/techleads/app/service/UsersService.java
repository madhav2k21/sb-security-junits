package com.techleads.app.service;

import com.techleads.app.exception.UserNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;


    public Users saveUser(Users user) {
        user.setUserId(UUID.randomUUID().toString());
        Users savedUser = usersRepository.save(user);
        return savedUser;
    }

    public List<Users> findAllUsersByPageNumberAndPageSize(int pageNumber, int pageSize) {

        Pageable pageableRequest = PageRequest.of(pageNumber, pageSize);
        Page<Users> usersPage = usersRepository.findAll(pageableRequest);
        if (Objects.isNull(usersPage)) {
            throw new UserNotFoundException("Users are not available");
        }
        return usersPage.getContent();

    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
