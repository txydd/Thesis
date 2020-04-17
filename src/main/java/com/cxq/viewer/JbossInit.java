/*
package com.cxq.viewer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

@Configuration
public class JbossInit implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		WebApplicationContext context = getContext();

		Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(context));
		registration.setLoadOnStartup(1);
		registration.addMapping("/*");
	}

	private WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation(ViewerApplication.class.getName());
		return context;
	}

}
*/
