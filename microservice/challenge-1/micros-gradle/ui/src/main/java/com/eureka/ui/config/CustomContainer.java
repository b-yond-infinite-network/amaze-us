package com.eureka.ui.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import javax.servlet.descriptor.TaglibDescriptor;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Custom container config class - replaces web.xml
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Component
public class CustomContainer implements
        WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory container) {
        if(container instanceof TomcatServletWebServerFactory) {
            final TomcatServletWebServerFactory tomcatEmbeddedServletContainerFactory
                    = (TomcatServletWebServerFactory) container;

            tomcatEmbeddedServletContainerFactory.addContextCustomizers(
                    new TomcatContextCustomizer() {
                        @Override
                        public void customize(Context context) {
                            //context.addWelcomeFile("index.jsp");
                            context.setSessionTimeout(10);
                            context.setCookieProcessor(new LegacyCookieProcessor());

                            final ErrorPage throwableErrorPage = new ErrorPage();
                            throwableErrorPage.setExceptionType("java.lang.Exception");
                            throwableErrorPage.setLocation("/WEB-INF/jsp/oops.jsp");
                            context.addErrorPage(throwableErrorPage);

                            final Collection<JspPropertyGroupDescriptor> jspPropertyGroups = new ArrayList<>();
                            final Collection<TaglibDescriptor> taglibs = new ArrayList<>();

                            final JspPropertyGroup group = new JspPropertyGroup();
                            group.addUrlPattern("*.jsp");
                            group.setPageEncoding("UTF-8");
                            group.setElIgnored(String.valueOf(false));
                            group.addIncludePrelude("/WEB-INF/templates/header.jspf");
                            group.addIncludeCoda("/WEB-INF/templates/footer.jspf");

                            final JspPropertyGroupDescriptor descriptor = new JspPropertyGroupDescriptorImpl(group);

                            jspPropertyGroups.add(descriptor);

                            final JspConfigDescriptor jspConfigDescriptor = new JspConfigDescriptorImpl(jspPropertyGroups, taglibs);
                            context.setJspConfigDescriptor(jspConfigDescriptor);
                        }
                    }
            );
        }
    }
}
