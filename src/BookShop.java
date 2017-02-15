import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

public class BookShop implements BookList {
	private HashMap<Book, Integer> stock;
	
	BookShop(String storeDataFilePath) {
		// TODO: Check file 
		stock = initStoreData(storeDataFilePath);
	}
	
	public HashMap<Book, Integer> initStoreData(String filePath) {
		stock = new HashMap<Book, Integer>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = "";
			// TODO: Regexp
			//Pattern bookPattern = Pattern.compile("(.+);(.+);.+;(\\d+)");
			
			while ((line = reader.readLine()) != null) {
				 //Matcher bookMatcher = bookPattern.matcher(line);
				String[] choppedLine = line.split(";");
				// TODO: Title could contain ';'
				String title = choppedLine[0];
				String author = choppedLine[1];
				String priceStr = choppedLine[2].replaceAll(",", "");
				BigDecimal price = new BigDecimal(priceStr);
				// TODO: Make sure this is >= 0
				int numInStock = Integer.parseInt(choppedLine[3]);
				Book book = new Book(title, author, price);
				// TODO: Check for duplicates
				stock.put(book, numInStock);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stock;
	}

	@Override
	public Book[] list(String searchString) {
		LinkedList<Book> resultsList = new LinkedList<Book>();
		searchString = searchString.toLowerCase().trim();
		for(Book book : stock.keySet()) {
			String title = book.getTitle().toLowerCase().trim();
			String author = book.getAuthor().toLowerCase().trim();
			if (searchString.equals("") || searchString.equals(title) || searchString.equals(author)) {
				resultsList.add(book);
			}
		}
		return resultsList.toArray(new Book[resultsList.size()]);
	}

	@Override
	public boolean add(Book book, int quantity) {
		if (quantity >= 0) {			
			if (stock.containsKey(book)) {
				stock.put(book, stock.get(book)+quantity);
			} else {
				stock.put(book, quantity);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int[] buy(Book... books) {
		int[] results = new int[books.length];
		for(int i=0; i<books.length; i++) {
			Book book = books[i];
			if (!stock.containsKey(book)) {
				results[i] = 2;
			} else if (stock.get(book) == 0) {
				results[i] = 1;
			} else {
				results[i] = 0;
				stock.put(book, stock.get(book)-1);
			}
		}
		return results;
	}
	
	public static void main(String[] args) {
		String path = "/Users/Joel/Documents/workspace/Books/src/books.txt";
		BookShop shop = new BookShop(path);
		Book[] search = shop.list("");
		boolean addResult = shop.add(search[0], 1337);
		int[] shoppingSpree = shop.buy(search);
	}
}

