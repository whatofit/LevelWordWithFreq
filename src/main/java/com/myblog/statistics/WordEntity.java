package com.myblog.statistics;

/**
 * Created by IntelliJ IDEA.
 * User: FLY
 * Date: 11-9-13
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class WordEntity implements Comparable<WordEntity> {
    public WordEntity() {
        // TODO Auto-generated constructor stub
    }
    private String key;
    private Integer count;
    public WordEntity (String key, Integer count) {
        this.key = key;
        this.count = count;
    }
    public int compareTo(WordEntity o) {
        int cmp = count.intValue() - o.count.intValue();
        return (cmp == 0 ? key.compareTo(o.key) : -cmp);
        //只需在这儿加一个负号就可以决定是升序还是降序排列  -cmp降序排列，cmp升序排列
        //因为TreeSet会调用WorkForMap的compareTo方法来决定自己的排序
    }
 
   @Override
    public String toString() {
        return key + " 出现的次数为：" + count;
    }
 
   public String getKey() {
        return key;
    }
 
   public Integer getCount() {
        return count;
    }
}