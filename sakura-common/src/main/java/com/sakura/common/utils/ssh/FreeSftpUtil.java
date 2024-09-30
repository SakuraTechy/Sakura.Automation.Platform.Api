package com.sakura.common.utils.ssh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuzhi
 * @date 2020年10月16日 下午4:44:10
 */
@Data
public class FreeSftpUtil {

    private static final Logger log = LoggerFactory.getLogger(FreeSftpUtil.class);

    private String host;
    private String username;
    private String password;
    private int port;
    private static ChannelSftp sftp = null;
    private Session sshSession = null;

    public FreeSftpUtil() {
//        this.host = ConfigUtil.getProperty("FreeSFTP_IP", Constants.CONFIG_APP);
//        this.port = Integer.parseInt(ConfigUtil.getProperty("FreeSFTP_Port", Constants.CONFIG_APP));
//        this.username = ConfigUtil.getProperty("FreeSFTP_UserName", Constants.CONFIG_APP);
//        this.password = ConfigUtil.getProperty("FreeSFTP_PassWord", Constants.CONFIG_APP);
    }

    public FreeSftpUtil(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    /**
     * 连接Sftp服务
     */
    public void connect() {
        try {
            log.info("开始连接sftp！");
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("sftp连接成功！");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("sftp连接失败！", e);
        }
    }

    /**
     * 连接Sftp服务
     */
    public void connect(String host, int port, String username, String password) {
        try {
            log.info("开始连接sftp！");
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("sftp连接成功！");
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("sftp连接失败！", e);
        }
    }

    /**
     * 关闭资源
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 下载单个文件
     *
     * @param remotePath     远程下载目录(以路径符号结束)
     * @param remoteFileName 下载文件名
     * @param localPath      本地保存目录(以路径符号结束)
     * @param localFileName  保存文件名
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        try {
            sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            mkdirs(localPath + localFileName);
            sftp.get(remoteFileName, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (SftpException e) {
            //e.printStackTrace();
            log.error("文件下载失败！", e);
        }
        return false;
    }

    /**
     * 批量下载文件
     *
     * @param remotPath  远程下载目录(以路径符号结束)
     * @param localPath  本地保存目录(以路径符号结束)
     * @param fileFormat 下载文件格式(以特定字符开头,为空不做检验)
     * @param del        下载后是否删除sftp文件
     * @return
     */
    public boolean batchDownLoadFile(String remotPath, String localPath, String fileFormat, boolean del) {
        try {
            Vector v = listFile(remotPath);
            if (v.size() > 0) {
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        if (fileFormat != null && !"".equals(fileFormat.trim())) {
                            if (filename.startsWith(fileFormat)) {
                                if (this.downloadFile(remotPath, filename, localPath, filename) && del) {
                                    deleteSFTP(remotPath, filename);
                                }
                            }
                        } else {
                            if (this.downloadFile(remotPath, filename, localPath, filename) && del) {
                                deleteSFTP(remotPath, filename);
                            }
                        }
                    }
                }
            }
        } catch (SftpException e) {
            //e.printStackTrace();
            log.error("文件下载失败！", e);
        }
        return false;
    }

    /**
     * 上传单个文件
     *
     * @param localPath      本地上传目录(以路径符号结束)
     * @param localFileName  上传的文件名
     * @param remotePath     远程保存目录
     * @param remoteFileName 保存文件名
     */
    public boolean uploadFile(String localPath, String localFileName, String remotePath, String remoteFileName, boolean del) {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            if (del) {
                deleteFile(localPath + localFileName);
            }
            return true;
        } catch (FileNotFoundException | SftpException e) {
            //e.printStackTrace();
            log.error("文件上传失败！", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 批量上传文件
     *
     * @param remotePath 远程保存目录
     * @param localPath  本地上传目录(以路径符号结束)
     * @param del        上传后是否删除本地文件
     */
    public boolean bacthUploadFile(String localPath, String remotePath, boolean del) {
        try {
            File file = new File(localPath);
            File[] files = file.listFiles();
            createDir(remotePath);
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].isFile() && !files[i].getName().contains("bak")) {
                    if (this.uploadFile(localPath, files[i].getName(), remotePath, files[i].getName(), del) && del) {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("文件上传失败！", e);
        }
        return false;
    }

    /**
     * 上传文件
     * 文件内容复制
     * @param localFile  指定本地文件
     * @param remoteFile 配置服务路径
     * @param realpath   服务器具体路径
     * @throws SftpException
     */
    public void uploadDirectory(String localFile, String remoteFile, String realpath) throws SftpException {
        File file = new File(localFile);
        //上传sftp目录
        String pathOut= remoteFile+"/" + realpath;
        if(file.exists()){
            try {
                sftp.cd(pathOut);
            } catch (Exception e) {
                // 目录不存在，则创建文件夹
                String[] dirs = pathOut.split("/");
                String tempPath = "";
                int index = 0;
                mkdirDir(dirs, tempPath, dirs.length, index);
            }
            if (file.isDirectory()) {  //对象是否是文件夹
                File[] files = file.listFiles();
                if (files == null || files.length <= 0) {
                    log.info("读取文件错误");
                }
                String realpathtemp = realpath;
                //cd 目录
                String realpathcd="";
                for (File f : files) {
                    String fp = f.getAbsolutePath();
                    if (f.isDirectory()) {
                        realpathcd = remoteFile+"/" +realpathtemp  + "/" + f.getName();
                        realpath = realpathtemp  + "/" + f.getName();
                        try {
                            sftp.cd(realpathcd);
                        } catch (Exception e) {
                            String pathOutTemp= remoteFile+"/" + realpath.replaceAll("//","/");
                            String[] dirs = pathOutTemp.split("/");
                            String tempPath = "";
                            int index = 0;
                            mkdirDir(dirs, tempPath, dirs.length, index);
                        }
                        uploadDirectory(fp,remoteFile, realpath);
                    } else {
                        //OVERWRITE 覆盖文件
                        sftp.put(fp, remoteFile + "/" + realpath, ChannelSftp.OVERWRITE);
                    }
                }
            } else {
                String fp = file.getAbsolutePath();
                sftp.put(fp, remoteFile + "/" + realpath, ChannelSftp.OVERWRITE);
            }
        }
    }

    /**
     * 递归根据路径创建文件夹
     * @param dirs     根据 / 分隔后的数组文件夹名称
     * @param tempPath 拼接路径
     * @param length   文件夹的格式
     * @param index    数组下标
     * @return
     */
    public void mkdirDir(String[] dirs, String tempPath, int length, int index) {
        // 以"/a/b/c/d"为例按"/"分隔后,第0位是"";顾下标从1开始
        index++;
        if (index < length) {
            // 目录不存在，则创建文件夹
            tempPath += "/" + dirs[index];
        }
        try {
            sftp.cd(tempPath);
            if (index < length) {
                mkdirDir(dirs, tempPath, length, index);
            }
        } catch (SftpException ex) {
            try {
                sftp.mkdir(tempPath);
                sftp.cd(tempPath);
            } catch (SftpException e) {
                log.error("SFTP创建文件失败"+e.getMessage());
            }
            mkdirDir(dirs, tempPath, length, index);
        }
    }

    /**
     * 创建目录
     *
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }
            }
            this.sftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            //e.printStackTrace();
            log.error("创建目录失败！", e);
        }
        return false;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

    /**
     * 删除stfp文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("删除stfp文件失败！", e);
        }
    }

    /**
     * 如果目录不存在就创建目录
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public Vector listFile(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 目录
     * @return ret
     * @throws SftpException
     */
    public List<String> listFiles(String directory) throws SftpException {
        sftp.cd(directory);
        Vector<String> files = sftp.ls("*");
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            Object obj = files.elementAt(i);
            if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
                LsEntry entry = (LsEntry) obj;
                if (true && !entry.getAttrs().isDir()) {
                    ret.add(entry.getFilename());
                }
                if (true && entry.getAttrs().isDir()) {
                    if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                        ret.add(entry.getFilename());
                    }
                }
            }
        }
        log.info(String.valueOf(ret));
        return ret;
    }

    public static void main(String[] args) throws SftpException {
        try {
            FreeSftpUtil ftp = new FreeSftpUtil("172.18.1.118", 22, "king", "111111");
//        String localPath = System.getProperty("user.dir") + "/TestData/PCAP/";
//        String remotePath = "/sql/";
            String localPath = "D:/Sakura/Sakura.Automation.Platform/automation/Sakura.Web.UI.Automation.Test/src/test/java/TestRunXml";
            String remotePath = "/Sakura.Web.UI.Automation.Test/src/test/java/1/";

            ftp.connect();
//        ftp.deleteSFTP(remotePath, "log.txt");
//        ftp.deleteSFTP(remotePath, "error.txt");
//        ftp.deleteSFTP(remotePath, "appium.txt");
            // ftp.uploadFile(localPath, "log.txt",remotePath, "log.txt");
//            ftp.uploadFile(localPath, "AAS_DBSG_SMOKE_001.xml", remotePath, "AAS_DBSG_SMOKE_001.xml", false);
//            ftp.bacthUploadFile(localPath,remotePath,false);
            ftp.uploadDirectory(localPath,remotePath,"");
            // ftp.downloadFile(remotePath, "appium.txt",localPath, "appium.txt");
            // ftp.batchDownLoadFile(remotePath, localPath, null, false);
            ftp.listFiles(remotePath);
            ftp.disconnect();
        } catch (Exception e) {
            log.error("", e);
            System.exit(0);
        }
    }
}