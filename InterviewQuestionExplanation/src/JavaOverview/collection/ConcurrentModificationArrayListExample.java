package JavaOverview.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConcurrentModificationArrayListExample {
	 public static void main(String[] args) {
	       List<String> list = new ArrayList<String>();
	       list.add("a");
	        list.add("b");
	        list.add("c");
	       
	        Iterator<String> iterator = list.iterator();
	       
	        while(iterator.hasNext()){
	         //iterator.remove(); will throw IllegalStateException
	         String str = iterator.next();
	         System.out.print(str+" ");
	         
	         if(str.equals("b"))
	           list.add("b2");   //will throw ConcurrentModificationException
	         
	        }
	       
	        System.out.println("\nAfter iteration list is : "+list);
	       
	    }
}

/*OUTPUT

a b Exception in thread "main" java.util.ConcurrentModificationException
    at java.util.ArrayList$Itr.checkForComodification(Unknown Source)
    at java.util.ArrayList$Itr.next(Unknown Source)
    at com.javaMadeSoEasy.ConcurrentModificationArrayListExample.main(ConcurrentModificationArrayListExample.java:20)
 
*/