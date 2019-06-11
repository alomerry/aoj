package mo.controller;

import mo.core.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

public interface FileController {
    /**
     * 前端Simditor上传图片文件
     * @param image 图片
     * @return
     */
    Result addImage(MultipartFile image);

    /**
     * 获取图片
     * @param imageName 图片名称
     * @return
     */
    void getImages(String imageName, HttpServletResponse response) ;

}
