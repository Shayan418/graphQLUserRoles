package com.demo.springgraphql.userroles.resolver;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.demo.springgraphql.userroles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.demo.springgraphql.userroles.model.User;
import com.demo.springgraphql.userroles.model.Role;
import com.demo.springgraphql.userroles.repository.UserRepository;

@Controller
public class UserMutationResolver {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserMutationResolver(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@MutationMapping
	public User createUser(@Argument String username, @Argument String email, 
						   @Argument String firstName, @Argument String lastName, 
						   @Argument List<Long> roleIds) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		Set<Role> roles = new HashSet<>();

		if (roleIds != null) {
			for(Long roleId : roleIds){
				Optional<Role> optionalRole = roleRepository.findById(roleId);
				if(optionalRole.isPresent()){
					roles.add(optionalRole.get());
				} else {
					throw new RuntimeException("Role with ID '" + roleId +"' Not Found!");
				}
			}
		}

		user.setRoles(roles);
		userRepository.save(user);

		return user;
	}

	@MutationMapping
	public User addUserRole(@Argument Long id, @Argument List<Long> roleIds) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()){
			throw new RuntimeException("User with ID '" + id +"' Not Found!");
		}

		User user = optionalUser.get();
		Set<Role> roles = user.getRoles();
		if(null == roles){
			roles = new HashSet<>();
		}

		if (roleIds != null) {
			for(Long roleId : roleIds){
				Optional<Role> optionalRole = roleRepository.findById(roleId);
				if(optionalRole.isPresent()){
					roles.add(optionalRole.get());
				} else {
					throw new RuntimeException("Role with ID '" + roleId +"' Not Found!");
				}
			}
		}

		user.setRoles(roles);
		userRepository.save(user);
		return user;
	}

	@MutationMapping
	public User removeUserRole(@Argument Long id, @Argument List<Long> roleIds) {
		System.out.println("Inside removeUserRole");
		System.out.println(roleIds);
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isEmpty()){
			throw new RuntimeException("User with ID '" + id +"' Not Found!");
		}

		User user = optionalUser.get();
		System.out.println("User Found :" + user.toString());

		if (roleIds != null) {
			for(Long roleId : roleIds){
				Optional<Role> optionalRole = roleRepository.findById(roleId);
				System.out.println("Role found of : " + roleId + " : " + optionalRole.isPresent());
				if(optionalRole.isPresent()){
					System.out.println("Role found of : " + roleId + " : " + optionalRole.get().toString());
					user.removeRole(optionalRole.get());
				} else {
					throw new RuntimeException("Role with ID '" + roleId +"' Not Found!");
				}
			}
		}

		userRepository.save(user);
		return user;
	}

	@MutationMapping
	public boolean deleteUser(@Argument Long id) {
		if(!userRepository.existsById(id)){
			return false;
		}
		userRepository.deleteById(id);
		return true;
	}
}