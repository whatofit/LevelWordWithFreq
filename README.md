# LevelWordWithFreq
Level Word With Freq

DumpANC2FreqWordDB

你了解吗？各级英语考试对词汇量的要求
中考 1500~1600
高考 3000~3500
CET-4 4000~4500 大学英语四级考试
CET-6/考研 5500~6000 大学英语六级考试、硕士/博士研究生入学考试
TEM-4 6000~7000 英语专业四级水平考试!
TEM-8 10000~12000 英语专业八级水平考试
SAT 10000+(12000左右) 学术能力评估测试,俗称美国高考
ACT 6000-8000也有人认为在10000+
GMAT 12000+ 国外MBA入学英语考试
IELTS/iBT(TOEFL)(Internet Based Test) 8000~15000+ 雅思/新托福英语考试 TOEFL iBT(Internet Based Test)
GRE 20000+ 美国研究生入学考试

雅思大概10000词汇 托福是1万 GRE是2万

初中：  几种说法：
1、2500~3500个单词；
2、约3000个左右单词；
3、至少2000个单词；

高中：  几种说法：
1、4000~5000个单词；
2、约4500个左右单词；
3、至少3500个单词； 

英语四级：  几种说法：
1、5000~6000个单词；
2、约5500个左右单词；
3、至少4500个单词； 

英语六级：  几种说法：
1、英语四级+1200个以上单词；
2、约6600个左右单词；
3、至少6000个单词； 


# NewOrUpdateLevelDict
New Or Update Level Dict

http://www.anc.org/data/anc-second-release/frequency-data/

一.第一版功能：
1.把单词按照分频排列，再把单词分级(60/100个词汇一个级别,1*60;2*30;3*20;4*15;5*12:6*10)
2.把单词(按年级/能力考试/)分级
3.


增加单词列表的阶段/add the stage of word list
create

ByLevelWordList


Merge to WordTable
添加指定阶段/Level的单词列表
Add the word list at the specified stage/Level
Update 

向表格中添加指定阶段的单词列表/把指定阶段的单词列表添加到表格中
Add a list of words at the specified stage to the table

添加单词的阶段/The stage of adding words


(不考虑在不同level之间交错的单词，以低level为准)
1.先add高level的单词，再后add低level的单词（低level单词可覆盖高level单词）
2.在add之前，保证各个level单词的集合无交集。


1.可读取一个或多个已配置的数据库“列名/阶段(小学/四级)”及对应的“列记录level值”到内存
Map<String, Map<String, String>>
word：列，level值
------------------
word = word.replaceAll("[()\\d]", "");// 去掉序号
Map<String, String> mapWord = mapResult.get(word);
if (mapWord == null) {
    mapWord = new HashMap<String, String>();
    mapWord.put(file[0], courseValue);
    mapResult.put(word, mapWord);       //新增
} else {
    mapWord.put(file[0], courseValue);  //修改
}

检查数据库的table中是否有


File 文件路径
路径以/开头则是指向磁盘根目录。如/usr/local/bin/xxx则是指向根目录中/usr目录
路径以文件（文件夹）名开头则是相对于user.dir目录下的文件。如example.xml实际指向的文件是System.getProperty("user.dir") + "example.xml"文件


CLASSPATH 资源路径
CLASSPATH 资源有class.getResource()与classLoader.getResource()两种获取方式

如果Test.class.getResource()资源是以/开头则指向CLASSPATH根目录
如果Test.class.getResource()是以文件（包名）开头则相对Test.class文件所在包查找资源
Test.class.getClassLoader().getResource()是以CLASSPATH根目录查找资源只能以文件（包名）开头


获取资源文件的方法说明：
       getResourceAsStream ()返回的是inputstream
       getResource()返回:URL
       Class.getResource("")    返回的是当前Class这个类所在包开始的为置
       Class.getResource("/") 返回的是classpath的位置
       getClassLoader().getResource("")  返回的是classpath的位置
       getClassLoader().getResource("/")  错误的!!
       
http://blog.csdn.net/ak913/article/details/7399056