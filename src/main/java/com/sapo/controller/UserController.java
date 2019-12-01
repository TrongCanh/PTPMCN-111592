package com.sapo.controller;

import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapo.config.JwtTokenUtil;
import com.sapo.dto.ForgotPassDTO;
import com.sapo.dto.JwtRequest;
import com.sapo.dto.JwtResponse;
import com.sapo.dto.UserRegisterDTO;
import com.sapo.model.User;
import com.sapo.repository.UserRepository;
import com.sapo.service.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
	@Autowired
	public JavaMailSender mail;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		if (userDetails == null)
			return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserRegisterDTO user) throws Exception {

		if (userRepository.findByEmail(user.getEmail()) != null)
			return (ResponseEntity<?>) ResponseEntity.badRequest().body("Email đã tồn tại");

		if (userRepository.findByUsername(user.getUsername()) != null)
			return (ResponseEntity<?>) ResponseEntity.badRequest().body("Username đã tồn tại");

		userDetailsService.save(user);
		authenticate(user.getUsername(), user.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));

	}

	@PutMapping("/updateInfor")
	public String updateInfor(@RequestBody UserRegisterDTO user, @RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}

		String username = jwtTokenUtil.getUsernameFromToken(token);
		User mUser = userRepository.findByUsername(username);
		if (user.getPhone() != null) {
			mUser.setPhone(user.getPhone());
		}

		if (user.getName() != null) {
			mUser.setName(user.getName());
		}
		if (user.getPassword() != null) {
			if (bcryptEncoder.matches(user.getPasswordConfirm(), mUser.getPassword())) {
				mUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			} else
				return "passwordConfirm không đúng";
		}
		userRepository.save(mUser);
		return "update thành công";

	}

	@GetMapping("/infor")
	public User userInfor(@RequestHeader("Authorization") String token) {
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = userRepository.findByUsername(username);
		return user;
	}

	@PutMapping("/forget")
	public String forgotPass(@RequestBody ForgotPassDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail());
		if (user != null) {
			Random rd = new Random();
			int newPass = rd.nextInt(10000000);
			SimpleMailMessage mess = new SimpleMailMessage();
			mess.setTo(user.getEmail());
			mess.setSubject("ResetPass");
			mess.setText(
					"Tài khoản: " + user.getUsername() + ". Mật khẩu mới của bạn là: " + String.valueOf(newPass));
			this.mail.send(mess);
			user.setPassword(bcryptEncoder.encode((String.valueOf(newPass))));
			userRepository.save(user);
			return "Đề nghị check mail";
		}
		return "mail lỗi";
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
