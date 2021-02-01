package com.fileuploader.parser;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileParser<T> {

    List<T> read(MultipartFile file);
}
