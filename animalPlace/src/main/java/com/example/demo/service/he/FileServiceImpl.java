package com.example.demo.service.he;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

	@Value("${file.dir}")
	private String saveFolder;
	
	@Override
	public HashMap<String, Object> getTumbnailResource(String systemname) {
		try {
			Path path = Paths.get(saveFolder+systemname);
			String contentType = Files.probeContentType(path);
			Resource resource = new InputStreamResource(Files.newInputStream(path));
			HashMap<String, Object> datas = new HashMap<>();
			datas.put("contentType", contentType);
			datas.put("resource", resource);
			return datas;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
