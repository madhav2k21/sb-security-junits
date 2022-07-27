package com.techleads.app.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.techleads.app.model.Users;
import com.techleads.app.model.UsersDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsersDTOAssembler
		extends RepresentationModelAssemblerSupport<Users, UsersDTO> {


	public UsersDTOAssembler() {
		super(UserController.class, UsersDTO.class);
	}

	@Override
	public UsersDTO toModel(Users entity) {
		UsersDTO usersDTO = instantiateModel(entity);

		usersDTO.add(linkTo(
				methodOn(UserController.class)
						.findUserByEmail(entity.getEmail()))
				.withSelfRel());

		usersDTO.setId(entity.getId());
		usersDTO.setFirstName(entity.getFirstName());
		usersDTO.setLastName(entity.getLastName());
		usersDTO.setUserId(entity.getUserId());
		usersDTO.setEmail(entity.getEmail());
		usersDTO.setEncryptedPassword(entity.getEncryptedPassword());
		return usersDTO;
	}


	@Override
	public CollectionModel<UsersDTO> toCollectionModel(Iterable<? extends Users> entities) {
		CollectionModel<UsersDTO> usersDTOS = super.toCollectionModel(entities);

		usersDTOS.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());

		return usersDTOS;
	}

	private List<UsersDTO> toUsersDTO(List<Users> userEntity) {
		if (userEntity.isEmpty())
			return Collections.emptyList();

		return userEntity.stream()
				.map(user -> UsersDTO.builder()
						.id(user.getId())
						.firstName(user.getFirstName())
						.lastName(user.getLastName())
						.userId(user.getUserId())
						.email(user.getEmail())
						.encryptedPassword(user.getEncryptedPassword())
						.build()
						.add(linkTo(
								methodOn(UserController.class)
										.findUserByEmail(user.getEmail()))
								.withSelfRel()))
				.collect(Collectors.toList());
	}
}
