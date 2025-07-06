package com.demo.springgraphql.userroles.resolver;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.demo.springgraphql.userroles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.springgraphql.userroles.model.User;
import com.demo.springgraphql.userroles.model.Role;
import com.demo.springgraphql.userroles.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import javassist.NotFoundException;

@Component
public class Mutation implements GraphQLMutationResolver {
	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Autowired
	public Mutation(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public User createUser(String username, String email, String firstName, String lastName, List<Long> roleIds) throws NotFoundException {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		Set<Role> roles = new HashSet<>();

		for(Long roleId : roleIds){
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			if(optionalRole.isPresent()){
				roles.add(optionalRole.get());
			} else {
				throw new NotFoundException("Role with ID '" + roleId +"' Not Found!");
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		return user;
	}

	public User addUserRole(Long id, List<Long> roleIds) throws NotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()){
			throw new NotFoundException("User with ID '" + id +"' Not Found!");
		}

		User user = optionalUser.get();
		Set<Role> roles = user.getRoles();
		if(null == roles){
			roles = new HashSet<>();
		}

		for(Long roleId : roleIds){
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			if(optionalRole.isPresent()){
				roles.add(optionalRole.get());
			} else {
				throw new NotFoundException("Role with ID '" + roleId +"' Not Found!");
			}
		}

		user.setRoles(roles);
		userRepository.save(user);
		return user;
	}

	public User removeUserRole(Long id, List<Long> roleIds) throws NotFoundException {
		System.out.println("Inside removeUserRole");
		System.out.println(roleIds);
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()){
			throw new NotFoundException("User with ID '" + id +"' Not Found!");
		}

		User user = optionalUser.get();
		System.out.println("User Found :" + user.toString());


		for(Long roleId : roleIds){
			Optional<Role> optionalRole = roleRepository.findById(roleId);
			System.out.println("Role found of : " + roleId + " : " + optionalRole.isPresent());
			if(optionalRole.isPresent()){
				System.out.println("Role found of : " + roleId + " : " + optionalRole.get().toString());
				user.removeRole(optionalRole.get());
			} else {
				throw new NotFoundException("Role with ID '" + roleId +"' Not Found!");
			}
		}

		userRepository.save(user);
		return user;
	}

	public boolean deleteUser(Long id) {
		if(!userRepository.existsById(id)){
			return false;
		}
		userRepository.deleteById(id);
		return true;
	}


	public Role createRole(String roleName, String description){
		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);

		roleRepository.save(role);
		return role;
	}

}
