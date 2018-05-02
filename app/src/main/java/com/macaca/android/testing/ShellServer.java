package com.macaca.android.testing;

import android.util.Log;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellServer {

    public static String cmd(String command) {
        Process resultProcess = process(command.concat("\n"));
        return getShellOut(resultProcess);

    }


    public static BufferedReader shellOut(Process ps) {
        BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return br;
    }

    public static String getShellOut(Process ps) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = shellOut(ps);
        String line;

        try {
            while ((line = br.readLine()) != null) {
//				sb.append(line);
                sb.append(line + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString().trim();
    }

    private static Process process(String command) {
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ps;
    }

    /**
     * 解决了 参数中包含 空格和脚本没有执行权限的问题
     * @param scriptPath 脚本路径
     * @param para 参数数组
     */
    public static void execShell(String scriptPath, String ... para) {
        try {
            String[] cmd = new String[]{scriptPath};
//            //为了解决参数中包含空格
            cmd= ArrayUtils.addAll(cmd,para);

            //解决脚本没有执行权限
            ProcessBuilder builder = new ProcessBuilder("chmod", "755",scriptPath);
            Process process = builder.start();
            process.waitFor();

            Process ps = Runtime.getRuntime().exec(cmd);
            int exitValue = ps.waitFor();

            if (0 != exitValue) {
                Log.d("shell","call shell failed. error code is :" + exitValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
