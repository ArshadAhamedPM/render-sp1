package com.quran.api.controller;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.quran.api.model.VerbInfo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VerbExcelReaderService {

    public List<VerbInfo> readVerbsFromExcel() {
        List<VerbInfo> verbInfoList = new ArrayList<>();

        try {
            // Load the file from resources
            InputStream excelFile = new ClassPathResource("verbs.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            // Iterate through each row
            for (Row row : sheet) {
                // Skip header row
                if (row.getRowNum() == 0) {
                    continue;
                }
                

                String verb = row.getCell(0).getStringCellValue();
                if(verb==null || verb.trim().equalsIgnoreCase("")) {
                	continue;
                }
                String root = row.getCell(1).getStringCellValue();
                String form = row.getCell(2).getStringCellValue();
                String translation = row.getCell(3).getStringCellValue();

                // Create a VerbInfo object and add to the list
                VerbInfo verbInfo = new VerbInfo(verb, root, form, translation,row.getRowNum());
                verbInfoList.add(verbInfo);
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return verbInfoList;
    }
}

