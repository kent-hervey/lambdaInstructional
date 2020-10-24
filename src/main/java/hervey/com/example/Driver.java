package hervey.com.example;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Driver {
	
	public static void main(String[] args) {
		
		//start Peggy Fisher LI code
		
		Predicate<String> stringLen = (s) -> s.length() < 10;
		String testString = new String("Apples");
		System.out.println(stringLen.test(testString) + " - " + testString +" length is less than 10");
		
		//
		Predicate<String> theLength = (string)-> string.length() <12;
		String tester = new String ("the big one");
		System.out.println(theLength.test(tester) + " - " + tester + " length is less than 12");
		
		//
		
		Consumer<String> consumerStr = (s) -> System.out.println(s.toLowerCase());
		consumerStr.accept("The Quick Brown Fox Jumped Over the Lazy Dog.");
		
		Function<Integer,String> converter = (num)-> Integer.toString(num);
		System.out.println("length of 26:  " + converter.apply(26).length());
		
		Supplier<String> s = ()->"Java is fun";
		System.out.println(s.get());
		
		Integer f = 10;
		Integer g = 25;
		 
		BinaryOperator<Integer> add = (d,e)-> f + g;
		System.out.println("adds " + f + " + " + g + "  : "  + add.apply(g, f));
		
		
		UnaryOperator<String> str = (asdf)->asdf.toUpperCase();
		System.out.println(str.apply("This is my message in upper case"));
		
		IntFunction<String> intToString = num -> Integer.toString(num);
		System.out.println("expected value 3, actual value:  " + intToString.apply(123).length());
		
		//static method reference using ::
		IntFunction<String> intToString2 = Integer::toString;
		System.out.println("expected value 4, actual value:  " + intToString2.apply(4567).length());
		
		//lambdas made using a constructor
		Function<String,BigInteger> newBigInt = BigInteger::new;
		System.out.println("expected value: 1234568789, actual value:  " + newBigInt.apply("123456789"));
		
		//example of a lambda made from an instance method
		Consumer<String> print = System.out::println;
		print.accept("Coming to you directly from a lambda...");
		
		UnaryOperator<String> makeGreeting = "Hello, "::concat;
		System.out.println(makeGreeting.apply("Peggy"));
			
		//example of passing multiple values to a method using lambda expressions
		//notice data types not specified
		Calculate add2 = (a,b)-> a+b;
		Calculate difference = (a,b) -> Math.abs(a-b);
		Calculate divide = (a,b) -> (b != 0 ? a/b : 0);
		Calculate multiply = (c,d) -> c * d;
		
		System.out.println(add2.calc(3, 2));
		System.out.println(difference.calc(5, 10));
		System.out.println(divide.calc(12, 3));
		System.out.println(multiply.calc(2, 33));
		
		//using collections
		List<String> names = Arrays.asList("Paul", "Jane", "Michaela", "Sam");
		//way to sort prior to Java 8 lambdas
		Collections.sort(names, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		
		//first example with lambda
		Collections.sort(names, (String a, String b) ->{
			return b.compareTo(a);
		});
		
		
		//removed the data types and allow the compiler to infer the type
		Collections.sort(names, (a,b) -> b.compareTo(a));
		
		//total pages in your book collection
		Book book1 = new Book("Miss Peregrine's HOme for Peculiar Children", "Ranson", "Riggs", 382);
		Book book2 = new Book("Harry Potter and the Sorcerer's Stone", "JK", "Rowling", 411);
		Book book3 = new Book("The Cat in the Hat", "Dr", "Seuss", 45);
		
		//use .collet to aggregate values
		List<Book> books = Arrays.asList(book1, book2, book3);
		int total = books.stream().collect(Collectors.summingInt(Book::getPages));
		System.out.println(total);
		
		//use .collect to aggregate author first names into a list
		//and .map to get the last name of the author
		List<String> list = books.stream().map(Book::getAuthorLName).collect(Collectors.toList());
		System.out.println(list);
		
		//create a list with duplicates
		List<Book> dupBooks = Arrays.asList(book1, book2, book3, book1, book2);
		System.out.println("before removing duplicates:  ");
		System.out.println(dupBooks.toString());
		
		//remove duplicates using a Set
		Collection<Book> noDups = new HashSet<>(dupBooks);
		System.out.println("Afer removing duplicates:  ");
		System.out.println(noDups.toString());
		
		//example of using Set to eliminate dups and sort automatically
		Set<Integer> numbers = new HashSet<>(asList(4,3,3,3,2,1));
		System.out.println(numbers.toString());
		
		//Peggy on Streams
		Arrays.asList("red", "green", "blue")
			.stream()
			.sorted()
			.findFirst()
			.ifPresent(System.out::println);
			
		
		//example of Stream.of with a filter
		Stream.of("apple", "pear", "banana", "cherry", "apricot")
			.filter(fruit ->{
				//System.out.println("filter: " + fruit;
				return fruit.startsWith("a"); //predicate
			})
			//if the foreach is removed, nothing will print,
			//the foreach makes it a terminal event
			.forEach(fruit -> System.out.println("Starts with A:  " +fruit));

		//using a stream an dmap operation to create a list of words in caps
		List<String> collected = Stream.of("Java", " Rocks")
				.map(string -> string.toUpperCase())
				.collect(toList());
				
		System.out.println(collected.toString());
		
		//and now Peggy does primitive streams
		IntStream.range(1,4).forEach(System.out::println);
		
		//find the average of the numbers squared
		Arrays.stream(new int[] {1,2,3,4})
			.map(n -> n * n)
			.average()
			.ifPresent(System.out::println);
		
		//map doubles to ints
		Stream.of(1.5, 2.3, 3.7)
			.mapToInt(Double::intValue)
			.forEach(System.out::println);
		
		
		//Savitch code:
		
		// Some sample names
		ArrayList<String> names1 = new ArrayList<>();
		names1.add("Paco");
		names1.add("Enrique");
		names1.add("Bob");

		// Get the largest name as defined by length using a lambda function
		String s2 = names1.stream()
		     .reduce("", (n1, n2) ->
		             {
		               if (n1.length() > n2.length())
		                     return n1;
		               else
		                     return n2;
		             }
		            );
		System.out.println("longest Name: " + s2);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
