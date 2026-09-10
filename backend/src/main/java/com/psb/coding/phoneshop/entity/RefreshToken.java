package com.psb.coding.phoneshop.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "refresh_tokens")
@Data
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refresh_token_id")
	private Long id;
	
	@Column(name = "token_hash", nullable = false, unique = true)
	private String tokenHash;
	
	@Column(name= "expires_at", nullable = false)
	private Instant expiresAt;
	
	@Column(name = "is_revoked", nullable = false)
	private boolean isRevoked;
	
	@Column(name = "created_at", nullable = false)
	private Instant createdAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
