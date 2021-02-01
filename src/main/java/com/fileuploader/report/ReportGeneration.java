package com.fileuploader.report;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ReportGeneration<T> {

    String generate(List<T> object) throws JsonProcessingException;
}
