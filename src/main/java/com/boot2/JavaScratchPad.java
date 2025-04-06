package com.boot2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JavaScratchPad {

    public static void main(String[] args) {



    }

    public static HttpURLConnection getTokenHttpURLConnection(String tokenUrl, String encodedAuth, int postDataLength) throws IOException
    {

    URL url = new URL(tokenUrl);
    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
    conn.setDoOutput(true);
    conn.setInstanceFollowRedirects(false);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    encodedAuth = "Basic QmJZeW1oNnlabzZaNEdoTzBuMExUaUJMZGdZcXFsdEMoR3R5U2VWRHL1RDgxc3RRbg==";
    conn.setRequestProperty("Authorization", encodedAuth);
    conn.setRequestProperty("charset", "utf-8");
    conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
    conn.setUseCaches(false);
    return conn;

}
}
