package com.demo.springgraphql.userroles.resolver;

import com.demo.springgraphql.userroles.model.Role;
import com.demo.springgraphql.userroles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.springgraphql.userroles.model.User;
import com.demo.springgraphql.userroles.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {
	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	public Query(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public User findUserById(long ID){
		return userRepository.findById(ID).orElseThrow(null);
	}

	public User findUserByUsername(String username){
		return userRepository.findByUsername(username).orElseThrow(null);
	}

	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	public Iterable<Role> findAllRoles(){
		return roleRepository.findAll();
	}

	public long countUsers() {
		return userRepository.count();
	}

	public long countRoles() {
		return roleRepository.count();
	}

}
