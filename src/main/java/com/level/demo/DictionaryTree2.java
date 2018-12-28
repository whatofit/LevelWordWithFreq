package com.level.demo;

//https://baike.baidu.com/item/字典树/9825209?fr=aladdin&fromid=517527&fromtitle=Trie树
    
//Trie树：统计词频、排序、查找
//Trie树利用字符串的公共前缀降低了查询时间的开销，提高了查询的效率。
//字典树的插入，删除和查找都非常简单，用一个一重循环即可。
//1. 从根节点开始一次搜索
//2. 取得要查找关键词的第一个字母，并根据该字母选择对应的子树并转到该子树继续进行检索
//3. 在相应的子树上，取得要查找关键词的第二个字母，并进一步选择对应的子树进行检索
//4. 迭代过程...
//5. 在某个节点处，关键词的所有字母已被取出，则读取附在该节点上的信息，即完成查找
public class DictionaryTree2 {
    private int SIZE = 26;
    private TreeNode root;// 字典的根

    public DictionaryTree2() {
        root = new TreeNode();
    }

    private class TreeNode {
        private int num; // 词频统计
        private TreeNode[] son;// 每一层都是由26字母开头的，即所有的节点
        private boolean isWord;// 是不是最后一个节点
        private char val;// 节点的值

        TreeNode() {
            num = 1;
            son = new TreeNode[SIZE];
            isWord = false;
        }
    }

    public void insert(String str) {
        if (str == null || str.length() == 0)
            return;
        TreeNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++) {
            int pos = letters[i] - 'a';
            if (node.son[pos] == null) {
                node.son[pos] = new TreeNode();
                node.son[pos].val = letters[i];
            } else {
                node.son[pos].num++;
            }
            node = node.son[pos];
        }
        node.isWord = true;
    }

    public int countNums(String str) { // 计算单词的数量
        if (str == null || str.length() == 0) {
            return -1;
        }
        TreeNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++) {
            int pos = letters[i] - 'a';
            if (node.son[pos] == null) {
                return 0;
            } else {
                node = node.son[pos];
            }
        }
        return node.num;
    }

    // 在字典树中查找一个完全匹配的单词.
    public boolean search(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        TreeNode node = root;
        char[] letters = str.toCharArray();
        for (int i = 0, len = str.length(); i < len; i++) {
            int pos = letters[i] - 'a';
            if (node.son[pos] != null) {
                node = node.son[pos];
            } else {
                return false;
            }
        }
        return node.isWord;
    }

    public TreeNode getRoot() {
        return this.root;
    }

    public static void main(String[] args) {
        DictionaryTree2 tree = new DictionaryTree2();
        String[] strs = { "beer", "banana", "band", "bee", "you", "young", "that" };
        for (String str : strs) {
            tree.insert(str);
        }
        System.out.println(tree.search("bee"));
        System.out.println(tree.countNums("ba"));

    }
}
