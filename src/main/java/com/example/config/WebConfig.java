package com.example.config;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ResourceUrlProvider urlProvider;

    @Autowired
    private HandlebarsViewResolver viewResolver;

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        viewResolver.registerHelper("src", new ResourceUrlHelper(this.urlProvider));
        registry.viewResolver(viewResolver);
    }

    /**
     * http://stackoverflow.com/questions/28875565/versionresourceresolver-breaks-css-relative-urls
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/javascript/*.js")
                .addResourceLocations("classpath:/static/javascript/")
                .setCachePeriod(60 * 60 * 24 * 365) /* one year */
                .resourceChain(true)
                .addResolver(new VersionResourceResolver()
                        .addContentVersionStrategy("/**"));
    }

}
