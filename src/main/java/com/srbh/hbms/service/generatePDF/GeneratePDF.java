package com.srbh.hbms.service.generatePDF;

import org.springframework.stereotype.Component;

import java.util.Map;

public interface GeneratePDF {

    void generatePdf(String template, Map<String, Object> data, String fileName);
}
