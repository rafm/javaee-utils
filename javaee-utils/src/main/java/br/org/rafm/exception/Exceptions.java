package br.org.rafm.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

public final class Exceptions {
	
	/**
	 * <h1>Servlet context params used</h1>
	 * <ul>
	 * <li>name=br.org.rafm.NOT_USE_DEFAULT_EXCEPTION_TYPES_TO_UNWRAP, description=Default types: javax.faces.FacesException, javax.el.ELException, javax.ejb.EJBException</li>
	 * <li>name=br.org.rafm.EXCEPTION_TYPES_TO_UNWRAP</li>
	 * <ul>
	 * 
	 * @author Rafael Augusto Felix Maia
	 *
	 */
	public static Throwable unwrap(Throwable exception, final ServletContext servletContext) {
		Throwable cause;
		if ((cause = exception.getCause()) == null) {
			return exception;
		}
		
		final List<String> typesToUnwrap = getTypesToUnwrap(servletContext);
		while (isTypeToUnwrap(exception, typesToUnwrap)) {
			exception = cause;
			
			if ((cause = exception.getCause()) == null) {
				break;
			}
		}
		
		return exception;
	}
	
	private static List<String> getTypesToUnwrap(final ServletContext servletContext) {
		final List<String> typesToUnwrap = new ArrayList<String>();
		
		if (!Boolean.valueOf(servletContext.getInitParameter("br.org.rafm.NOT_USE_DEFAULT_EXCEPTION_TYPES_TO_UNWRAP"))) {
			Collections.addAll(typesToUnwrap, "javax.faces.FacesException", "javax.el.ELException", "javax.ejb.EJBException");
		}
		
		final String EXCEPTION_TYPES_TO_UNWRAP = servletContext.getInitParameter("br.org.rafm.EXCEPTION_TYPES_TO_UNWRAP");
		if (EXCEPTION_TYPES_TO_UNWRAP != null) {
			for (final String TYPE_TO_UNWRAP : EXCEPTION_TYPES_TO_UNWRAP.split(",")) {
				typesToUnwrap.add(TYPE_TO_UNWRAP.trim());
			}
		}
		
		return typesToUnwrap;
	}

	private static boolean isTypeToUnwrap(final Throwable exception, final List<String> typesToUnwrap) {
		final String className = exception.getClass().getName();
		
		for (final String typeToUnwrap : typesToUnwrap) {
			if (className.equals(typeToUnwrap)) {
				return true;
			}
		}
		
		return false;
	}
}
