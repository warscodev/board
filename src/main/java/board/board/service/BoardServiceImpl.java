package board.board.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/*import java.util.Iterator;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;*/


//import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.mapper.BoardMapper;
import board.board.common.FileUtils;
import board.board.dto.BoardDto;
import board.board.dto.BoardFileDto;

@Service

//트랜잭션 어노테이션이 적용된 대상은 설정된 트랜잭션 빈에 의해서 트랜잭션이 처리됨.
//트랜잭션 어노테이션 이용한 트랜잭션은 새로운 클래스 또는 메서드 등을 만들때마다 붙여줘야하는 단점이 있음.
//@Transactional
public class BoardServiceImpl implements BoardService{
	
	private Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list)==false) {
			boardMapper.insertBoardFileList(list);
		}
		
		/* 업로드된 파일 정보 로그 출력
		  if(ObjectUtils.isEmpty(multipartHttpServletRequest)==false) {
		  Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		  String name; while(iterator.hasNext()) { name = iterator.next();
		  log.debug("file tag name : " + name); List<MultipartFile> list =
		  multipartHttpServletRequest.getFiles(name); for(MultipartFile multipartFile
		  :list) { log.debug("start file information"); log.debug("file name : "+
		  multipartFile.getOriginalFilename()); log.debug("file size : "+
		  multipartFile.getSize()); log.debug("file content type :" +
		  multipartFile.getContentType()); log.debug("end file information.\n"); } } }
		 */
		
		
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);
		
		//트랜잭션 테스트를 위한 오류코드
		//int i = 10 / 0;
		return board;
	}
	
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		boardMapper.updateBoard(board);
	}

	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		boardMapper.deleteBoard(boardIdx);
	}

}
