package com.techleads.app.controller;

import com.techleads.app.UserRequestBody;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.service.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {


    @Autowired
    private UsersService usersService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping(value = {"/users"})
    public ResponseEntity<UsersDTO> saveUser(@RequestBody @Valid UserRequestBody user) {
        UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);
        UsersDTO users = usersService.saveUser(userDTO);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @GetMapping(value = {"/users/{email}"})
    public ResponseEntity<UsersDTO> findUserByEmail(@PathVariable("email") String email) {
        UsersDTO user = usersService.findUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = {"/users"})
    public ResponseEntity<List<UsersDTO>> findAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "2") int pageSize) {
        List<UsersDTO> users = usersService.findAllUsers(pageNumber,pageSize);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



}
