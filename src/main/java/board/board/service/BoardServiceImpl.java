package board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.board.mapper.BoardMapper;
import board.board.dto.BoardDto;

@Service

//트랜잭션 어노테이션이 적용된 대상은 설정된 트랜잭션 빈에 의해서 트랜잭션이 처리됨.
//트랜잭션 어노테이션 이용한 트랜잭션은 새로운 클래스 또는 메서드 등을 만들때마다 붙여줘야하는 단점이 있음.
//@Transactional
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Override
	public List<BoardDto> selectBoardList() throws Exception{
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDto board) throws Exception{
		boardMapper.insertBoard(board);
	}
	
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception{
		boardMapper.updateHitCount(boardIdx);
		
		//트랜잭션 테스트를 위한 오류코드
		//int i = 10 / 0;
		return boardMapper.selectBoardDetail(boardIdx);
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
