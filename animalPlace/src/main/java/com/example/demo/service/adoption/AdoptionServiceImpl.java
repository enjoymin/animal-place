package com.example.demo.service.adoption;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.he.UserDTO;
import com.example.demo.mapper.adoption.AdoptionFileMapper;
import com.example.demo.mapper.adoption.AdoptionMapper;
import com.example.demo.mapper.he.UserMapper;
import com.example.demo.model.adoption.AdoptionCriteria;
import com.example.demo.model.adoption.AdoptionDTO;
import com.example.demo.model.adoption.AdoptionFileDTO;

@Service
public class AdoptionServiceImpl implements AdoptionService {
	
	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private AdoptionMapper amapper;
	@Autowired
	private UserMapper umapper;
	@Autowired
	private AdoptionFileMapper fmapper;
	
	@Override
	public boolean regist(AdoptionDTO adoption, MultipartFile[] files) throws Exception{
		if(amapper.insertAdoption(adoption) != 1) {
			return false;
		}
		if(files == null || files.length == 0) {
			return false;
		}
		else {
			//방금 등록한 게시글 번호
			long adoptionnum = amapper.getLastNum(adoption.getUserid());
			System.out.println("파일 개수 : "+files.length);
			
			for(int i=0;i<files.length-1;i++) {
				MultipartFile file = files[i];
				
				//apple.png
				String orgname = file.getOriginalFilename();
				//5
				int lastIdx = orgname.lastIndexOf(".");
				//.png
				String ext = orgname.substring(lastIdx);
				
				LocalDateTime now = LocalDateTime.now();
				//20240911161030123
				String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
				//20240911161030123랜덤문자열.png
				String systemname = time+UUID.randomUUID().toString()+ext;
				
				String path = saveFolder + "adoption/" +systemname;
				
				//FileDTO 로 DB에 업로드 될 파일의 정보들 저장
				AdoptionFileDTO fdto = new AdoptionFileDTO();
				fdto.setOrgname(orgname);
				fdto.setSystemname(systemname);
				fdto.setAdoptionnum(adoptionnum);
				fmapper.insertFile(fdto);
				
				//실제 파일 업로드
				file.transferTo(new File(path));
			}
			return true;
		}
	}

	@Override
	public List<AdoptionDTO> getList(AdoptionCriteria adCri) {
		List<AdoptionDTO> list = amapper.getList(adCri);
		
		return list;
	}

	@Override
	public long getTotal(AdoptionCriteria adCri) {
		return amapper.getTotal(adCri);
	}

	@Override
	public AdoptionDTO getDetail(long adoptionnum) {
		return amapper.getAdoptionByAdoptinnum(adoptionnum);
	}

	@Override
	public long getLastNum(String userid) {
		return amapper.getLastNum(userid);
	}

	@Override
	public UserDTO getUserDetail(String userid) {
		UserDTO user = umapper.getUserByUserid(userid);
		return user;
	}

	@Override
	public boolean remove(long adoptionnum) {
		if(amapper.deleteAdoption(adoptionnum) == 1) {
			List<AdoptionFileDTO> files = fmapper.getFiles(adoptionnum);
			//게시글에 달린 파일의 정보들을 하나씩 꺼내오며
			for(AdoptionFileDTO fdto : files) {
				//saveFolder(파일이 저장되는 폴더)에서 꺼내온 DTO의 systemname에 해당하는 파일을 자바의 객체로 가져옴
				File file = new File(saveFolder+"adoption/",fdto.getSystemname());
				//그 파일이 존재한다면
				if(file.exists()) {
					//삭제
					file.delete();
					//DB상에서도 삭제
					fmapper.deleteFileBySystemname(fdto.getSystemname());
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean modify(AdoptionDTO adoption, MultipartFile[] files, String updateCnt) throws Exception {
		if(amapper.updateAdoption(adoption) != 1) {
			return false;
		}
		List<AdoptionFileDTO> orgFileList = fmapper.getFiles(adoption.getAdoptionnum());
		if(orgFileList.size() == 0 && (files == null || files.length == 0)) {
			return true;
		}
		else {
			if(files != null && files.length != 0) {
				boolean flag = false;
				//후에 비즈니스 로직 실패 시 원래대로 복구하기 위해 업로드 성공했던 파일들도 삭제해 주어야 한다.
				//업로드 성공한 파일들의 이름을 해당 리스트에 추가하면서 로직을 진행한다.
				ArrayList<String> sysnames = new ArrayList<>();
				for (int i = 0; i < files.length-1; i++) {
					MultipartFile file = files[i];
					String orgname = file.getOriginalFilename();
					//수정의 경우 중간에 있는 파일이 수정되지 않은 경우도 있다.(원본 파일 그대로 둔 경우)
					//그런 경우 file의 orgname은 null 이거나 "" 이다.
					//따라서 파일 처리를 할 필요가 없으므로 반복문을 넘어간다.
					if(orgname == null || orgname.equals("")) {
						continue;
					}
					//파일 업로드 과정(regist와 동일)
					int lastIdx = orgname.lastIndexOf(".");
					String ext = orgname.substring(lastIdx);
					LocalDateTime now = LocalDateTime.now();
					String time = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
					String systemname = time+UUID.randomUUID().toString()+ext;
					String path = saveFolder + "adoption/" +systemname;
					AdoptionFileDTO fdto = new AdoptionFileDTO();
					fdto.setOrgname(orgname);
					fdto.setSystemname(systemname);
					fdto.setAdoptionnum(adoption.getAdoptionnum());
					flag = fmapper.insertFile(fdto) == 1;
					file.transferTo(new File(path));
					//업로드 성공한 파일의 이름(systemname)을 sysnames 리스트에 추가
					sysnames.add(systemname);
					
					if(!flag) {
						break;
					}
				}
				//강제탈출(DB insert 실패)
				if(!flag) {
					//업로드 했던 파일 삭제, 게시글 데이터 돌려놓기, 파일 data(실제 파일) 삭제, ...
					//아까 추가했던 systemname들(업로드 성공했던 파일의 이름)을 꺼내오면서
					for(String systemname : sysnames) {
						//실제 파일이 존재한다면 삭제
						File file = new File(saveFolder + "adoption/",systemname);
						if(file.exists()) {
							file.delete();
						}
						//DB상에서도 삭제
						fmapper.deleteFileBySystemname(systemname);
					}
					//board 원래대로 돌리기
				}
			}
			//지워져야 할 파일(기존에 있었던 파일들 중 수정된 파일)들의 이름 추출
			String[] deleteNames = updateCnt.split("\\\\");
			for(String systemname : deleteNames) {
				File file = new File(saveFolder + "adoption/",systemname);
				if(file.exists()) {
					file.delete();
				}
				fmapper.deleteFileBySystemname(systemname);
			}
			return true;
		}
	}

	@Override
	public List<AdoptionFileDTO> getFiles(long adoptionnum) {
		return fmapper.getFiles(adoptionnum);
	}




    
}
