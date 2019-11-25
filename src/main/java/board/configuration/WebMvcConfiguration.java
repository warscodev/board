package board.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import board.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
		//파일 인코딩 설정
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//업로드 되는 파일 크기 제한. 바이트(byte)단위로 설정 가능. 여기서는 5mb로 설정.
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		
		return commonsMultipartResolver;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}

}
