package com.company;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class subtitles {
    public static void main(String[] args) {
        String subs = "";
        try {
            subs = new String(Files.readAllBytes(Paths.get("/Users/stevecsvihinka/Downloads/YiYi/CD1/Yi.Yi.2000.720p.BluRay.FLAC2.0.x264-EbP.srt")));
        } catch (IOException e) {
            System.out.println("That's either not the right file path, or not a text file");
        }

        String[] split = subs.split("\n");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("-->")) {
                SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
                Calendar cal = Calendar.getInstance();
                String firstTime = split[i].substring(0, 8);
                String secondTime = split[i].substring(16, 25);
                Date first = modifyTime(firstTime);
                Date second = modifyTime(secondTime);
                split[i] = time.format(first)+split[i].substring(8, 16)+time.format(second)+split[i].substring(26);
              }
            }
        String newSubs = String.join("\n", split);
        try(PrintWriter out = new PrintWriter("/Users/stevecsvihinka/Desktop/yiyi.srt")) {
            out.println(newSubs);
        } catch(FileNotFoundException e) {
            System.out.println("You're beat, man");
        }
    }

    public static Date modifyTime(String s) {
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        try {
            date = time.parse(s);
            cal.setTime(date);
            cal.add(Calendar.SECOND, -4);
            date = cal.getTime();
            System.out.println(time.format(date));
        } catch (Exception e) {
            System.out.println("Something went wrong. Are you sure the file is properly formatted?");
        }
        return date;
    }
}