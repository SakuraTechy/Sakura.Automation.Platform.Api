package com.sakura.common.utils.file;

import lombok.Getter;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import com.sakura.common.config.SakuraConfig;
import com.sakura.common.constant.Constants;
import com.sakura.common.exception.file.FileNameLengthLimitExceededException;
import com.sakura.common.exception.file.FileSizeLimitExceededException;
import com.sakura.common.exception.file.InvalidExtensionException;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.uuid.IdUtils;

/**
 * 文件上传工具类
 *
 * @author liuzhi
 */
public class FileUploadUtils
{
    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    @Getter
    private static final String defaultBaseDir = SakuraConfig.getProfile();

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws Exception
     */
    public static String upload(MultipartFile file) throws IOException
    {
        try
        {
            return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     * @throws InvalidExtensionException 文件校验异常
     */
    public static String upload(String baseDir, MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException
    {
        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file, allowedExtension);

        String fileName = extractFilename(file);

        String absPath = getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        file.transferTo(Paths.get(absPath));
        return getPathFileName(baseDir, fileName);
    }

    /**
     * 批量文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param files 上传的文件数组
     * @return 返回上传成功的文件名列表
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     */
    public static List<String> batchUpload(MultipartFile[] files, String baseDir) throws IOException {
        List<String> uploadedFileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = saveFile(file, baseDir);
            uploadedFileNames.add(fileName);
        }
        return uploadedFileNames;
    }

    /**
     * 保存文件到指定目录
     *
     * @param file       上传的文件
     * @param uploadDir  上传目录
     * @param overwrite  是否覆盖已存在的文件
     * @return 文件保存的完整路径
     * @throws IOException 如果文件保存失败
     */
    public static String saveFile(MultipartFile file, String uploadDir, boolean overwrite) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (uploadDir.isEmpty()) {
            throw new IllegalArgumentException("uploadDir is empty");
        }
        // 创建上传目录（如果不存在）
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 构建文件路径
        Path filePath = uploadPath.resolve(Objects.requireNonNull(file.getOriginalFilename()));

        // 检查文件是否已存在
        if (Files.exists(filePath) && !overwrite) {
            throw new IOException("File already exists: " + filePath);
        }

        // 保存文件
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString().replace("\\", "/");
    }

    /**
     * 保存文件到指定目录，覆盖已存在的文件
     *
     * @param file       上传的文件
     * @param uploadDir  上传目录
     * @return 文件保存的完整路径
     * @throws IOException 如果文件保存失败
     */
    public static String saveFile(MultipartFile file, String uploadDir) throws IOException {
        return saveFile(file, uploadDir, true);
    }

    /**
     * 保存文件到指定目录，使用唯一的文件名
     *
     * @param file       上传的文件
     * @param uploadDir  上传目录
     * @return 文件保存的完整路径
     * @throws IOException 如果文件保存失败
     */
    public static String saveFileWithUniqueName(MultipartFile file, String uploadDir) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // 创建上传目录（如果不存在）
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 生成唯一的文件名
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFileName);

        // 保存文件
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString().replace("\\", "/");
    }

    /**
     * 从网络下载文件流并保存到指定目录
     *
     * @param url           文件下载URL
     * @param authToken     认证令牌
     * @param targetDir     保存目标目录
     * @param filename      文件名
     * @return              保存的文件的绝对路径
     * @throws IOException  如果下载或保存文件失败
     */
    public static String downloadFile(String url, String authToken, String targetDir, String filename) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                new org.springframework.http.HttpEntity<>(headers),
                byte[].class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            byte[] fileData = response.getBody();
            // 确保目标目录存在
            Path dirPath = Paths.get(targetDir);
            Files.createDirectories(dirPath);
            // 构建文件路径并写入文件
            Path filePath = dirPath.resolve(filename);
            if (fileData != null) {
                Files.write(filePath, fileData);
            }
            // 返回文件的绝对路径
            return filePath.toAbsolutePath().toString().replace("\\", "/");
        } else {
            throw new IOException("下载失败: " + response.getStatusCode());
        }
    }

    /**
     * 删除指定路径的文件
     *
     * @param filePath 文件路径
     * @throws IOException 如果文件删除失败
     */
    public static void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
//        fileName = DateUtils.datePath() + "/" + IdUtils.fastUUID() + "." + extension;
        fileName = "/" + IdUtils.fastUUID() + "." + extension;
        return fileName;
    }

    public static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException
    {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists())
        {
            if (!desc.getParentFile().exists())
            {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    public static final String getPathFileName(String uploadDir, String fileName) throws IOException
    {
        int dirLastIndex = SakuraConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws InvalidExtensionException
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException
    {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE)
        {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }

        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension))
        {
            if (allowedExtension == MimeTypeUtils.IMAGE_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidImageExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.FLASH_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidFlashExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.MEDIA_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidMediaExtensionException(allowedExtension, extension,
                        fileName);
            }
            else if (allowedExtension == MimeTypeUtils.VIDEO_EXTENSION)
            {
                throw new InvalidExtensionException.InvalidVideoExtensionException(allowedExtension, extension,
                        fileName);
            }
            else
            {
                throw new InvalidExtensionException(allowedExtension, extension, fileName);
            }
        }

    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension)
    {
        for (String str : allowedExtension)
        {
            if (str.equalsIgnoreCase(extension))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file)
    {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension))
        {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    public static void main(String[] args) throws IOException {
        String BASE_URL = "http://172.23.1.230:8091/certificateApply/download?certificateId=27556";
        String authorization = "cd7b32c6d2524088bea28bf354cc0d19";
        String directoryPath = "D:/data/";
        String filename = "product-AAS-DBSG5000系列.zip";
        System.out.println(downloadFile(BASE_URL, authorization, directoryPath, filename));
    }
}
