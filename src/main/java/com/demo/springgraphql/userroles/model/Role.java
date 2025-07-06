package com.demo.springgraphql.userroles.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="roleName", nullable = false, unique = true)
	private String roleName;

	@Column(name="description")
	private String description;

	public Role(){}

	public Role(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;                            // Same instance
		if (o == null || getClass() != o.getClass()) return false; // Handles proxies safely

		Role role = (Role) o;

		if (id != null && role.id != null) {
			return id.equals(role.id);
		}

		return Objects.equals(roleName, role.roleName);
	}

	@Override
	public int hashCode() {
		return (id != null) ? Objects.hash(id) : Objects.hash(roleName);
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", roleName='" + roleName + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
