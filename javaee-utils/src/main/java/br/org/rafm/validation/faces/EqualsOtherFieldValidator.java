package br.org.rafm.validation.faces;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("rafm.EqualsOtherFieldValidator")
public class EqualsOtherFieldValidator implements Validator {
	
	private ResourceBundle messageBundle;
	
	private String componentLabel;
	
	/**
	 * Validates if two input fields are equals. It needs the attribute "otherField" to be specified in the component.
	 * 
	 * To customize messages, inform the attribute(s) "message" and/or "message_detail".
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * 
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		validation : {
			final String otherFieldName = (String) component.getAttributes().get("otherField");
			if (otherFieldName == null) {
				break validation;
			}
			
			final UIInput otherField = (UIInput) component.findComponent(otherFieldName);
			final Object otherFieldValue = otherField.isValid() ? otherField.getValue() : otherField.getSubmittedValue();
			if (otherFieldValue == null) {
				if (value == null) {
					return;
				}
			} else if (otherFieldValue.equals(value)) {
				return;
			}
		}
		
		throw new ValidatorException(createFacesMessages(context, component));
	}
	
	private FacesMessage createFacesMessages(final FacesContext context, final UIComponent component) {
		String summary = (String) component.getAttributes().get("message");
		if (summary == null) {
			verifyMessageBundleInitialization(context);
			
			summary = getMessage(context, component, UIInput.REQUIRED_MESSAGE_ID);
		}
		
		String detail = (String) component.getAttributes().get("message_detail");
		if (detail == null) {
			verifyMessageBundleInitialization(context);
			
			try {
				detail = getMessage(context, component, UIInput.REQUIRED_MESSAGE_ID+"_detail");
			} catch (MissingResourceException e) {
				detail = summary;
			}
		}
		
		return new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
	}
	
	private void verifyMessageBundleInitialization(final FacesContext context) {
		if (messageBundle == null) messageBundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getExternalContext().getRequestLocale());
	}
	
	private String getMessage(final FacesContext context, final UIComponent component, final String key) {
		if (componentLabel == null) {
			componentLabel = getComponentLabel(context, component);
		}
		
		return MessageFormat.format(messageBundle.getString(key), componentLabel);
	}
	
	private String getComponentLabel(final FacesContext context, final UIComponent component) {
		Object label = component.getAttributes().get("label");
		
		if (label == null || label.toString().isEmpty()) {
			final ValueExpression labelExpression = component.getValueExpression("label");
			
			if (labelExpression != null) {
				label = labelExpression.getValue(context.getELContext());
			}
		}
		
		return label != null ? label.toString() : component.getClientId();
	}
}
