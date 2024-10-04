package com.quran.api.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.quran.api.model.ENoun;
import com.quran.api.model.VerbInfo;

import jakarta.xml.bind.JAXBException;

@RestController
public class Controller {
	
	@Autowired
	Services services;
	
	@Autowired
    private VerbJsonReaderService jsonReaderService;
	
	@Autowired
	VerbExcelReaderService excelReaderService;

	@GetMapping("/suras")
	public List<Map<String, Object>> getSuras() throws JAXBException, IOException{
		return services.getSuras();
		
	}
	
	@GetMapping("/sura/{id}")
	public List<Map<String, Object>> getSura(@PathVariable("id") int id) throws JAXBException, IOException {
		return services.getSura(id);
	}
	
	@GetMapping("/sura/{id}/{ayaId}")
	public String getAyah(@PathVariable("id") int id,@PathVariable("ayaId") int ayaId) throws JAXBException, IOException {
		return services.getAyah(id,ayaId);
	}

    @GetMapping("/verbs")
    public List<VerbInfo> getVerbs() {
        return jsonReaderService.getAllVerbs();
    }
    
    @GetMapping("/nouns")
    public List<Map<String, Object>> getNounsExtract(){
    	return jsonReaderService.getAllNouns();
    }
}
