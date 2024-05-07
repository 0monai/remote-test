package com.monai.monaiojcodesandbox;


import com.monai.monaiojcodesandbox.model.ExecuteCodeRequest;
import com.monai.monaiojcodesandbox.model.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
