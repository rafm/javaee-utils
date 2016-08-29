package br.org.rafm.servlet;

import static br.org.rafm.exception.Exceptions.unwrap;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ExceptionUnwrapperFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (ServletException e) {
			throw new ServletException(e.getMessage(), unwrap(e.getRootCause(), request.getServletContext()));
		}
	}

	@Override
	public void destroy() {}
}
