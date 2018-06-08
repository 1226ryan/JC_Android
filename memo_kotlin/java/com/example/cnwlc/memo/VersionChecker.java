package com.example.cnwlc.memo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VersionChecker {

    private static VersionChecker instance;
    public static VersionChecker getInstance() {
        if(instance == null)
            instance = new VersionChecker(context);

        return instance;
    }

    private static Activity context;
    public VersionChecker(Activity context) {
        this.context = context;
    }


    // 마켓에서 앱 버전 가져오기
    public static String getMarketVersion() {
        String url = "마켓에 올라와 있는 주소";
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

    // 안드로이드 스튜디오에서 작성한 버전 가져오기
    public static String getAppVersion() {
        String device_version = "";
        try {
            device_version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return device_version;
    }

    public void getVersionCompare() {
        if (getMarketVersion().compareTo(getAppVersion()) > 0) {
            Toast.makeText(context, "버전 업데이트가 필요합니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "버전이 같습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
