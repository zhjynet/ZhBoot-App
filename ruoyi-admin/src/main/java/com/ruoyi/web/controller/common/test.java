package com.ruoyi.web.controller.common;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        String filePath = "E:/Users/zhangjy/Desktop/data_2.csv"; // 替换为您的txt文件的路径
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            double sum = 0;
            int i=0;
            while ((line = reader.readLine()) != null) {
                String a =  line.substring(23);
                double b = Double.parseDouble(a);
                i++;
                sum = sum+b;

            }
            System.out.println(sum/i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}