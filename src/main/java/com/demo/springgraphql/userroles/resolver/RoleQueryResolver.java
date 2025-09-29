package com.demo.springgraphql.userroles.resolver;

import com.demo.springgraphql.userroles.model.Role;
import com.demo.springgraphql.userroles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RoleQueryResolver {
	private final RoleRepository roleRepository;

	@Autowired
	public RoleQueryResolver(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@QueryMapping
	public List<Role> findAllRoles(@Argument int page, @Argument int size){
		Pageable pageable = PageRequest.of(page, size);
		Page<Role> rolePage = roleRepository.findAll(pageable);
		return rolePage.getContent();
	}

	@QueryMapping
	public long countRoles() {
		return roleRepository.count();
	}
}