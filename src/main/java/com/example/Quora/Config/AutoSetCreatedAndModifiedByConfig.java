package com.example.Quora.Config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("autoSetCreatedAndModifiedByConfig")
public class AutoSetCreatedAndModifiedByConfig implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		// TODO Auto-generated method stub

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null || auth.getPrincipal().equals("anonymousUser")) {
			return Optional.of("SELF_REGISTERED");
		}

		return Optional.of(auth.getName());
	}

}
