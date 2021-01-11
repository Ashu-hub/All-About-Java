package Java8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class Student {
	String name;
	int id;
	String subject;
	double percentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Student(String name, int id, String subject, double percentage) {
		super();
		this.name = name;
		this.id = id;
		this.subject = subject;
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", id=" + id + ", subject=" + subject + ", percentage=" + percentage + "]";
	}

}

public class CollectorsEaxmpleObject {
	public static void main(String[] args) {
		
		List<Student> studentList = new ArrayList<>();
		
		studentList.add(new Student("Paul", 11, "Economics", 78.9));
		studentList.add(new Student("Zevin", 12, "Computer Science", 91.2));
		studentList.add(new Student("Harish", 13, "History", 83.7));
		studentList.add(new Student("Xiano", 14, "Literature", 71.5));
		studentList.add(new Student("Soumya", 15, "Economics", 77.5));
		studentList.add(new Student("Asif", 16, "Mathematics", 89.4));
		studentList.add(new Student("Nihira", 17, "Computer Science", 84.6));
		studentList.add(new Student("Mitshu", 18, "History", 73.5));
		studentList.add(new Student("Vijay", 19, "Mathematics", 92.8));
		studentList.add(new Student("Harry", 20, "History", 71.9));
		
		
		System.out.println("*****************************");
		
		//0.1 Old Sorting technique,
		
		studentList.stream().sorted(new Comparator<Student>(){
			@Override
			   public int compare(Student o1, Student o2) {
		        return o1.getName().compareTo(o2.getName());
		    }
		}).forEach(System.out::println);
		System.out.println("*****************************");

		// new way
		studentList.stream().sorted(Comparator.comparing(Student::getName)).forEach(System.out::println);
		System.out.println("*****************************");
		
		//0. sorting based on 2 props
		studentList.stream().sorted(Comparator.comparing(Student::getPercentage).thenComparing(Student::getName)).forEach(System.out::println);
		System.out.println("*****************************");		

		//1. Example : Collecting top 3 performing students into List
		
		List<Student> x = studentList.stream().sorted(Comparator.comparing(Student::getPercentage).reversed()).limit(3).collect(Collectors.toList());
		//List<Student> x = studentList.stream().sorted(Comparator.comparing(Student::getPercentage).reversed()).collect(Collectors.toList());
		x.stream().forEach(System.out::println);
		
		//O/P- [Vijay-19-Mathematics-92.8, Zevin-12-Computer Science-91.2, Asif-16-Mathematics-89.4]
		
		System.out.println("**********");
		
	//2. Example : Collecting subjects offered into Set.
		Set<String> y = studentList.stream().map(Student::getSubject).collect(Collectors.toSet());
		y.stream().forEach(System.out::println);

		//[Economics, Literature, Computer Science, Mathematics, History]

		System.out.println("**********");
		
	//3. Example : Collecting name and percentage of each student into a Map	
		Map<String, Double> nameNPer = studentList.stream().collect(Collectors.toMap(Student::getName, Student::getPercentage));
		System.out.println(nameNPer);
		
		//O/P {Asif=89.4, Vijay=92.8, Zevin=91.2, Harry=71.9, Xiano=71.5, Nihira=84.6, Soumya=77.5, Mitshu=73.5, Harish=83.7, Paul=78.9}
		System.out.println("**********");
		
	//4 . Example : Collecting first 3 student into LinkedList
		Collection<Student> first3student = studentList.stream().limit(3).collect(Collectors.toCollection(LinkedList::new));
		first3student.stream().forEach(System.out::println);
		//[Paul-11-Economics-78.9, Zevin-12-Computer Science-91.2, Harish-13-History-83.7]
		System.out.println("**********");
		
	//5. Example : Collecting the names of all students joined as a comma
		
		String nameJoinedByComma = studentList.stream().map(Student::getName).collect(Collectors.joining(", "));
		System.out.println(nameJoinedByComma);
		
		//Paul, Zevin, Harish, Xiano, Soumya, Asif, Nihira, Mitshu, Vijay, Harry
		System.out.println("**********");
		
	//6.  Counting number of students.
		Long noOFStudents = studentList.stream().collect(Collectors.counting());
		System.out.println(noOFStudents);

		System.out.println("**********");
		
	//7. Example : Collecting highest percentage.
		Optional<Double> highPer = studentList.stream().map(Student::getPercentage).collect(Collectors.maxBy(Comparator.naturalOrder()));
		System.out.println(highPer);
	
		//Optional[92.8]
		System.out.println("**********");
		
	//8. Example : Collecting lowest percentage.
		Optional<Double> lowestPer = studentList.stream().map(Student::getPercentage).collect(Collectors.minBy(Comparator.naturalOrder()));
		System.out.println(lowestPer);
		
		//Optional[71.5]
		System.out.println("**********");
		
	//9. Example : Collecting sum of percentages
		Double sumPer = studentList.stream().collect(Collectors.summingDouble(Student::getPercentage));
		System.out.println(sumPer);
		
		//815.0
		System.out.println("**********");
		
	//10. Example : Collecting average percentage
		Double avgPer = studentList.stream().collect(Collectors.averagingDouble(Student::getPercentage));
		System.out.println(avgPer);
		
		//815.0
		System.out.println("**********");
		
	//11. Example : Extracting highest, lowest and average of percentage of students
		DoubleSummaryStatistics summaryPer = studentList.stream().collect(Collectors.summarizingDouble(Student::getPercentage));
		System.out.println("Hihgest Per-"+ summaryPer.getMax());
		System.out.println("Min Per-"+ summaryPer.getMin());
		System.out.println("Avg Per-"+ summaryPer.getAverage());
		
		/*
		 * Hihgest Per-92.8
		 *  Min Per-71.5 
		 *  Avg Per-81.5
		 */
		System.out.println("**********");
		
	//12. Example : Grouping the students by subject
		Map<String, List<Student>> groupBySub = studentList.stream().collect(Collectors.groupingBy(Student::getSubject));
		System.out.println(groupBySub);

		/*
		 * {
		 * Economics=[Student [name=Paul, id=11, subject=Economics, percentage=78.9], Student [name=Soumya, id=15, subject=Economics, percentage=77.5]], 
		 * Literature=[Student [name=Xiano, id=14, subject=Literature, percentage=71.5]],
		 * Computer Science=[Student [name=Zevin, id=12, subject=Computer Science, percentage=91.2], Student [name=Nihira, id=17, subject=Computer Science, percentage=84.6]], 
		 * Mathematics=[Student [name=Asif,id=16, subject=Mathematics, percentage=89.4], Student [name=Vijay, id=19, subject=Mathematics, percentage=92.8]],
		 * History=[Student [name=Harish, id=13,subject=History, percentage=83.7], Student [name=Mitshu, id=18, subject=History, percentage=73.5], Student [name=Harry, id=20,subject=History, percentage=71.9]]}
		 */
		System.out.println("**********");
		
	//13. Example : Partitioning the students who got above 80.0% from who don’t.
		Map<Boolean, List<Student>> above80Per = studentList.stream().collect(Collectors.partitioningBy(student -> student.getPercentage() > 80.0));
		System.out.println(above80Per);
		
		// {false=[Paul-11-Economics-78.9, Xiano-14-Literature-71.5, Soumya-15-Economics-77.5, Mitshu-18-History-73.5, Harry-20-History-71.9], 
		//  true=[Zevin-12-Computer Science-91.2, Harish-13-History-83.7, Asif-16-Mathematics-89.4, Nihira-17-Computer Science-84.6, Vijay-19-Mathematics-92.8]}
		
		System.out.println("**********");
	
	//14. Example : Collection the students Name who got maxPer.
		String studentwithMaxPer = studentList.stream().collect(
				Collectors.collectingAndThen(
						Collectors.maxBy(Comparator.comparing(Student::getPercentage)),
						(Optional<Student> stu) -> stu.isPresent() ? stu.get().getName(): "None") 
				);
		System.out.println("14. "+studentwithMaxPer);
		System.out.println("**********");
		
	//15. Example Subject wise Highest Marks
		Map<String, String> subwiseHighMarks = studentList.stream().collect(Collectors.groupingBy(Student::getSubject,
				Collectors.collectingAndThen(
						Collectors.maxBy(Comparator.comparing(Student::getPercentage)),
						( Optional<Student> stu) -> stu.isPresent() ? stu.get().getName() : "None")
				));
		
		System.out.println("15. "+subwiseHighMarks);
		//15. {Economics=Paul, Literature=Xiano, Computer Science=Zevin, Mathematics=Vijay, History=Harish}

	}
}

//ref - https://javaconceptoftheday.com/java-8-collectors-tutorial/
