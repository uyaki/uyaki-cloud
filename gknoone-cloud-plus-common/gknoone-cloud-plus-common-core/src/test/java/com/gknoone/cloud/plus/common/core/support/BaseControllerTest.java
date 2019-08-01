package com.gknoone.cloud.plus.common.core.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BaseControllerTest {

    @Test
    public void handleResult() {
        BaseController baseController = new BaseController();
        List<String> x =new ArrayList<>();
        String jb = JSON.toJSONString(x);
        System.out.println(baseController.handleResult(jb));
    }

    @Test
    public void handleResult1() {
    }
}