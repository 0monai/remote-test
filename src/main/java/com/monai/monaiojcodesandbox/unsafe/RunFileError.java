package com.monai.monaiojcodesandbox.unsafe;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RunFileError {
    public static void main(String[] args) throws IOException, InterruptedException {
        String userdir = System.getProperty("user.dir");
        String filepath=userdir+ File.separator+"src/main/resources/木马程序.bat";
        Process process = Runtime.getRuntime().exec(filepath);
        process.waitFor();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //逐行读取
        String compileOutputLine;
        while ((compileOutputLine = bufferedReader.readLine()) != null) {
            System.out.println(compileOutputLine);
        }
        System.out.println("执行异常程序成功");
    }
}
