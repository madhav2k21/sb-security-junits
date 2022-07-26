package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping(value = {"/users"})
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        Users users = usersService.saveUser(user);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/users/{email}"})
    public ResponseEntity<Users> findUserByEmail(@PathVariable("email") String email) {
        Users users = usersService.findUserByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = {"/users"})
    public ResponseEntity<List<Users>> findUserByEmail(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {
        List<Users> users = usersService.findAllUsersByPageNumberAndPageSize(pageNumber, pageSize);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
