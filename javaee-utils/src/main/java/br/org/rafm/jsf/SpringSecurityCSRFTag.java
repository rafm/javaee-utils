package br.org.rafm.jsf;

import java.io.IOException;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;

@FacesComponent(value="security", namespace="http://rafm.org.br/security", tagName="csrf", createTag=true)
public class SpringSecurityCSRFTag extends UIComponentBase {

	@Override
	public String getFamily() {
		return "security";
	}

	@Override
	public void encodeEnd(FacesContext facesContext) throws IOException {
		final CsrfToken csrfToken = (CsrfToken) ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getAttribute("_csrf");
		
		if (csrfToken != null) {
			final ResponseWriter writer = facesContext.getResponseWriter();
			
			writer.startElement("input", this);
				writer.writeAttribute("type", "hidden", "type");
				writer.writeAttribute("id", getClientId(facesContext), "id");
				writer.writeAttribute("name", csrfToken.getParameterName(), "name");
				writer.writeAttribute("value", csrfToken.getToken(), "value");
			writer.endElement("input");
		}
	}
}
