package com.backend.app.userservice.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.shared.error.ErrorMessage;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.validator.PatternMatch;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;
import com.backend.app.userservice.user.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

interface IUserController {
  ResponseEntity<BaseResponse<String>> userSignIn(SignInRequest signInRequest);

  ResponseEntity<BaseResponse<String>> userSignUp(SignUpRequest signUpRequest);
}

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UserController implements IUserController {

  private final UserService userService;

  public UserController(
      UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signin")
  public ResponseEntity<BaseResponse<String>> userSignIn(@RequestBody SignInRequest request) {
    try {

      BaseResponse<String> response = userService.signIn(request);

      if (response.getCode().equals(4001)) {
        return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(response);
      }
      if (response.getCode() != 2001) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      }

      return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<BaseResponse<String>> userSignUp(@RequestBody SignUpRequest request) {
    try {
      // if (request.getUsername() == null || request.getUsername().isEmpty()) {
      // return ResponseEntity
      // .status(HttpServletResponse.SC_BAD_REQUEST)
      // .body(new BaseResponse<String>(4000, "Username is required", null));
      // }
      // if (request.getEmail() == null || request.getEmail().isEmpty()) {
      // return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
      // .body(new BaseResponse<String>(4000, "Email is required", null));
      // }
      // if (PatternMatch.isEmailValid(request.getEmail()).equals(false)) {
      // return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
      // .body(new BaseResponse<String>(4000, "Email is not valid", null));
      // }
      // if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
      // return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
      // .body(new BaseResponse<String>(4000, "Firstname is required", null));
      // }

      // if (request.getPassword() == null || request.getPassword().isEmpty()) {
      // return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
      // .body(new BaseResponse<String>(4000, "Password is required", null));
      // }
      // BaseResponse<String> response = userService.createUserFromSignUp(request);

      // if (response.getCode() != 2001) {
      // return
      // ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      // }
      // return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
      return null;
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
