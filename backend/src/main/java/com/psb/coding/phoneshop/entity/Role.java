package com.psb.coding.phoneshop.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long id;
	
	@Column(name = "role_name")
	private String role;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		    name = "roles_permissions",
		    joinColumns = @JoinColumn(name = "roles_role_id"),
		    inverseJoinColumns = @JoinColumn(name = "permissions_permission_id")
		)
	private Set<Permission> permissions;
}
