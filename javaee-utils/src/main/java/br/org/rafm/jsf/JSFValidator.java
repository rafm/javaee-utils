package br.org.rafm.jsf;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public final class JSFValidator {

	/**
	 * Validates if two input fields are equals. It needs the attribute "otherField" to be specified in the component.
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @return true if the fields are equals or false if they are not.
	 */
	public static boolean isOtherFieldEqual(FacesContext context, UIComponent component, Object value) {
		final String otherFieldName = (String) component.getAttributes().get("otherField");
		if (otherFieldName == null) {
			return false;
		}
		
		final UIInput otherField = (UIInput) component.findComponent(otherFieldName);
		final String otherFieldValue = (String) (otherField.isValid() ? otherField.getValue() : otherField.getSubmittedValue());
		if (otherFieldValue == null) {
			return value == null;
		} else {
			return otherFieldValue.equals(value);
		}
	}
}
