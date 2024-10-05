package com.quran.api.controller;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quran.api.model.VerbInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VerbJsonReaderService {

	final static Logger logger = LoggerFactory.getLogger(VerbJsonReaderService.class);
    private List<VerbInfo> verbInfoList;
    private List<Map<String,Object>> nounsList;

    // Method to load data from JSON file at startup
    @jakarta.annotation.PostConstruct
    private void loadDataFromJson() {
    	logger.info("loadDataFromJson happening in startup");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Load the JSON file from resources
            InputStream jsonFile = new ClassPathResource("verbsJson.json").getInputStream();
            verbInfoList = objectMapper.readValue(jsonFile, new TypeReference<List<VerbInfo>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @jakarta.annotation.PostConstruct
    private void loadDataFromJsonForNouns() {
    	logger.info("loadDataFromJsonForNouns happening in startup");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Load the JSON file from resources
            InputStream jsonFile = new ClassPathResource("nouns.json").getInputStream();
            nounsList = objectMapper.readValue(jsonFile, new TypeReference<List<Map<String,Object>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<VerbInfo> getAllVerbs() {
    	logger.info("Getting verbs list");
        return verbInfoList;
    }

	public List<Map<String, Object>> getAllNouns() {
		logger.info("Getting nouns list");
		return nounsList;
	}
}

