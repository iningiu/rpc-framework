package com.saum.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
public final class PropertiesFileUtil {
    public static Properties readPropertiesFile(String fileName){
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if(url != null){
            rpcConfigPath = url.getPath() + fileName;
        }

        Properties properties = null;
        try(InputStreamReader reader = new InputStreamReader(new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8)){
            properties = new Properties();
            properties.load(reader);
        }  catch (IOException e) {
            log.error("读取properties文件[{}]时出错",fileName);
        }
        return properties;
    }
}
