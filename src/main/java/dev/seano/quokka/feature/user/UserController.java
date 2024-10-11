package dev.seano.quokka.feature.user;

import dev.seano.quokka.feature.user.res.UserResponse;
import dev.seano.quokka.res.PagedResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<PagedResponse<UserResponse>> getUsers(@RequestParam(required = false, defaultValue = "1") int page,
																@RequestParam(required = false, defaultValue = "10") int size) {
		var pageRequest = PageRequest.of(page - 1, size, Sort.by("created"));
		return ResponseEntity.ok(new PagedResponse<>(userService.findAllPageable(pageRequest)));
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
		return ResponseEntity.ok(new UserResponse(userService.findByUsername(username)));
	}
}
