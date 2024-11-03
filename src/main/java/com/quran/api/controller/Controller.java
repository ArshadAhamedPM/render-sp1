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
    
    @GetMapping("/sheetData")
    public List<Map<String, String>> getSheetData(){
    	return services.getSheetData();
    }
    
    @GetMapping("/setSheetData/{name}/{area}/{contact}")
    public String setSheetData(@PathVariable("name") String name,@PathVariable("area") String area,@PathVariable("contact") String contact){
    	return services.setSheetData(name,area,contact);
    }
    
    
    //https://docs.google.com/forms/d/e/1FAIpQLSeLDGac9w2UAj2J-txTRpwdWIMLe7ddunBRx69w-MV79CJ9qw/viewform?usp=pp_url&entry.1632252061=Arshad&entry.615037883=Mugalina&entry.261087118=66666666
}
