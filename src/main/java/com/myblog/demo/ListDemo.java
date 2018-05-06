package com.myblog.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//java-----求两个list的交集、并集、和差集
public class ListDemo {

    public ListDemo() {
    }

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>();
        list1.add("1111");
        list1.add("3333");
        list1.add("2222");   
        list1.add("2222");    
        list1.add("3333");  
        list1.add("3333");  
        list1.add("5555");
        list1.add("777");
        
//        List list2 = new ArrayList();
//        list2.add("3333");
//        list2.add("4444");
//        list2.add("5555");
//        list2.add("6666");
        
        List<String> list2 = new ArrayList<String>();
//      list2.add("000");
//      list2.add("1111");
//      list2.add("2222");
//      list2.add("3333");
//      list2.add("5555");
      list2.add("1111");
      list2.add("2222");
      list2.add("3333");
      list2.add("5555");

//         交集
//         list1.retainAll(list2);

         //差集
//         list1.removeAll(list2);

         // 并集
//       list1.addAll(list2);
         
//  // 无重复并集
//  list2.removeAll(list1);
//  list1.addAll(list2);
      
      List<String> list3 = new ArrayList<String>();
//      for (int i = list1.size() - 1; i>=0; i--) {
//          for(int j = i-1; j >= 0;j--) {
//              if (list1.get(j) == list1.get(i)) {
//                  list3.add(list1.get(i));
//                  break;
//              }
//          }
//      }
      for (int i = 0; i< list1.size() - 1; i++) {
          for(int j = i+1; j < list1.size() - 1;j++) {
              if (list1.get(j) == list1.get(i)) {
                  list3.add(list1.get(i));
                  break;
              }
          }
      }

        Iterator<String> it = list3.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
