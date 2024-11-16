package com.backend.app.userservice.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.backend.app.userservice.user.models.SignInRequest;
import com.backend.app.userservice.user.models.SignUpRequest;
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

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signin")
  public ResponseEntity<BaseResponse<String>> userSignIn(@RequestBody SignInRequest request) {
    try {

      BaseResponse<String> response = userService.signIn(request);

      if (response.getCode() == 4001) {
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

      BaseResponse<String> response = userService.createUserFromSignUp(request);

      if (response.getCode() != 2001) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      }
      return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
