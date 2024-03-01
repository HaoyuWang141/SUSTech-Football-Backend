package com.sustech.football.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletContainerConfig {

    @Bean
    public ServletWebServerFactory servletContainer() {
        // 创建Tomcat容器工厂
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        // 添加HTTP连接器
        tomcat.addAdditionalTomcatConnectors(createStandardConnector());
        return tomcat;
    }

    private Connector createStandardConnector() {
        // 定义要添加的连接器，这里配置为监听8080端口的HTTP请求
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8084); // HTTP端口
        return connector;
    }
}
