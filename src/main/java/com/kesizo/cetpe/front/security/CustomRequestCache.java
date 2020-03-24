package com.kesizo.cetpe.front.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CustomRequestCache extends HttpSessionRequestCache {

	/**
	 * Saves unauthenticated requests so we can redirect the user to the page they were trying to access once they’re logged in
	 *
	 * @param request
	 * @param response
	 */
	@Override
	public void saveRequest(HttpServletRequest request, HttpServletResponse response) { // 


		if (!SecurityUtils.isFrameworkInternalRequest(request)) {
			super.saveRequest(request, response);
		}
	}

}