package zhelonin.hm3.config;

import jakarta.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;



public class WebConfig implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) {
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic registration =
        (ServletRegistration.Dynamic) servletContext.addServlet("dispatcher", dispatcherServlet);
    registration.setLoadOnStartup(1);
    registration.addMapping("/");
  }

}
