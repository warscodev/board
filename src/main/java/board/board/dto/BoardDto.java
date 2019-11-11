package board.board.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardDto {
	
	private int boardInx;
	private String title;
	private String contents;
	private int hitcnt;
	private String creatorId;
	private LocalDateTime createdDatetime;
	private String updaterId;
	private LocalDateTime updatedDatetime;

}
