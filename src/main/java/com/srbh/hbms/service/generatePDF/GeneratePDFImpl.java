package com.srbh.hbms.service.generatePDF;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.util.Map;

@Service
public class GeneratePDFImpl implements GeneratePDF {

    private Logger logger = LoggerFactory.getLogger(GeneratePDFImpl.class);

    @Autowired
    TemplateEngine templateEngine;

    @Value("${directory}")
    String path;

    @Override
    public void generatePdf(String template, Map<String, Object> data, String fileName) {

        Context context =new Context();
        context.setVariables(data);

        String htmlPage = templateEngine.process(
                template,
                context
        );

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(path+fileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlPage);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }

    }
}
