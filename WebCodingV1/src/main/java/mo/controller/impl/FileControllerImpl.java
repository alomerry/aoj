package mo.controller.impl;

import com.alibaba.fastjson.JSONObject;
import mo.controller.AbstractController;
import mo.controller.FileController;
import mo.core.Result;
import mo.core.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileControllerImpl extends AbstractController implements FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Override
    @ResponseBody
    @RequestMapping("/simditorImage")
    public Result simditorImage(MultipartFile image) {

        logger.info("image[{}]", image);

        JSONObject url = new JSONObject();
        url.put("url", "https://avatars1.githubusercontent.com/u/34118544?s=40&v=4");
        return new Result().setCode(ResultCode.OK).setData(url);
    }
}
