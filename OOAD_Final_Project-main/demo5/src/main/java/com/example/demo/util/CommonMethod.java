package com.example.demo.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CommonMethod {





    /*
     * 向一个新的仓库没有.git 提交文件;
     * 使用Git git = new Git(clone(repoPath,clientPath))
     * */
    public static  RevCommit CommitFile(Git git,String path,String Content) throws GitAPIException, IOException {
        //创建新仓库;
        System.out.println(path);

//        Git git = Git.init().setDirectory(new File(repoPath)).call();
        File file = new File(path + File.separator + "hello1.c");
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(Content.getBytes());
        outStream.close();    //关闭文件输出流
        git.add().addFilepattern(".").call();
        return git.commit().setMessage("set hello1.c ").call();
    }




    /*
    * Clone 仓库;
    * 使用Git git = new Git(clone(repoPath,clientPath))
    * */
    public static Repository clone(String repoPath, String clientPath) throws Exception {
        File client = new File(clientPath);
        client.mkdir();
        try (Git result = Git.cloneRepository()
                .setURI(repoPath)
                .setDirectory(client)
                .call()) {
            return result.getRepository();
        }
    }

    /*
     * 获取某个已经存在.git 文件的仓库
     * */
    public static Repository get(String repoPath) throws Exception {
        return new FileRepository(repoPath);
    }



    /**/

    public static void push(String repoPath) throws Exception {
//        String user = gitRefreshBean.getGmGitUser();
//        String password = gitRefreshBean.getGmGitPassword();
        Git gitPush = Git.open(new File(repoPath));
        gitPush.add().addFilepattern(".").call();
        //获取状态区;
        Status status = gitPush.status().call();
        String a  = gitPush.push().getRemote();
        System.out.println(a);
//                setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password)).call();
//        log.info("push成功");

    }




}
