package com.monai.monaiojcodesandbox.unsafe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 向服务器写入文件(植入危险程序)
 */
public class WriteFileError {
    public static void main(String[] args) throws IOException {
        String userdir = System.getProperty("user.dir");
        String filepath=userdir+ File.separator+"src/main/resources/木马程序.bat";
        String errorProgram="java -version 2>&1";
        Files.write(Paths.get(filepath), Arrays.asList(errorProgram));
        System.out.println("写入木马成功,你GG了 嘻嘻嘻");
    }
}
