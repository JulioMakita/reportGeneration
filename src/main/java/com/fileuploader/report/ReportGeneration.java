package com.fileuploader.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fileuploader.model.ReportType;

import java.util.List;

public interface ReportGeneration<T> {

    String generate(List<T> object, ReportType reportType) throws JsonProcessingException;
}
