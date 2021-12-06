package com.swan.picturerepository.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

@Component
public class CsrfRequestMatcher implements RequestMatcher {
	private Pattern defaultAllowPattern = Pattern.compile("^(GET|TRACE|HEAD|OPTIONS)$");//CSRF Token을 사용하지 않도록 하는 기본 HTTP Method 설정
	private RegexRequestMatcher csrfDisabledMatcher = new RegexRequestMatcher("/RegisterKey|/upload/.*", null);

	@Override
	public boolean matches(HttpServletRequest request) {
		if (defaultAllowPattern.matcher(request.getMethod()).matches())
			return false;
		return !csrfDisabledMatcher.matches(request);
	}
}

