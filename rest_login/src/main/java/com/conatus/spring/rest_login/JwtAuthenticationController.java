package com.conatus.spring.rest_login;

import java.util.Objects;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
//import com.conatus.spring.rest_login.JwtUserDetailsService;


//import com.conatus.spring.rest_login.JwtTokenUtil;
//import com.conatus.spring.rest_login.JwtRequest;
//import com.conatus.spring.rest_login.JwtResponse;

import org.json.JSONObject;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/login/{access_token}", method=RequestMethod.GET)
	public String getAccessToken(@PathVariable String access_token) throws Exception {
		URL url = new URL("https://kapi.kakao.com/v2/user/me");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Authorization", "Bearer " + access_token);
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
			new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null)
			content.append(inputLine);
		in.close();
		con.disconnect();

		/*
		"https://kapi.kakao.com/v2/user/me" \
  -H "Authorization: Bearer {ACCESS_TOKEN}" \
  -d 'property_keys=["kakao_account.email"]'
  */
		JSONObject jObject = new JSONObject(content.toString());
		// TODO: only return the accont_email. 
		return (jObject.toString());
	}

	@RequestMapping(value="/login/{access_token}", method=RequestMethod.POST)
	public ResponseEntity<?> postAccessToken(@RequestBody UserDTO user, @PathVariable String access_token) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}