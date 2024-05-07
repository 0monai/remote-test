package com.monai.monaiojcodesandbox.unsafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileError {
    public static void main(String[] args) throws IOException {
        String userdir = System.getProperty("user.dir");
        String filepath=userdir+ File.separator+"src/main/resources/application.yml";
        List<String> allLines = Files.readAllLines(Paths.get(filepath));
        System.out.println(String.join("\n", allLines));
    }
}
