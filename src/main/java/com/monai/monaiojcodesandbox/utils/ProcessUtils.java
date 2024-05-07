package com.monai.monaiojcodesandbox.utils;

import cn.hutool.core.util.StrUtil;
import com.monai.monaiojcodesandbox.model.ExecuteMessage;
import org.springframework.util.StopWatch;

import java.io.*;

/**
 * 进程工具类
 */
public class ProcessUtils {
    /**
     * 执行进程并获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runProcessAndGetMessage(Process runProcess,String opName) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            //等待程序执行 获取错误码
            int exitValue = runProcess.waitFor();
            executeMessage.setExitValue(exitValue);

            //正常退出
            if (exitValue == 0) {
                System.out.println("编译成功");
                //分批获取进程的输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine).append("\n");
                }
                executeMessage.setMessage(compileOutputStringBuilder.toString());
            } else {
                System.out.println("编译失败,错误码" + exitValue);
                //分批获取进程的输出
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                StringBuilder compileOutputStringBuilder = new StringBuilder();
                //逐行读取
                String compileOutputLine;
                while ((compileOutputLine = bufferedReader.readLine()) != null) {
                    compileOutputStringBuilder.append(compileOutputLine).append("\n");
                }
                //分批获取进程的异常输出
                BufferedReader errorBufferedReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
                StringBuilder errorCompileOutputStringBuilder = new StringBuilder();
                //逐行读取
                String errorCompileOutputLine;
                while ((errorCompileOutputLine = errorBufferedReader.readLine()) != null) {
                    errorCompileOutputStringBuilder.append(errorCompileOutputLine).append("\n");
                }
                executeMessage.setErrorMessage(errorCompileOutputStringBuilder.toString());
            }
            stopWatch.stop();
            executeMessage.setTime(stopWatch.getLastTaskTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  executeMessage;
    }

    /**
     * 执行交互式进程并获取信息
     * @param runProcess
     * @param opName
     * @return
     */
    public static ExecuteMessage runInteractProcessAndGetMessage(Process runProcess,String opName,String args) {
        ExecuteMessage executeMessage = new ExecuteMessage();

        try {
            InputStream inputStream = runProcess.getInputStream();
            OutputStream outputStream = runProcess.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            String[] split = args.split(" ");
            String join = StrUtil.join("\n", split) + "\n";
            outputStreamWriter.write(join);
            //相当于按了回车 执行输入的发送
            outputStreamWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            StringBuilder compileOutputStringBuilder = new StringBuilder();
            //逐行读取
            String compileOutputLine;
            while ((compileOutputLine = bufferedReader.readLine()) != null) {
                compileOutputStringBuilder.append(compileOutputLine);
            }
            executeMessage.setMessage(compileOutputStringBuilder.toString());
            outputStreamWriter.close();
            outputStream.close();
            inputStream.close();
            runProcess.destroy();
            //等待程序执行 获取错误码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  executeMessage;
    }

}
