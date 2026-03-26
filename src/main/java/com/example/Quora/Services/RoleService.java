package com.example.Quora.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Quora.Entities.Role;
import com.example.Quora.Exceptions.InvalidInputException;
import com.example.Quora.Exceptions.ResourceNotFoundException;
import com.example.Quora.Repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	/*
	 * public Role create(final RoleRequestDto role) throws DuplicateEntryException
	 * { Optional<Role> existingRole = roleRepository.findByRole(role.getRole());
	 * 
	 * if (existingRole.isPresent()) { throw new
	 * DuplicateEntryException("Role Already exists."); }
	 * 
	 * Role newRole = Role.builder().role(role.getRole()).build();
	 * 
	 * return roleRepository.save(newRole); }
	 */

	public Role getRoleById(final int roleId) throws ResourceNotFoundException, InvalidInputException {

		if (roleId <= 0) {
			throw new InvalidInputException("Invalid Role Id.");
		}

		final Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not Found."));

		return role;
	}

	public List<Role> getAllRoles() throws ResourceNotFoundException {

		final List<Role> roles = roleRepository.findAll();

		if (roles == null || roles.size() <= 0) {
			throw new ResourceNotFoundException("Role not Found.");
		}

		return roles;
	}

	/*
	 * public Role deleteRole(final int roleId) throws ResourceNotFoundException,
	 * InvalidInputException { final Role role = getRoleById(roleId);
	 * 
	 * roleRepository.deleteById(roleId); return role; }
	 */
}
