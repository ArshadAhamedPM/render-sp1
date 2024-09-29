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
import com.quran.api.repo.RequestDetailsRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Services {
	
	
	
	private Quran quran;
	
	final static Logger logger = LoggerFactory.getLogger(Services.class);
	   
	@jakarta.annotation.PostConstruct
	public void getSuraDetails() throws JAXBException, IOException {
		
		InputStream xmlFile = new ClassPathResource("quran-uthmani.xml").getInputStream();
		JAXBContext jaxbContext = JAXBContext.newInstance(Quran.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		quran = (Quran) unmarshaller.unmarshal(xmlFile);
	}

	public List<Map<String, Object>> getSuras() throws JAXBException, IOException {
		long s = System.currentTimeMillis();
		logger.info("Get sura names starts ");
		List<Map<String, Object>> list = new ArrayList<>();
		quran.getSuras().stream().forEach(x -> {
			Map<String, Object> map = new HashMap<>();
			map.put("index", x.getIndex());
			map.put("name", x.getName());
			list.add(map);
		});
		logger.info("Get sura names ends "+ (System.currentTimeMillis()-s));
		return list;
	}

	public List<Map<String, Object>> getSura(int id) throws JAXBException, IOException {
		long s = System.currentTimeMillis();
		logger.info("Get sura starts ");
		List<Map<String, Object>> list = new ArrayList<>();
		quran.getSuras().stream().filter(x -> x.getIndex() == id).findFirst().ifPresent(sura -> {
			sura.getAyas().stream().forEach(x -> {
				Map<String, Object> map = new HashMap<>();
				map.put("index", x.getIndex());
				map.put("name", x.getText());
				list.add(map);

			});
		});
		logger.info("Get sura ends "+ (System.currentTimeMillis()-s));
		return list;
	}

	public String getAyah(int id, int ayaId) throws JAXBException, IOException {
		String aya = "";
		long s = System.currentTimeMillis();
		logger.info("Get ayat starts ");
		Optional<Sura> sura = quran.getSuras().stream().filter(x -> x.getIndex() == id).findFirst();
		if (sura.isPresent()) {
			aya = sura.get().getAyas().stream().filter(x -> x.getIndex() == ayaId).findFirst().get().getText();
		}
		logger.info("Get ayat ends "+ (System.currentTimeMillis()-s));
		return aya;
	}
	
	
	

}
