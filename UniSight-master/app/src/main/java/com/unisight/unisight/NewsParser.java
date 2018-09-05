package com.unisight.unisight;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class NewsParser extends AsyncTask<Void, Void, HashMap<String, String>> {
    @Override
    protected HashMap<String, String> doInBackground(Void... voids) {
        HashMap<String, String> result = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://news.naver.com").get();
            Elements today_main_news_img = doc.select(".newsnow_imgarea");
            Elements today_main_news_txt = doc.select(".newsnow_tx_inner");
            for (Element element : today_main_news_img) {
                result.put(element.text(), element.getElementsByAttribute("href").attr("href"));
            }
            for (Element element : today_main_news_txt) {
                result.put(element.text(), element.getElementsByAttribute("href").attr("href"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
