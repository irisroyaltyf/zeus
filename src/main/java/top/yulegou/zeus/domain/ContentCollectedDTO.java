package top.yulegou.zeus.domain;

import lombok.Data;

import java.util.*;

/**
 * 抓取内容返回结果
 * 一个页面抓取一份结果
 */
@Data
public class ContentCollectedDTO {
    /**
     * 抓取到的内容结果
     */
    private Map<String, String> fieldsRst;
    /**
     * 替换img, 使用field关联，下载一次后放在download中一个Content页面只会下载一次图片
     */
    private HashMap<String, Set<String>> fieldImagesSet;
    private HashMap<String, String> downloaded;
    /**
     * 抓取内容页面
     */
    private String url;
    private Integer taskId;
    private boolean success = false;

    public ContentCollectedDTO() {
        fieldsRst = new HashMap<>();
        fieldImagesSet = new HashMap<>();
        downloaded = new HashMap<>();
    }
    public void addFieldResult(String fieldName, String collected) {
        fieldsRst.put(fieldName, collected);
    }

    public void addImage(String fieldName, String imgUrl) {
        if (!fieldImagesSet.containsKey(fieldName)) {
            fieldImagesSet.put(fieldName, new HashSet<>());
        }
        fieldImagesSet.get(fieldName).add(imgUrl);
    }
    public void addImages(String fieldName, List<String> imgUrls) {
        if (!fieldImagesSet.containsKey(fieldName)) {
            fieldImagesSet.put(fieldName, new HashSet<>());
        }
        fieldImagesSet.get(fieldName).addAll(imgUrls);
    }
    public Set<String> getFieldImages(String field) {
        return fieldImagesSet.get(field);
    }
    public void addDownLoad(String oldImage, String newImage) {
        downloaded.put(oldImage, newImage);
    }
    public String getDownload(String oldImage) {
        if (downloaded.containsKey(oldImage)) {
            return downloaded.get(oldImage);
        }
        return null;
    }
}
