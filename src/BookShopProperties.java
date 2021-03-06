import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.junit.Test;

@RunWith(JUnitQuickcheck.class)
public class BookShopProperties {
	BookShop shop = new BookShop("/Users/Joel/Documents/workspace/Books/src/books.txt");
	
	@Property public void concatenationLength(String s1, String s2) {
        assertEquals(s1.length() + s2.length(), (s1 + s2).length());
    }
	
	@Test public void emptySearch() {
		assertEquals(shop.list(""), 0);
	}
}