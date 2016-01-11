package br.org.rafm.unittest;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

public final class FacesContextMocker extends FacesContext {

	private FacesContextMocker() {}
	
	public static void mockFacesContext(FacesContext facesContextMock) {
		setCurrentInstance(facesContextMock);
		assertEquals(facesContextMock, getCurrentInstance());
	}
		
	@Override
	public void addMessage(String arg0, FacesMessage arg1) {
		throw new IllegalStateException();
	}

	@Override
	public Application getApplication() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator<String> getClientIdsWithMessages() {
		throw new IllegalStateException();
	}

	@Override
	public ExternalContext getExternalContext() {
		throw new IllegalStateException();
	}

	@Override
	public Severity getMaximumSeverity() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator<FacesMessage> getMessages() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator<FacesMessage> getMessages(String arg0) {
		throw new IllegalStateException();
	}

	@Override
	public RenderKit getRenderKit() {
		throw new IllegalStateException();
	}

	@Override
	public boolean getRenderResponse() {
		throw new IllegalStateException();
	}

	@Override
	public boolean getResponseComplete() {
		throw new IllegalStateException();
	}

	@Override
	public ResponseStream getResponseStream() {
		throw new IllegalStateException();
	}

	@Override
	public ResponseWriter getResponseWriter() {
		throw new IllegalStateException();
	}

	@Override
	public UIViewRoot getViewRoot() {
		throw new IllegalStateException();
	}

	@Override
	public void release() {
		throw new IllegalStateException();
	}

	@Override
	public void renderResponse() {
		throw new IllegalStateException();
	}

	@Override
	public void responseComplete() {
		throw new IllegalStateException();
	}

	@Override
	public void setResponseStream(ResponseStream arg0) {
		throw new IllegalStateException();
	}

	@Override
	public void setResponseWriter(ResponseWriter arg0) {
		throw new IllegalStateException();
	}

	@Override
	public void setViewRoot(UIViewRoot arg0) {
		throw new IllegalStateException();
	}
}
