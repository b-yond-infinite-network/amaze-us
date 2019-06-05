package com.byond.savethehumans.util;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    protected static final Logger logger = LoggerFactory.getLogger( FileUtil.class);


    public static boolean fileExists(String filePathString){
        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()) {
            return true;
        } else {
            logger.info("File is not existed: {}", filePathString);
            return false;
        }
    }




    public static String simpleRefine(String str){
        return str.replaceAll("\\,", " ")
                .replaceAll("\\.", " . ")
                .replaceAll("\\.", " . ")
                .replaceAll("\\،", " ")
                .replaceAll("\\:", " ")
                .replaceAll("\\;", " ")
                .replaceAll("\\«", " ")
                .replaceAll("\\»", " ")
                .replaceAll("\\)", " ")
                .replaceAll("\\(", " ")
                .replaceAll("\\'", " ")
                .replaceAll("\\\"", " ")
                .replaceAll("\\#", " ");
    }
}
