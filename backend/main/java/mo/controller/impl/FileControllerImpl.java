package mo.controller.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.FileController;
import mo.core.Result;
import mo.core.ResultCode;
import mo.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
public class FileControllerImpl extends AbstractController implements FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    //TODO 限制上传图片大小
    @Override
    @ResponseBody
    @RequestMapping("/simditorImage")
    public Result addImage(MultipartFile image) {

        logger.info("image[{}]", image);

        JSONObject url = new JSONObject();
        if (!image.isEmpty()) {
            String fileName = System.currentTimeMillis() + StringUtils.generateString(4) + image.getOriginalFilename();
            String path = getHttpServletRequest().getServletContext().getRealPath("/images") + File.separator + fileName;

            File imageFile = new File(path);
            try {
                image.transferTo(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            url.put("url", "http://localhost:8088/api-oj/image?imageName=" + fileName);
        }

        return new Result().setCode(ResultCode.OK).setData(url);
    }

    @Override
    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public void getImages(@RequestParam String imageName, HttpServletResponse response) {

        logger.info("请求图片名称[{}]", imageName);
        FileInputStream fis = null;
        File image = new File(getHttpServletRequest().getServletContext().getRealPath("/images") + File.separator + imageName);
        try {
            fis = new FileInputStream(image);
        } catch (FileNotFoundException e) {
            logger.info("图片不存在");
            e.printStackTrace();
        }
        try {
            ImageIO.write(ImageIO.read(fis), getImageType(imageName), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getImageType(String imageName) {
        logger.info("图片格式[{}]", imageName.substring(imageName.lastIndexOf(".") + 1));
        return imageName.substring(imageName.lastIndexOf(".") + 1);
    }
}
