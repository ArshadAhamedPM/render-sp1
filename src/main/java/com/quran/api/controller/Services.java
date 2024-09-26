package com.quran.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.quran.api.model.Quran;
import com.quran.api.model.Sura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Services {
	
	@Autowired
	private HttpServletRequest request;
	
	final static Logger logger = LoggerFactory.getLogger(Services.class);
	   

	public Quran getSuraDetails() throws JAXBException, IOException {
		getClientIp();
		
		InputStream xmlFile = new ClassPathResource("quran-uthmani.xml").getInputStream();
		JAXBContext jaxbContext = JAXBContext.newInstance(Quran.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Quran quran = (Quran) unmarshaller.unmarshal(xmlFile);
		return quran;
	}

	public List<Map<String, Object>> getSuras() throws JAXBException, IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		getSuraDetails().getSuras().stream().forEach(x -> {
			Map<String, Object> map = new HashMap<>();
			map.put("index", x.getIndex());
			map.put("name", x.getName());
			list.add(map);
		});
		return list;
	}

	public List<Map<String, Object>> getSura(int id) throws JAXBException, IOException {
		List<Map<String, Object>> list = new ArrayList<>();
		getSuraDetails().getSuras().stream().filter(x -> x.getIndex() == id).findFirst().ifPresent(sura -> {
			sura.getAyas().stream().forEach(x -> {
				Map<String, Object> map = new HashMap<>();
				map.put("index", x.getIndex());
				map.put("name", x.getText());
				list.add(map);

			});
		});
		return list;
	}

	public String getAyah(int id, int ayaId) throws JAXBException, IOException {
		String aya = "";
		Optional<Sura> sura = getSuraDetails().getSuras().stream().filter(x -> x.getIndex() == id).findFirst();
		if (sura.isPresent()) {
			aya = sura.get().getAyas().stream().filter(x -> x.getIndex() == ayaId).findFirst().get().getText();
		}
		return aya;
	}
	
	public void getClientIp() {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        logger.info("getSuraDetails : "+ipAddress);

    }

}
