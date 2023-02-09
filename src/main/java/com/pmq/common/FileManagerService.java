package com.pmq.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	// WebMvcConfig도 같이 볼 것 => 실제 저장된 파일과 이미지패스를 매핑해줌 (*)
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// WAS에 url을 만들어내고, 그 url과 내컴퓨터에 있는 이미지파일을 맵핑!
	// 1. 실제 이미지가 저장 될 경로
	// 1) 집 컴퓨터
		// public final static String FILE_UPLOAD_PATH = "D:\\웹개발(21-05-20)\\pmq\\pmq_workspace\\images/";
		public final static String FILE_UPLOAD_PATH = "D:\\web\\workspace4pmq\\pmq\\src\\main\\resources\\static\\images\\user_images/";
	// 2) 학원
	// public final static String FILE_UPLOAD_PATH = "D:\\심미영_웹개발_210520\\7_personal_project\\pmq_workspace\\images/";
	
	// 2. 파일을 받아서 String(url = 내컴퓨터 상 url) return
	public String saveFile(String userLoginId, MultipartFile file) throws IOException {
		// 3. 파일 디렉토리 경로 -> 사용자별로 다른 폴더를 갖게..! (파일명이 겹치지 않게)
		// ex) marobiana_162099585780(current time)/apple.png  
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/" ;
		String filePath = FILE_UPLOAD_PATH + directoryName; // 이 경로에 만들 것이다..!
		
		// 4. 진짜 폴더 만들기
		File directory = new File(filePath); 
		// mkdir(make directory) -> boolean값으로 리턴
		if (directory.mkdir() == false) {
			// false -> 폴더만들기 실패
			logger.error("[fileUpload] directory create fail." + directoryName + ", filePath" + filePath);
			return null; // 실패했기 때문에 null값을 리턴
		}
		
		// 5. 파일 업로드 (byte 단위로 업로드 된다.)
		byte[] bytes = file.getBytes(); // 예외처리는 위로 던지기~
		// Path path = Paths.get(filePath + file); // 어디에 어떤 이름으로 올릴건지 (파일 경로 + MutipartFile로 가져온 file명)
		Path path = Paths.get(filePath + file.getOriginalFilename());  // input에 올릴 파일명이다.
		
		// 진짜로 내 컴퓨터에 쓰기!
		Files.write(path, bytes);
		
		// 6. 이미지 URL path를 리턴한다. -> 이것을 주소창에 치면 이미지가 나와야함..!
		return "/images/" + directoryName + file.getOriginalFilename();  
	}
	
	public void deleteFile(String imgUrl) throws IOException {
		Path path = Paths.get(FILE_UPLOAD_PATH + imgUrl.replace("/images/",""));
		if (Files.exists(path)) {
			// 파일이 존재하면 삭제한다.
			Files.delete(path);
		}
		
		// 디렉토리 삭제
		path = path.getParent(); 
		if (Files.exists(path)) {
			Files.delete(path);
		}
		
	}
	
}
