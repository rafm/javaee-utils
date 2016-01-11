package br.org.rafm.unittest;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class MockitoUnitTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	protected void mockLog4J2(final Logger log) {
		when(log.exit(anyObject())).
			then(invocation -> { return invocation.getArguments()[0]; });
		when(log.throwing(anyObject())).
			then(invocation -> { return invocation.getArguments()[0]; });
	}
	
	protected void mockFacesContext(final FacesContext facesContext) {
		FacesContextMocker.mockFacesContext(facesContext);
	}
	
	protected ResourceBundle createMockForResourceBundle() {
		return new ResourceBundle() {
			@Override
			protected Object handleGetObject(String key) {
				return key;
			}
			
			@Override
			public Enumeration<String> getKeys() {
				return null;
			}
		};
	}
}
