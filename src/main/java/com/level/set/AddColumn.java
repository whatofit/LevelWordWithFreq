package com.level.set;
//package com.myblog.set;
////一个集合之中的内容不重复，唯一
//
////根据2个集合，添加(合并)一列
////比如集合1的每行如下：
////2	abandon	核	vt.离弃，丢弃；遗弃，抛弃；放弃
////5	able	基	a.有(能力、时间、知识等)做某事，有本事的
////5432	zone	核	n.地区,区域 v.分区,划分地带
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.stream.Collectors;
//
//import com.myblog.Constant;
//import com.myblog.model.Word;
//import com.myblog.util.CfgUtil;
//import com.myblog.util.RegEx;
//import com.myblog.util.ResourceUtil;
//import com.myblog.util.Utils;
//
////结合2的每行如下:
////able
////zoo
//
////1.读取列表1到list1，读取列表2到list2
////
//public class AddColumn {
//
//	// public static int mergeType = 1;// 0代表合并研纲;1合并中小学+高考大纲
//
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		// 1.读取文件路径/#被加数augend/summand:#加数addend
//		String cfg_augend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_augend");
//		String cfg_addend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_addend");
//		String cfg_sum_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_sum_result");
//
//		// 2.处理原文件集合为word list
//		RegEx.spelling_Idx = 1;
//		Map<String, String> augendMap = loadWordList(cfg_augend);
//		RegEx.spelling_Idx = 1;
//		Map<String, String> addendMap = loadWordList(cfg_addend);
//
//		// List<String> wordLines = new ArrayList<String>();
//		// Map<String, String> wordMap = new HashMap<String, String>();
//		boolean isContinue = true;
//		while (isContinue) {
//			isContinue = false;
//			for (Iterator<Entry<String, String>> it = augendMap.entrySet().iterator(); it.hasNext();) {
//				Map.Entry<String, String> entry = it.next();
//				String augendKeyWord = (String) entry.getKey();
//				String augendMapValue = (String) entry.getValue();
//				// colo(u)r单词在考研单词词义的备注中
//				if (addendMap.containsKey(augendKeyWord)) {// 在addendMap中找augendWord
//					// newLine += "\t" + addendMap.get(word);
//					augendMap.put(augendKeyWord, augendMapValue + "\t" + addendMap.get(augendKeyWord));
//					addendMap.remove(augendKeyWord);
//					isContinue = true;
//				} else {
//					// System.out.println("augendMapValue.trim=" + augendMapValue);//.trim()
//					String[] augendField = augendMapValue.split("\\s"); // .trim()
//					if (augendField != null && augendField.length > 2) {// CET有衍生词
//						// System.out.println("augendMapValue,field=" + Arrays.toString(field));
//						String[] words = augendField[2].replaceAll("\"", "").split(",");// 分开CET衍生词
//						for (int i = 0; words != null && i < words.length; i++) {// 循环CET衍生词
//							String derivedWord = RegEx.removeBrackets(words[i]);// 规范化衍生词
//							if (addendMap.containsKey(derivedWord)) {// 若在addendMap的key中找到augendWord的衍生词derivedWord
//								String addendLine = addendMap.get(derivedWord);
//								String newLine = "";
//								if (mergeType == 0) {
//									if (augendMapValue.contains("研纲")) {
//										String[] addendField = addendLine.trim().split("\\s"); // 后续,拼接
//										newLine = augendMapValue + "," + addendField[1];
//									} else {
//										newLine = augendMapValue + "\t" + addendLine;// 第一次'\t'拼接
//									}
//								} else {
//									if (augendMapValue.contains("小初高")) {
//										String[] addendField = addendLine.trim().split("\\s"); // 后续,拼接
//										newLine = augendMapValue + "," + addendField[2];
//										// System.out.println("augendMapValue.newLine=" + newLine);
//									} else {
//										newLine = augendMapValue + "\t" + addendLine;// 第一次'\t'拼接
//									}
//								}
//								augendMap.put(augendKeyWord, newLine);
//								augendMapValue = augendMap.get(augendKeyWord);// key已变化，重新取key
//								addendMap.remove(derivedWord);
//								isContinue = true;
//							}
//						}
//					}
//				}
//				if (addendMap.size() == 0) {
//					break;
//				}
//			}
//		}
//
//		for (Iterator<Entry<String, String>> it = addendMap.entrySet().iterator(); it.hasNext();) {
//			Map.Entry<String, String> entry = it.next();
//			String word = (String) entry.getKey();
//			String addendLine = (String) entry.getValue();
//			String[] field = addendLine.trim().split("\\s"); //
//			String newLine = "";
//			if (mergeType == 0) {
//				newLine = "无\t" + field[1] + "\t\t" + addendLine;// 前面是四六级+word+衍生word三列
//			} else {
//				newLine = "无\t" + field[2] + "\t\t\t\t" + addendLine;// 前面是关键词+四六级+word+衍生word三列+研纲+多个都逗号分隔的词汇
//			}
//			augendMap.put(word, newLine);
//		}
//		List<String> wordLines = Utils.SortMap(augendMap, false);
//		// 3.保存word list
//		String wordFile = Constant.PATH_RESOURCES + cfg_sum_result;
//		ResourceUtil.writerFile(wordFile, wordLines, false);
//		long endTime = System.currentTimeMillis();
//		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
//	}
//
//	public static Map<String, String> loadWordList(String file) {
//		System.out.println("parse word file: " + file);
//		List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
//		Map<String, String> wordMap = words.parallelStream()
//				.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim())).map(RegEx::split2Word)
//				.distinct() // 剔重
//				// .collect(Collectors.toSet());
//				.collect(Collectors.toMap(Word::getSpelling, Word::getSentences));
//		System.out.println("unique words count: " + wordMap.size());
//		return wordMap;
//	}
//
//}
