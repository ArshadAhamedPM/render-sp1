package com.quran.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	
	private static final String CSV_URL = "https://docs.google.com/spreadsheets/d/1G9MpN17-eOBax1B0Fb0MVwkyPH3GEGd_j2vXNdOJ3ew/export?format=csv";
	
	final static Logger logger = LoggerFactory.getLogger(Services.class);
	   
	@jakarta.annotation.PostConstruct
	public void getSuraDetails() throws JAXBException, IOException {
		logger.info("getSuraDetails starts...");
		InputStream xmlFile = new ClassPathResource("quran-uthmani.xml").getInputStream();
		JAXBContext jaxbContext = JAXBContext.newInstance(Quran.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		quran = (Quran) unmarshaller.unmarshal(xmlFile);
		logger.info("getSuraDetails ...");
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
		logger.info("Get sura starts "+id);
		List<Map<String, Object>> list = new ArrayList<>();
		quran.getSuras().stream().filter(x-> x.getIndex() == id).findFirst().ifPresent(sura -> {
			sura.getAyas().stream().forEach(x -> {
				Map<String, Object> map = new HashMap<>();
				map.put("index", x.getIndex());
				map.put("name", x.getText());
				map.put("suraName", sura.getName());
				
				
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

	public List<Map<String, String>> getSheetData() {
		
		try {
            // Get CSV data from the Google Sheets URL
            RestTemplate restTemplate = new RestTemplate();
            String csvData = restTemplate.getForObject(CSV_URL, String.class);

            // Parse CSV data
            StringReader csvBodyReader = new StringReader(csvData);
            List<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader).getRecords();

            // Convert CSV records to JSON
            ObjectMapper mapper = new ObjectMapper();
            ArrayNode jsonArray = mapper.createArrayNode();

            for (CSVRecord record : records) {
                ObjectNode jsonObject = mapper.createObjectNode();
                record.toMap().forEach(jsonObject::put);
                jsonArray.add(jsonObject);
            }

            String jsonStr= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonArray);
            List<Map<String, String>> list = mapper.readValue(
            		jsonStr, new TypeReference<List<Map<String, String>>>() {}
            );
            return list;
        } catch (Exception e) {
            e.printStackTrace();
           
        }
		
		return null;
	}

	public String setSheetData(String name, String area, String contact) {

		try {
			logger.info("setSheetData {} {} {}",name,area,contact);
			String setDataUrl = "https://docs.google.com/forms/d/e/1FAIpQLSfMhu7G3HGe7LMh008AHzx5cok58GvG_mCPCSinU-PqByXwaA/formResponse?&submit=Submit?usp=pp_url&entry.107067118=#name&entry.1033209037=#area&entry.126908675=#contact"; 
			if(area==null || area.trim()=="") {
				area = "Najma";
			}
			setDataUrl = setDataUrl.replace("#name", name.strip()).replace("#area", area.strip()).replace("#contact", contact.strip());
			
			RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(setDataUrl, String.class);
			return "success";
		}catch (Exception e) {
			logger.error("error in set data {}",e);
		}
		return null;
	}
	
	
//	<ion-header>
//	  <ion-toolbar color="secondary">
//	    <ion-title>Favorite Verbs</ion-title>
//	  </ion-toolbar>
//	</ion-header>
//
//	<ion-content class="ion-padding">
//	  <ion-card *ngFor="let verb of favoriteVerbs; let i = index" color="secondary">
//	    <ion-card-header>
//	      <ion-card-title class="rtl-text">{{ verb.verb }}</ion-card-title>
//	    </ion-card-header>
//	    <ion-card-content>
//	      <p><strong>Root:</strong> {{ verb.root }}</p>
//	      <p><strong>Translation:</strong> {{ verb.translation }}</p>
//
//	      <!-- Dustbin icon positioned in the bottom right corner -->
//	      <ion-icon name="trash-bin" class="remove-icon" (click)="confirmRemoveVerb(i)"></ion-icon>
//	    </ion-card-content>
//	  </ion-card>
//	</ion-content>


}
