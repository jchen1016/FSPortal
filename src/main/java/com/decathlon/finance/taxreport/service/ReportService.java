package com.decathlon.finance.taxreport.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface ReportService {

    public void writeResponseAsPdf(final JasperPrint jp, HttpServletResponse response) throws JRException, IOException;

    public void writeResponseAsXlsx(final JasperPrint jp, HttpServletResponse response) throws IOException, JRException;

    public HttpEntity<byte[]> generatePdf(String fileName, InputStream blIs, InputStream glIs, String reportType, String companyType, String builderName) throws IOException, JRException;
}
