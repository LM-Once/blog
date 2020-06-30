package com.it.common;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;


/**
 * @author 18576756475
 * @version V1.0
 * @ClassName JGitUtils
 * @Description JGit 工具类
 * @Date 2020-01-03 14:38:00
 **/
public class JGitUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JGitUtils.class);


    /**
     * 克隆 仓库
     *
     * @param url       项目git地址
     * @param localPath clone到本地路径
     * @return
     */
    public static void cloneRepository(String url, String localPath) {
        try {
            LOGGER.info("JGitUtils {} : start clone ....");
            Git git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(new File(localPath))
                    .setCloneAllBranches(true)
                    .call();
            LOGGER.info("JGitUtils {} : clone finished ....");
        } catch (Exception e) {
            LOGGER.info("JGitUtils {} : clone failed");
            e.printStackTrace();
        }
    }

    /**
     * 切换分支
     *
     * @param localPath  项目路径
     * @param branchName 分支名称
     */
    public static void checkoutBranch(String localPath, String branchName) {
        String projectURL = localPath + "\\.git";

        Git git = null;
        try {
            git = Git.open(new File(projectURL));
            List<Ref> refs = git.branchList().call();
            if (branchNameExist(refs, branchName)) {
                git.checkout().setCreateBranch(false).setName(branchName).call();
            } else {
                git.checkout().setCreateBranch(true).setName(branchName).call();
            }
            LOGGER.info("JGitUtils {} : checkoutBranch success ....");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("JGitUtils {} : checkoutBranch fail ....");
        } finally {
            if (git != null) {
                git.close();
            }
        }
    }

    /**
     * Description:判断本地分支名是否存在
     *
     * @param refs       分支列表
     * @param branchName 分支名称
     * @author 18576756475
     * @date 2020年1月6日 下午2:49:46
     */
    public static boolean branchNameExist(List<Ref> refs, String branchName) throws GitAPIException {
        for (Ref ref : refs) {
            if (ref.getName().contains(branchName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Description: 提交代码
     * @param localPath   本地分支路径
     * @param pushMessage 提交信息注释
     * @author 18576756475
     * @date 2020年1月6日 下午2:49:46
     */
    public static void commit(String localPath, String pushMessage) {
        String projectURL = localPath + "\\.git";
        Git git = null;
        try {
            git = Git.open(new File(projectURL));
            git.pull().call();
            Status status = git.status().call();
            if (status.hasUncommittedChanges() == false) {
                LOGGER.info("无已修改文件");
                return;
            }
            git.add().addFilepattern(".").call();
            git.commit().setMessage(pushMessage).call();
            git.pull().call();
            git.push().call();
            LOGGER.info("提交成功");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("提交失败");
        } finally {
            if (git != null) {
                git.close();
            }
        }
    }

    public static void main(String[] args) {
        //1. clone
        /*JGitUtils.cloneRepository("http://alm.adc.com/shared/OlibScripts/_git/Olib_Connectivity_Wlan_Scripts",
                "F:\\music\\test");*/
        //2.切换分支
        JGitUtils.checkoutBranch("F:\\music\\test", "test1");
        //JGitUtils.commit("F:\\music\\test", "测试提交1");
    }
}
