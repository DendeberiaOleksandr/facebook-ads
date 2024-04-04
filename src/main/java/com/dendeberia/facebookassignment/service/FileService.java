package com.dendeberia.facebookassignment.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    File toFile(MultipartFile multipartFile) throws IOException;

}
