package board.board.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//예외처리 클래스
@ControllerAdvice
public class ExceptionHandler {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//여기서는 간단히 Exception.calss로 모든 예외처리를 지정했지만, 실제 프로젝트에서는 각각의 예외에 맞는 적절한 예외처리가 필요. 절대 Exception클래스를 이용해 모든 예외를 처리하지 않는다.
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {
		
		//예외 발생시 보여줄 화면 지정
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		
		
		//에러 로그 출력
		log.error("exception", exception);
		
		return mv;
		
	}

}
