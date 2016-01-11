package br.org.rafm.cdi;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;

public final class CDIUtils {
	
	public static final <T> T getReference(Class<T> classe, Annotation... annotations) {
		final BeanManager bm = CDI.current().getBeanManager(); // PostConstruct
		final Bean<?> bean = bm.getBeans(classe, annotations).iterator().next();
		return classe.cast(bm.getReference(bean, classe, bm.createCreationalContext(bean)));
	}
}
