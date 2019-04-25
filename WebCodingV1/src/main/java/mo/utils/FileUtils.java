package mo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 将Zip压缩包 inputFile 解压到 filepath目录下
     *
     * @param inputFile 源文件
     * @param filePath  解压目录
     * @return 是否内部文件都是.in或者.out
     * @throws FileNotFoundException
     */
    public static boolean ZipFileDecompression(File inputFile, String filePath) throws FileNotFoundException {
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(inputFile));
        BufferedInputStream binIn = new BufferedInputStream(zipIn);
        File fout = null;
        ZipEntry entry;
        boolean resultFlag = true;
//        logger.info("文件[{}]开始解压!", inputFile);
        try {
            while ((entry = zipIn.getNextEntry()) != null && resultFlag) {
                fout = new File(filePath, entry.getName());
                if (!fout.exists()) {
//                    logger.info("创建父文件夹!");
                    (new File(fout.getParent())).mkdirs();
                }

                if (!StringUtils.equals(getFileType(fout.getName()), "in") && !StringUtils.equals(getFileType(fout.getName()), "out")) {
                    resultFlag = false;
                }

                FileOutputStream out = new FileOutputStream(fout);
                BufferedOutputStream Bout = new BufferedOutputStream(out);
                int b;
                while ((b = binIn.read()) != -1) {
                    Bout.write(b);
                }
                Bout.close();
                out.close();
//                logger.info("文件[{}]解压成功!", fout.getName());
            }
            logger.info("文件[{}]解压至[{}]成功!", inputFile.getName(), filePath);
            binIn.close();
            zipIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultFlag;
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
    }
}
