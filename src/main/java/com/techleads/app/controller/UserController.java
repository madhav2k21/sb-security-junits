package com.techleads.app.controller;

import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import com.techleads.app.repository.UsersRepository;
import com.techleads.app.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PagedResourcesAssembler<Users> pagedResourcesAssembler;

    @Autowired
    private UsersDTOAssembler usersDTOAssembler;

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

//    @GetMapping(value = {"/users"})
//    public ResponseEntity<List<Users>> findUserByEmail(@RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
//                                                       @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {
//        List<Users> users = usersService.findAllUsersByPageNumberAndPageSize(pageNumber, pageSize);
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }


//    @GetMapping(value = "/v2/users")
//    public CollectionModel<EntityModel<Users>> findAllUsersHateoas() {
//
//        List<EntityModel<Users>> users = userService.findAllUsers().stream()
//                .map(user -> EntityModel.of(user,
//                        linkTo(methodOn(UserController.class).findUserByIdHateoas(user.getId())).withSelfRel(),
//                        linkTo(methodOn(UserController.class).findAllUsersHateoas()).withRel("v2/users")))
//                .collect(Collectors.toList());
//        return CollectionModel.of(users, linkTo(methodOn(UserController.class).findAllUsersHateoas()).withSelfRel());

    // PagedResourcesAssembler easily convert (Page) instances into (PagedResources)


    @GetMapping("/api/users-list")
    public ResponseEntity<PagedModel<UsersDTO>> getAllAlbums(Pageable pageable)
    {
        Page<Users> userEntity = usersRepository.findAll(pageable);

        PagedModel<UsersDTO> collModel = pagedResourcesAssembler
                .toModel(userEntity, usersDTOAssembler);

        return new ResponseEntity<>(collModel,HttpStatus.OK);
    }

    @GetMapping("/api/users")
    public ResponseEntity<CollectionModel<UsersDTO>> getAllUsers()
    {
        List<Users> userEntities = usersRepository.findAll();

        return new ResponseEntity<>(
                usersDTOAssembler.toCollectionModel(userEntities),
                HttpStatus.OK);
    }




}
