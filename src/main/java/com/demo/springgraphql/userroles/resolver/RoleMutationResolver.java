package com.demo.springgraphql.userroles.resolver;

import com.demo.springgraphql.userroles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.demo.springgraphql.userroles.model.Role;

@Controller
public class RoleMutationResolver {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleMutationResolver(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@MutationMapping
	public Role createRole(@Argument String roleName, @Argument String description){
		Role role = new Role();
		role.setRoleName(roleName);
		role.setDescription(description);

		roleRepository.save(role);
		return role;
	}
}