package br.org.rafm.jsf;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;

public final class JSFUtil {

	/**
	 * 
	 * @param htmlPageId
	 * @return the html page content
	 * @throws IOException
	 */
	public static String getHTMLPageContent(final String viewId) throws IOException {
		final StringBuilder sb = new StringBuilder();
		
		try (final BufferedInputStream bis = new BufferedInputStream(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(viewId+".html"))) {
			final byte[] buffer = new byte[1024];
			
			int nBytesRead;
			while ((nBytesRead = bis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, nBytesRead));
			}
		}
		
		return sb.toString();
	}
}
