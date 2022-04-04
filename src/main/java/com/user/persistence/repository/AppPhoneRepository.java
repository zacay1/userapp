package com.user.persistence.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.persistence.entity.AppPhone;
import com.user.persistence.entity.AppUser;

public interface AppPhoneRepository extends JpaRepository<AppPhone, Long> {
	@Transactional
	void deleteAllByAppUserReference(AppUser appUser);

}
