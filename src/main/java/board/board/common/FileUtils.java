package board.board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.board.dto.BoardFileDto;

@Component
public class FileUtils {
	public List<BoardFileDto> parseFileinfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;}
		
		List<BoardFileDto> fileList = new ArrayList<>();
		
		//파일이 업로드될 폴더를 생성.
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "image/"+current.format(format);
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
					
					// 서버에 저장될 파일 이름 생성. 나노초를 이용해 중복가능성 제거
					newFileName = Long.toString(System.nanoTime()) +
							originalFileExtension;
					
					//파일 정보를 BoardFileDto에 저장.
					BoardFileDto boardFile = new BoardFileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setStoredFilePath(path + "/"+ newFileName);
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					fileList.add(boardFile);
					
					//업로드된 파일을 새러운 이름으로 바꾸어 지정된 경로에 저장.
					file = new File(path + "/" + newFileName);
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
}