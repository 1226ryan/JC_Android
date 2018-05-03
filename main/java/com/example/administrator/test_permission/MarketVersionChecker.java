package com.example.administrator.test_permission;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2018-04-03.
 *
 *
 *
 * gradle 추가
 // market version check
 implementation group: 'org.jsoup', name: 'jsoup', version: '1.10.2'
 */

public class MarketVersionChecker {
    public static String getMarketVersion(String packageName) {
        String url = "https://play.google.com/store/apps/details?id=" + packageName;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements currentVersionDiv = doc.select(".BgcNfc");
            Elements currentVersion = doc.select("div.hAyfc div span.htlgb");
            for(int i = 0; i<currentVersionDiv.size(); i++) {
                if(currentVersionDiv.get(i).text().equals("Current Version")) {
                    return currentVersion.get(i).text();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMarketVersionFast(String packageName) {
        String mData = "", mVer = null;
        try {
            URL mUrl = new URL("https://play.google.com/store/apps/details?id=" + packageName);
            HttpURLConnection mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) return null;

            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);

            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader mReader = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                while (true) {
                    String line = mReader.readLine();
                    if (line == null) break;
                    mData += line;
                }
                mReader.close();
            }
            mConnection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = mData.indexOf(startToken);
        if (index == -1) {
            mVer = null;
        } else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }
        return mVer;
    }
}
