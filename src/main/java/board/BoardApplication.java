package board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// multipartResolver를 따로 등록했기 때문에 첨부파일과 관련된 자동구성을 사용하지 않도록 변경함.
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
