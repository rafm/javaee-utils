package br.org.rafm.jsf;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.el.EvaluationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("deprecation")
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
			final Throwable exception = getRootException(e.getRootCause());
			logException(exception);
			throw new ServletException(exception);
		}
	}

	@Override
	public void destroy() {}

	protected abstract void logException(Throwable exception);
	
	protected Throwable getRootException(Throwable exception) {
		Throwable cause;
		while ((cause = exception.getCause()) != null && (exception instanceof FacesException || exception instanceof EvaluationException)) {
			exception = cause;
		}

		return exception;
	}
}
