package com.demo.springgraphql.userroles.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.demo.springgraphql.userroles.model.User;
import com.demo.springgraphql.userroles.repository.UserRepository;

import java.util.List;

@Controller
public class UserQueryResolver {
	private final UserRepository userRepository;

	@Autowired
	public UserQueryResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@QueryMapping
	public User findUserById(@Argument Long id){
		return userRepository.findById(id).orElse(null);
	}

	@QueryMapping
	public User findUserByUsername(@Argument String username){
		return userRepository.findByUsername(username).orElse(null);
	}

	@QueryMapping
	public List<User> findAllUsers(@Argument int page, @Argument int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<User> userPage = userRepository.findAll(pageable);
		return userPage.getContent();
	}

	@QueryMapping
	public long countUsers() {
		return userRepository.count();
	}
}