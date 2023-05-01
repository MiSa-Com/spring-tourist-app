package com.ms.tourist_app.application.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.ms.tourist_app.application.constants.AppStr;
import com.ms.tourist_app.config.exception.UploadImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class UploadFile {

    private Cloudinary cloudinary;

    public UploadFile(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    /**
     * Gửi lên 1 ảnh
     * **/
    public String getUrlFromFile(MultipartFile multipartFile) {
        try {
            Map<?, ?> map = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            return map.get(AppStr.CloudImage.secureUrl).toString();
        } catch (IOException e) {
            throw new UploadImageException(AppStr.Response.uploadImageFailed);
        }
    }

    /**
     * Gửi lên nhiều ảnh
     * **/
    public List<String> getMultiUrl(List<MultipartFile> multipartFiles) {
        List<String> urls = new ArrayList<>();
        try {
            for (int i = 0; i < multipartFiles.size(); i++) {
                Transformation transformation = new Transformation().width(1920).height(1080).crop("fill").gravity("center");
                Map<?, ?> map = cloudinary.uploader().upload(multipartFiles.get(i).getBytes(), ObjectUtils.asMap("transformation",transformation));

                urls.add(map.get(AppStr.CloudImage.secureUrl).toString());
            }
            return urls;
        } catch (IOException e) {
            throw new UploadImageException(AppStr.Response.uploadImageFailed);
        }
    }

    public void removeImageFromUrl(String url) {
        try {
            cloudinary.uploader().destroy(url, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new UploadImageException(AppStr.Response.uploadImageFailed);
        }
    }

    public static File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
