package com.quran.api.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.quran.api.model.ENoun;
import com.quran.api.model.VerbInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class VerbExcelReaderService {

	public List readVerbsFromExcel() {
		List<ENoun> verbInfoList = new ArrayList<>();
		Map<String, List<ENoun>> nounsList = null;
		List mapList = null;
		try {
			// Load the file from resources
			InputStream excelFile = new ClassPathResource("QuranNounMeaning.xlsx").getInputStream();
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

			// Iterate through each row
			for (Row row : sheet) {
				System.out.println("line number " + row.getRowNum());
				try {
					// Skip header row
					if (row.getRowNum() == 0) {
						continue;
					}

					String noun = row.getCell(0).getStringCellValue();
					if (noun == null || noun.trim().equalsIgnoreCase("")) {
						continue;
					}
					String no = row.getCell(1).getStringCellValue();
					String meaning = row.getCell(2).getStringCellValue();
					String ayah = row.getCell(3).getStringCellValue();

					// Create a VerbInfo object and add to the list
					ENoun nouns = new ENoun(noun, meaning, ayah, no);
					verbInfoList.add(nouns);
				} catch (Exception e) {
					System.out.println("error occureed " + e);
				}

			}

			nounsList = verbInfoList.stream().collect(Collectors.groupingBy(x -> x.getNoun()));
			mapList = new ArrayList<>();
			int c = 1;
			for (Entry<String, List<ENoun>> entry : nounsList.entrySet()) {
				var key = entry.getKey();
				var val = entry.getValue();
				var map = new HashMap<>();
				map.put("noun", key);
				var egList = new ArrayList<>();
				for (ENoun eg : val) {
					var egMap = new HashMap<>();
					egMap.put("meaning", eg.getMeaning());
					egMap.put("no", eg.getNo());
					egMap.put("ayah", eg.getAyah());
					egList.add(egMap);
				}
				map.put("egList", egList);
				map.put("count", egList.size());
				map.put("id", c);
				c++;
				mapList.add(map);
			}

			workbook.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapList;
	}
}
