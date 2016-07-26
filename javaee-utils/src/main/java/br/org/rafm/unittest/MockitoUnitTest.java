package br.org.rafm.unittest;

import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class MockitoUnitTest {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
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
