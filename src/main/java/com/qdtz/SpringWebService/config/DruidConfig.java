package com.qdtz.SpringWebService.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * druid监控控制台配置 
 * http://localhost:port/contextpath/druid
 */
@Configuration
public class DruidConfig {
    @Bean
    public ServletRegistrationBean druidServlet() {// 主要实现web监控的配置处理
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");// 表示进行druid监控的配置处理操作
        servletRegistrationBean.addInitParameter("allow","192.168.3.175,192.168.88.240");//白名单
        servletRegistrationBean.addInitParameter("deny", "192.168.88.24");//黑名单
        servletRegistrationBean.addInitParameter("loginUsername", "root");// 用户名
        servletRegistrationBean.addInitParameter("loginPassword", "root");// 密码
        servletRegistrationBean.addInitParameter("resetEnable", "false");// 是否可以重置数据源
        return servletRegistrationBean;

    }

    @Bean // 监控
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");//所有请求进行监控处理
        filterRegistrationBean.addInitParameter("exclusions", "/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");// 排除
        return filterRegistrationBean;
    }

//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druidDataSource() {
//        return new DruidDataSource();
//    }
}
