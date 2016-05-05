package br.org.rafm.jsf;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractExceptionFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		try {
			chain.doFilter(httpRequest, httpResponse);
		} catch (FileNotFoundException e) {
			httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, httpRequest.getRequestURI());
		} catch (ServletException e) {
			throw new ServletException(e.getMessage(), unwrap(e.getRootCause()));
		}
	}

	@Override
	public void destroy() {}

	protected boolean isTypeToUnwrap(final Throwable exception) {
		return exception instanceof FacesException || exception instanceof ELException || exception instanceof EJBException;
	}
	
	private Throwable unwrap(Throwable exception) {
		Throwable cause;
		while (isTypeToUnwrap(exception) && (cause = exception.getCause()) != null) {
			exception = cause;
		}
		return exception;
	}
}
