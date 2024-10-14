package com.example.demo.service.adoption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.adoption.AdoptionFileMapper;
import com.example.demo.model.adoption.AdoptionFileDTO;

@Service
public class AdoptionFileServiceImp implements AdoptionFileService {

	@Value("${file.dir}")
	private String saveFolder;
	
	@Autowired
	private AdoptionFileMapper fmapper;

	@Override
	public HashMap<String, Object> getTumbnailResource(String systemname) throws Exception {

		Path path = Paths.get(saveFolder + "adoption/" + systemname);

		String contentType;
		Resource resource;
		contentType = Files.probeContentType(path);
		resource = new InputStreamResource(Files.newInputStream(path));
		HashMap<String, Object> datas = new HashMap<>();
		datas.put("contentType", contentType);
		datas.put("resource", resource);
		return datas;
	}

	@Override
	public AdoptionFileDTO getOneFileByAdoptionnum(long adoptionnum) {
		return fmapper.getOneFileByAdoptionnum(adoptionnum);
	}
}
