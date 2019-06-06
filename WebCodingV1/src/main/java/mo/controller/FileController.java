package mo.controller;

import mo.core.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileController {
    Result simditorImage(MultipartFile image);
}
