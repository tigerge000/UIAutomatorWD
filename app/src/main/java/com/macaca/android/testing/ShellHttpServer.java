/*
 * The MIT License (MIT)
 * Copyright (c) 2015 xiaocong@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.macaca.android.testing;

import android.nfc.Tag;
import android.util.Log;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class ShellHttpServer extends NanoHTTPD {

    public static String TAG = "SHELL: ";

    private static String START_APP_SCRIPT = "/sdcard/startApp.sh";

    public ShellHttpServer(int port) {
        super(port);
    }

    private String router = "";

    private static volatile ShellHttpServer singleton;

    public void route(String uri) {

        this.router = uri;
    }


    public static ShellHttpServer getInstance(int port) {
        if (singleton == null) {
            synchronized (ShellHttpServer.class) {
                if (singleton == null) {
                    try {
                        singleton = new ShellHttpServer(port);
                    } catch (Exception ioe) {
                        Log.d(TAG,"Couldn't start server:\n");
                    }
                }
            }
        }
        return singleton;
    }


    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> headers, Map<String, String> params,
                          Map<String, String> files) {
        Log.d(TAG,String.format("URI: %s, Method: %s, params, %s, files: %s", uri, method, params, files));

        if ("/shell".equals(uri)) {

            JSONObject result = new JSONObject();

            String command = params.get("command").toString();

            try {
                result.put("output",ShellServer.cmd(command));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return newFixedLengthResponse(Response.Status.OK, "application/json", result.toString());

        }else if("/wd/hub/session".equals(uri)){

                    Map<String, String> body = new HashMap<String, String>();

                    String sessionId = "";

                    if(files.containsKey("postData")) {

                        JSONObject desiredCapabilities = JSONObject.parseObject(files.get("postData")).getJSONObject("desiredCapabilities");
                        String packageName = desiredCapabilities.getString("packageName");
//                        String cmd = "monkey -p " + packageName + " -c android.intent.category.LAUNCHER 1";
//                        ShellServer.cmd(cmd);
//                        ShellServer.execShell(START_APP_SCRIPT,packageName);

                        sessionId = "5d8499ad-3500-4a02-b7d5-0006694d8220";

                        JSONObject result = new JSONObject();
                        result.put("sessionId","5d8499ad-3500-4a02-b7d5-0006694d8220");
                        result.put("status",0);
                        result.put("value",desiredCapabilities);

                        return NanoHTTPD.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, "", result.toJSONString());
                    }

                    return null;
        }

        else {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found!!!");
        }
    }

}
