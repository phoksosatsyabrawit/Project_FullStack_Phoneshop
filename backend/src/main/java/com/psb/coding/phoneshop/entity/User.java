package com.psb.coding.phoneshop.entity;

import java.time.LocalDate;
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
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	private String firstName;
	private String LastName;
	private String username;
	private String password;
	private String email;
	private LocalDate dateOfBirth;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		    name = "users_roles",
		    joinColumns = @JoinColumn(name = "user_user_id"),
		    inverseJoinColumns = @JoinColumn(name = "roles_role_id")
		)
	private Set<Role> roles;
	private Boolean isAccountNonExpired;
	private Boolean isAccountNonLocked;
	private Boolean isCredentialsNonExpired;
	private Boolean isEnabled;
}
