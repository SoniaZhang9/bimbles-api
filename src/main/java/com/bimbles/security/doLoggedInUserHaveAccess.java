package com.bimbles.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class doLoggedInUserHaveAccess {
	
	public static void check ( Long resourceId ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		CustomUserDetails currentLoggedInUser = (CustomUserDetails) authentication.getPrincipal();

		boolean isAdmin = currentLoggedInUser.getAuthorities().get(0).toString().equals("ADMIN");

		if (!isAdmin) {
			Long currentUserId = currentLoggedInUser.getId();

			if ( ! currentUserId.equals(resourceId) ) {
				throw new AccessDeniedException("Acceso denegado");
			}
		}

	}
}
