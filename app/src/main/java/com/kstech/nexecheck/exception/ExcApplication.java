package com.kstech.nexecheck.exception;

import android.app.Application;
import android.widget.Toast;


import com.kstech.nexecheck.utils.DateUtil;

import org.xutils.x;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;


/**
 * 全局异常处理上下文，覆盖自带Application
 * 系统未捕获异常处理，全局异常处理
 *
 */
public class ExcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);
        // 设置未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
    }


    /**
     * 自定义Myhandler 异常捕获类 捕获未知异常.
     */
    class MyHandler implements Thread.UncaughtExceptionHandler {

        // 一旦有未捕获的异常,就会回调此方法
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            System.out.println("发现一个未处理的异常, 但是被哥捕获了...");
            ex.printStackTrace();

            // 收集崩溃日志, 可以在后台上传给服务器,供开发人员分析
            try {
                //将crash log写入文件
                FileOutputStream fileOutputStream = new FileOutputStream("/storage/sdcard1/crash_log.txt", true);
                PrintStream printStream = new PrintStream(fileOutputStream);
                printStream.println(DateUtil.getDateTimeFormat(new Date())+"---------------------------------------------");
                ex.printStackTrace(printStream);
                printStream.flush();
                printStream.close();
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // 提示，然后结束程序
                Toast.makeText(getApplicationContext(),
                        "很抱歉，程序出错，即将退出:\r\n" + ex.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            }
            // 停止当前进程，防止下次进入白屏
            android.os.Process.killProcess(android.os.Process.myPid());
//	            System.exit(-1);
        }

    }
}

