package cn.java666.tools.crackdbeaverpassword.config;

import cn.java666.tools.crackdbeaverpassword.Main;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Geek
 * @date 2021-03-25 19:50:49 
 */

@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class APIConfig {
  @Value("${server.port}")
  private int port;
  
  @Bean
  public Docket API() {
    String doc_url_local = "http://127.0.0.1:" + port + "/doc.html";
    log.warn("API 文档地址（本地） = {}", doc_url_local);
    
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(
        new ApiInfoBuilder()
          .title("crack-dbeaver-password")
          .description("dbeaver 密码破解工具，我的密码必须由我做主。")
          .termsOfServiceUrl("https://github.com/geekyouth")
          .contact(new Contact("geekyouth", null, "geekyouth@qq.com"))
          .version("1.0")
          .build()
      )
      .groupName("default-group") //分组名称
      .select()
      .apis(RequestHandlerSelectors.basePackage(Main.class.getPackage().getName())) //这里指定Controller扫描包路径
      .paths(PathSelectors.any())
      .build();
  }
}
