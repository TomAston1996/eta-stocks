package com.tomaston.etastocks.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    /**
     * Used for reading JSON files for testing
     * @param filePath file path of the JSON file
     * @return byte array in string format
     * @throws IOException -
     */
    public static String readFromFileToString(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }
}
