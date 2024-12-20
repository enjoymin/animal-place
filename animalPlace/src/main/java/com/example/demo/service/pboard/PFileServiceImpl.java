package com.example.demo.service.pboard;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.pboard.PFileMapper;
import com.example.demo.mapper.pboard.PLikelistMapper;
import com.example.demo.model.pboard.PBoardDTO;
import com.example.demo.model.pboard.PFileDTO;

@Service
public class PFileServiceImpl implements PFileService {

	@Autowired
	private PFileMapper pfmapper;
	
	@Autowired
	private PLikelistMapper plmapper;

	@Value("${file.dir}")
	private String saveFolder;

	@Override
	public ArrayList<String> getFilesByBoardnum(Long boardnum) {
		return pfmapper.getFilesByBoardnum(boardnum);
	}

	@Override
	public ArrayList<String> getFileByBoardnum(ArrayList<PBoardDTO> list) {
		return pfmapper.getFileByBoardnum(list);
	}

	@Override
	public boolean insert(Long boardnum, MultipartFile[] files) throws Exception {
		if(files.length == 0 || files == null) {
			return true;
		}
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String orgname = file.getOriginalFilename();

			int lastDot = orgname.lastIndexOf(".");
			// 확장자가 없는 경우 예외 처리
			if (lastDot == -1 || lastDot == orgname.length() - 1) {
				return false;
			}

			String ext = orgname.substring(lastDot); // 파일 확장자

			LocalDateTime now = LocalDateTime.now();
			String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
			String systemname = time + UUID.randomUUID().toString() + ext;

			String path = saveFolder + "pimages/" + systemname;

			PFileDTO pfdto = new PFileDTO();
			pfdto.setOrgname(orgname);
			pfdto.setSystemname(systemname);
			pfdto.setBoardnum(boardnum);

			if (pfmapper.insertFile(pfdto) < 0) {
				return false;
			}

			// 파일을 지정한 경로로 전송
			file.transferTo(new File(path));
		}
		return true;
	}

	@Override
	public boolean delete(Long boardnum, String[] delete) {
		// delete 배열이 null 또는 비어있는지 체크
		if (delete == null || delete.length == 0) {
			System.out.println("No files to delete.");
			return true; // 삭제할 파일이 없으면 true 반환
		}

		for (int i = 0; i < delete.length; i++) {
			String systemname = delete[i];
			// 파일 삭제 DB 처리
			pfmapper.deleteFile(boardnum, systemname);

			String filePath = saveFolder + "pimages/" + systemname;
			File file = new File(filePath);

			// 파일이 존재하는지 체크
			if (file.exists()) {
				if (!file.delete()) {
					// 파일 삭제 실패
					System.err.println("Failed to delete file: " + filePath);
					return false; // 삭제 실패 시 false 반환
				}
			} else {
				// 파일이 존재하지 않을 경우 처리
				System.out.println("File does not exist: " + filePath);
			}
		}
		return true; // 모든 처리 후 true 반환
	}

	@Override
	public ArrayList<String> getBestImage() {
		ArrayList<String> flist = new ArrayList<>();
		ArrayList<Long> list = plmapper.bestLike();
		for(int i = 0; i < list.size(); i++) {
			flist.add(pfmapper.getImg(list.get(i)));
		}
		System.out.println(flist);
		return flist;
	}
}
