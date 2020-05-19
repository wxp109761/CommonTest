package com.example.CommonTest.CrawingNews;
        import android.util.Log;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;
public class Function {

    public static List<NewsItemModel> parseHtmlData(String result) {
        List<NewsItemModel> list = new ArrayList<>();
                Pattern pattern = Pattern
                .compile("<h1.*?article-title\">(.*?)</h1>.*?<div.*?\"article-sub\">.*?<span>(.*?)</span>.*?<span>.*?</span>.*?</div>.*?<div.*?\"article-content\"><div>(.*?)</div></div>");
        Matcher matcher = pattern.matcher(result);
  StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            System.out.println("ttt   "+matcher.group());
            NewsItemModel model = new NewsItemModel();
            model.setNewsDetailUrl(matcher.group(1).trim());
            model.setUrlImgAddress(matcher.group(2).trim());
            model.setNewsTitle(matcher.group(3).trim());
//            model.setNewsSummary(matcher.group(4).trim());

            sb.append("详情页地址：" + matcher.group(1).trim() + "\n");
            sb.append("图片地址：" + matcher.group(2).trim() + "\n");
            sb.append("标题：" + matcher.group(3).trim() + "\n");
//            sb.append("概要：" + matcher.group(4).trim() + "\n\n");

            list.add(model);
        }

        Log.e("----------------->", sb.toString()+"ggg");

        return list;
    }

}
