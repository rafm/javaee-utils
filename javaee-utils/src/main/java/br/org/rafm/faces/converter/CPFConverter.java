package br.org.rafm.faces.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("rafm.CPFConverter")
public class CPFConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value != null ? value.replaceAll("\\D", "") : null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}
		
		if (value instanceof String && ((String) value).length() == 11) {
			value = ((String) value).substring(0, 3)+"."+((String) value).substring(3, 6)+"."+((String) value).substring(6, 9)+"-"+((String) value).substring(9, 11);
		}
		
		return value.toString();
	}
}
