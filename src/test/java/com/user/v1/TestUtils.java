package com.user.v1;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import com.user.persistence.entity.AppPhone;
import com.user.persistence.entity.AppUser;

public class TestUtils {
	public static AppUser createUser(String reference, String uId) {
		AppUser appUser = new AppUser();
		appUser.setActive(true);
		appUser.setCreated(Instant.now());
		appUser.setEmail(reference + "juan@rodriguez.org");
		appUser.setLastLogin(Instant.now());
		appUser.setModified(Instant.now());
		appUser.setName(reference + "Juan Rodriguez");
		appUser.setPassword(reference + "hunter2*");
		appUser.setToken(
				"yJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtYW51ZWxAdGVzdC5jbyIsImNyZWF0aW9uRGF0ZSI6MTY0ODk1NDQyMzAwNCwiZXhwIjoxNjQ4OTU0NDMzLCJpYXQiOjE2NDg5NTQ0MjN9.l7ZJhSup669t07YwMzhaK3QvHzyg15BU_isI_oTPFh-4gKEnzq0NLcFa5VBiaUfjCX2ltwzEBPB9vw-teEcQog");
		AppPhone phone = new AppPhone();
		phone.setCitycode("1");
		phone.setContrycode("57");
		phone.setNumber("1234567");
		appUser.setPhones(new ArrayList<>(Arrays.asList(phone)));
		if (uId != null)
			appUser.setId(UUID.fromString(uId));
		return appUser;
	}

	public static AppUser createUser(String reference) {
		return createUser(reference, null);
	}
}
