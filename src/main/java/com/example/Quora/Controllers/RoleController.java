package com.example.Quora.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Quora.DTO.RoleRequestDto;
import com.example.Quora.Entities.Role;
import com.example.Quora.Exceptions.DuplicateEntryException;
import com.example.Quora.Exceptions.ResourceNotFoundException;
import com.example.Quora.Services.RoleService;
import com.example.Quora.Utils.CommonUtils;
import com.example.Quora.Utils.JsonResponseEntity;
import com.example.Quora.Utils.StringConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/role")
@Tag(name = "7. Role APIs", description = "APIs for managing roles")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@PostMapping("/newRole")
	@Operation(summary = "Create Role", description = "Creates a new role in the system")
	public JsonResponseEntity<Role> createNewRole(@RequestBody final RoleRequestDto role)
			throws DuplicateEntryException {

		final Role newRole = roleService.create(role);

		JsonResponseEntity<Role> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(newRole)) {
			response.setStatus(StringConstants.success);
			response.setResult(newRole);
			response.setException(null);
			response.setMessage(StringConstants.roleCreatedMessage);
			response.setStatusCode(HttpStatus.CREATED);
		}
		return response;
	}

	@GetMapping("/{roleId}")
	@Operation(summary = "Get Role", description = "Retrieves role details based on the provided role ID")
	public JsonResponseEntity<Role> getRoleById(@PathVariable final int roleId) throws ResourceNotFoundException {
		final Role role = roleService.getRoleById(roleId);

		JsonResponseEntity<Role> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(role)) {
			response.setStatus(StringConstants.success);
			response.setResult(role);
			response.setException(null);
			response.setMessage(StringConstants.deatilsFetchedMessage);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@GetMapping("fetchAllRoles")
	@Operation(summary = "Get All Roles", description = "Retrieves a list of all roles available in the system")
	public JsonResponseEntity<List<Role>> getAllRoles() throws ResourceNotFoundException {
		final List<Role> role = roleService.getAllRoles();

		JsonResponseEntity<List<Role>> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(role)) {
			response.setStatus(StringConstants.success);
			response.setResult(role);
			response.setException(null);
			response.setMessage(StringConstants.deatilsFetchedMessage);
			response.setStatusCode(HttpStatus.OK);
		}

		return response;
	}

	@DeleteMapping("/delete/{roleId}")
	@Operation(summary = "Delete Role", description = "Deletes a role based on the provided role ID")
	public JsonResponseEntity<Role> deleteRole(@PathVariable final int roleId) throws ResourceNotFoundException {
		final Role role = roleService.deleteRole(roleId);

		JsonResponseEntity<Role> response = new JsonResponseEntity<>();

		if (CommonUtils.isValidObject(role)) {
			response.setStatus(StringConstants.success);
			response.setResult(role);
			response.setException(null);
			response.setMessage(StringConstants.RoleDeletedMessage);
			response.setStatusCode(HttpStatus.OK);
		} else {
			response.setStatus(StringConstants.failed);
			response.setResult(null);
			response.setException(null);
			response.setMessage(StringConstants.failedRequestProcessingMessage);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}
}
