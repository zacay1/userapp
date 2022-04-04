package com.user.i1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.user.exceptions.ProcessExctions;
import com.user.i1.body.UserBody;
import com.user.pojo.response.FinalUserResponse;
import com.user.pojo.response.GenericResponse;
import com.user.pojo.response.ListUserResponse;
import com.user.service.UserService;
import com.user.utils.Messages;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(path = "/i1/users")
@SecurityRequirement(name = "Jwt_Token")
public class Users {

	@Autowired
	private UserService userService;

	@Operation(summary = Messages.TITLE_USER_CREATE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = Messages.DESCRIPTION_USER_CREATE, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FinalUserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "409", description = "Conflict", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@PostMapping()
	private ResponseEntity<GenericResponse> create(@Valid @RequestBody UserBody user) {
		try {
			return userService.create(user);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
			return ProcessExctions.generatErrorResponse(e);
		}

	}

	@Operation(summary = Messages.TITLE_USER_UPDATE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FinalUserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "409", description = "Conflict", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@PutMapping("/{id}")
	private ResponseEntity<GenericResponse> update(@PathVariable(name = "id") String userId,
			@Valid @RequestBody UserBody user) {
		try {
			return userService.update(user, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}

	}

	@Operation(summary = Messages.TITLE_USER_VIEW)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FinalUserResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad Request", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@GetMapping("/{id}")
	private ResponseEntity<GenericResponse> view(@PathVariable(name = "id") String userId) {
		try {
			return userService.view(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}
	}

	@Operation(summary = Messages.TITLE_USER_LISTE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ListUserResponse.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@GetMapping
	private ResponseEntity<GenericResponse> list() {
		try {
			return userService.list();
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}
	}

	@Operation(summary = Messages.TITLE_USER_DELETE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@DeleteMapping("/{id}")
	private ResponseEntity<GenericResponse> delete(@PathVariable(name = "id") String userId) {
		try {
			return userService.delete(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}
	}

	@Operation(summary = Messages.TITLE_USER_ACTIVE)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = Messages.TEXT_SUCCESSFUL_OPERATION, content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)) }),
			@ApiResponse(responseCode = "404", description = "Not Found", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class)) }) })
	@PatchMapping("/{id}/active")
	private ResponseEntity<GenericResponse> active(@PathVariable(name = "id") String userId,
			@RequestParam(name = "enable", required = true) Boolean enable) {
		try {
			return userService.active(userId, enable);
		} catch (Exception e) {
			e.printStackTrace();
			return ProcessExctions.generatErrorResponse(e);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public GenericResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
		String message = Messages.ERROR_UNDETERMINED_BAD_REQUEST;
		try {
			message = ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new GenericResponse(message);
	}

}
