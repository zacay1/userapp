package com.user.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.persistence.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
	AppUser findByEmail(String email);

}
