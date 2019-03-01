package com.digitalnet.push.net;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class RetrofitUtils {

      /**
       * List<String> filePaths  =====> List<MultipartBody.Part>
       * 这里的key则是后台服务器的字段名，即使H5中input的name的名字
       * 如果是单文件，filePaths的大小就为1
       * @param key
       * @param filePaths
       * @return
       */
      public static List<MultipartBody.Part> filesToMultipartBodyParts(String key, List<String> filePaths) {
            List<MultipartBody.Part> parts = new ArrayList<>(filePaths.size());
            for (String filePath : filePaths) {
                  File file = new File(filePath);
                  //这里的image/*表示上传的是相片的所有格式，你可以替换成你需要的格式
                  RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                  MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
                  parts.add(part);
            }
            return parts;
      }

      /**
       *   String  ====>  RequestBody
       * @param param
       * @return
       */
      public static RequestBody convertToRequestBody(String param) {
            //这里的text/plain表示参数都是文本
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
            return requestBody;
      }

      /**
       *   Map<String,String>  ====>  RequestBody
       * @param map
       * @return
       */
      public static RequestBody mapToRequestBody(Map<String,String> map) {
            String json= new Gson().toJson(map);//要传递的json
            return  RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
      }

      /**
       * map<String,String>  =====>  Map<String,RequestBody>
       * @param params
       * @return
       */
      public static Map<String,RequestBody> paramsToRequestBs(Map<String,String> params) {
            //参数的封装
            Map<String, RequestBody> bodys = new HashMap<>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                  bodys.put(entry.getKey(),RetrofitUtils.convertToRequestBody(entry.getValue()));
            }
            return bodys;
      }

      /**
       *   imgfile  ======>MultipartBody.Part
       * @param key
       * @param fileName
       * @param file
       * @return
       */
      public static MultipartBody.Part imgFileToMultipartBodyPart(String key, String fileName ,File file) {
            //这里的image/*表示上传的是相片的所有格式，你可以替换成你需要的格式
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, (fileName==null||fileName.length()==0)?file.getName():fileName, requestBody);
            return part;
      }
      /**
       *   file  ======>MultipartBody.Part
       * @param key
       * @param fileName
       * @param file
       * @return
       */
      public static MultipartBody.Part fileToMultipartBodyPart(String key, String fileName ,File file) {
            //这里的image/*表示上传的是相片的所有格式，你可以替换成你需要的格式
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, (fileName==null||fileName.length()==0)?file.getName():fileName, requestBody);
            return part;
      }
}
