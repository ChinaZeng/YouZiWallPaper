package com.youzi.service.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.youzi.framework.common.util.file.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConverterFactory extends Converter.Factory {
    private GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private ApiConverterFactory() {
    }

    public static ApiConverterFactory create(){
        return new ApiConverterFactory();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == File.class) {
            return new FileConverter();
        }
        return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    public static class FileConverter implements Converter<ResponseBody, File> {
        @Override
        public File convert(@NonNull ResponseBody responseBody) throws IOException {
            return FileUtil.writeFile(responseBody.byteStream(), FileUtil.getRandomFile(), false);
        }
    }
}
