package br.org.rafm.faces;

import static br.org.rafm.exception.Exceptions.unwrap;

import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PreRenderViewEvent;
import javax.faces.view.ViewDeclarationLanguage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAjaxExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public AbstractAjaxExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	private boolean isAjaxRequest(FacesContext facesContext) {
		return facesContext != null && facesContext.getPartialViewContext().isAjaxRequest();
	}

	private boolean isResponseCommitted(FacesContext facesContext) {
		return facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE && facesContext.getExternalContext().isResponseCommitted();
	}

	private void logAndRemoveExceptions(final Iterator<ExceptionQueuedEvent> iterator) {
		while (iterator.hasNext()) {
			logException(iterator.next().getContext().getException());
			iterator.remove();
		}
	}

	@SuppressWarnings("unused")
	private void resetResponse(final ExternalContext externalContext) {
		String contentType = externalContext.getResponseContentType();
		String characterEncoding = externalContext.getResponseCharacterEncoding();
		
		externalContext.responseReset();
		externalContext.setResponseContentType(contentType);
		externalContext.setResponseCharacterEncoding(characterEncoding);
	}
	
	@Override
	public void handle() throws FacesException {
		try {
			final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
			
			if (iterator.hasNext()) { // TODO !(firstException instanceof AbortProcessingException)
				final FacesContext facesContext = FacesContext.getCurrentInstance();
				
				if (isAjaxRequest(facesContext)) {
					if (isResponseCommitted(facesContext)) {
						logAndRemoveExceptions(iterator);
					} else {
						final Throwable firstException = getUnhandledExceptionQueuedEvents().iterator().next().getContext().getException();
						
						if (isRedirectionalException(firstException)) {
							redirectPage(facesContext, iterator);
						} else {
							showMessages(facesContext, iterator);
						}
					}
				} else if (logExceptionFromNonAjaxRequests()) {
					final Throwable firstException = iterator.next().getContext().getException();
					
					logException(firstException);
				}
			}
		} catch (Exception e) {
			logException(e);
		}
		
		wrapped.handle();
	}

	private void redirectPage(final FacesContext facesContext, final Iterator<ExceptionQueuedEvent> iterator) {
		final Throwable firstException = iterator.next().getContext().getException(); iterator.remove();
		
		logException(firstException);
		
		renderView(facesContext, firstException);
		
		if (logRemainingExceptionsInRedirectionalCases()) {
			logAndRemoveExceptions(iterator);
		} else {
			while (iterator.hasNext()) { iterator.next(); iterator.remove(); }
		}
	}

	private void renderView(final FacesContext facesContext, final Throwable exception) {
		final HttpServletRequest request = buildExceptionParametersFromRequest((HttpServletRequest) facesContext.getExternalContext().getRequest(), exception);
		final String viewId = getViewId(exception);
		final ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		final UIViewRoot viewRoot = viewHandler.createView(facesContext, viewId);
		
		facesContext.setViewRoot(viewRoot);
		facesContext.getPartialViewContext().setRenderAll(true);
		
		try {
			final ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(facesContext, viewId);
			vdl.buildView(facesContext, viewRoot);
			
			facesContext.getApplication().publishEvent(facesContext, PreRenderViewEvent.class, viewRoot);
			vdl.renderView(facesContext, viewRoot);
			
			facesContext.responseComplete();
		} catch (Exception e) {
			logException(e);
		} finally {
			request.removeAttribute(RequestDispatcher.ERROR_EXCEPTION);
		}
	}

	private HttpServletRequest buildExceptionParametersFromRequest(final HttpServletRequest request, final Throwable exception) {
		request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, exception);
		request.setAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE, exception.getClass());
		request.setAttribute(RequestDispatcher.ERROR_MESSAGE, exception.getMessage());
		request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
		request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		return request;
	}

	private void showMessages(final FacesContext facesContext, final Iterator<ExceptionQueuedEvent> iterator) {
		while (iterator.hasNext()) {
			final Throwable exception = iterator.next().getContext().getException(); iterator.remove();
			
			logException(exception);
			
			for (FacesMessage facesMessage : getFacesMessages(unwrap(exception, (ServletContext) facesContext.getExternalContext().getContext()))) {
				facesContext.addMessage(null, facesMessage);
			}
		}
	}

	protected abstract boolean isRedirectionalException(final Throwable firstException);

	protected abstract boolean logRemainingExceptionsInRedirectionalCases();

	protected abstract String getViewId(final Throwable exception);

	protected abstract List<FacesMessage> getFacesMessages(final Throwable exception);
	
	protected abstract void logException(final Throwable exception);
	
	protected abstract boolean logExceptionFromNonAjaxRequests();
}
