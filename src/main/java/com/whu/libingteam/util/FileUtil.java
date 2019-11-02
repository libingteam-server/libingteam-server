//package com.whu.libingteam.util;
//
//import cc.eamon.open.security.CodeMethod;
//import cc.eamon.open.security.SecurityFactory;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.Properties;
//
///**
// * 文件管理
// */
//
//public class FileUtil {
//
//    /**
//     * 可以上传的最大文件大小（字节数）
//     */
//    public static final int FILE_MAX_SIZE = 102400000;
//
//
//    /**
//     * 根目录
//     */
//    private static String basePath;
//    /**
//     * 图片
//     */
//    private static String imageFile;
//
//    /**
//     * FLASH
//     */
//    private static String flashFile;
//
//
//    //加载配置
//    static {
//        Properties prop = new Properties();
//        Resource resource = new ClassPathResource("file.properties");
//        try {
//            prop.load(resource.getInputStream());
//            basePath = prop.getProperty("base.path");
//            imageFile = prop.getProperty("image.file");
//            flashFile = prop.getProperty("flash.file");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    /**
//     * 禁止实例化
//     */
//    private FileUpload() {
//        throw new RuntimeException("You can not new an instance of this class!");
//    }
//
//    /**
//     * 保存文件
//     */
//    public static String save(MultipartFile file, Path path, String replace) throws IOException {
//        FileItem fileItem = FileUpload.parse(file);
//        String savePath = (path.getPath() + fileItem.getFileName()).replace("{placeholder}", replace);
//        FileUpload.save(fileItem, savePath);
//        System.out.println(savePath);
//        return savePath;
//    }
//
//    private static void save(FileItem fileItem, String path) throws IOException {
//        File file = new File(FileUpload.basePath + path);
//
//        //如果文件存在，则删除
//        if (file.exists()) {
//            file.delete();
//        }
//
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//
//        file.createNewFile();
//
//        FileOutputStream out = new FileOutputStream(file);
//        out.write(fileItem.getFileContent());
//        out.flush();
//        out.close();
//    }
//
//    /**
//     * 解析上传的文件
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    private static FileItem parse(MultipartFile file) throws IOException {
//        return new FileItem(file.getBytes(),
//                SecurityFactory.getCodeMethod(CodeMethod.SUPPORT.CODE_MD5).encode(file.getBytes()) + "."
//                        + FileFormat.judge(file.getInputStream()).toString().toLowerCase());
//    }
//
//    private static class FileItem {
//        private byte[] fileContent;
//        private String fileName;
//
//        FileItem(byte[] fileContent, String fileName) {
//            this.setFileContent(fileContent);
//            this.setFileName(fileName);
//        }
//
//        byte[] getFileContent() {
//            return fileContent;
//        }
//
//        void setFileContent(byte[] fileContent) {
//            this.fileContent = fileContent;
//        }
//
//        String getFileName() {
//            return fileName;
//        }
//
//        void setFileName(String fileName) {
//            this.fileName = fileName;
//        }
//    }
//
//
//
//}
