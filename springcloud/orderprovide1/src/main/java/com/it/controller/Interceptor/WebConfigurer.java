package com.it.controller.Interceptor;

import com.it.common.CommonUtils;
import com.it.common.constant.Constant;
import com.it.utils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Set;


@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

    private static Logger LOGGER = Logger.getLogger(WebConfigurer.class);
    private static String[] filterIntArray;

    {
        Set<String> filterIntList = PropertyUtils.getPropertiesValue(CommonUtils.getClassPath() + Constant.FILTER_INTERFACE);
        filterIntArray = filterIntList.toArray(new String[filterIntList.size()]);
        LOGGER.info("filter interceptor interfaces, filterInterfaces{} :"+ filterIntList);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(filterIntArray);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST","PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
}
