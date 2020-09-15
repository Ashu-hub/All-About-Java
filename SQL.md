# Joins:
[REf](https://www.youtube.com/watch?v=KTvYHEntvn8 )
	The SQL Joins clause is used to combine records from two or more tables in a database. A JOIN is a means for combining fields from two tables by using values common to each.

## Inner Joins/EQUIJOIN:

	The query compares each row of table1 with each row of table2 to find all pairs of rows which satisfy the join-predicate. When the join-predicate is satisfied, column values for each matched pair of rows of A and B are combined into a result row.
eg:
	select * from tblCountry 
		INNER JOIN tblState
		ON tblCountry.Countryid = tblState.Countryid

Results only matching records.

## Left JOIN
	The SQL LEFT JOIN returns all rows from the left table, even if there are no matches in the right table.
eg:
	select * from tblCountry 
		LEFT JOIN tblState
		ON tblCountry.Countryid = tblState.Countryid
	
	
## Right Join
		Returns all rows from the right table, even if there are no matches in the left table.
eg:-		
		select * from tblCountry 
				RIGHT JOIN tblState
				ON tblCountry.Countryid = tblState.Countryid
				
## FullOuter Join				
	The SQL FULL JOIN combines the results of both left and right outer joins.
eg:-
select * from tblCountry 
				FULL OUTER JOIN tblState
				ON tblCountry.Countryid = tblState.Countryid
	
## Self JOIN
		Joining a Table with Itself.
	eg:-find the employee details who are getting more salary than the manager's salary.
	
***************************************************************************************************

**Q)	Difference between FetchType LAZY and EAGER in Java Persistence API?**

		 The FetchType defines when Hibernate gets the related entities from the database.
		 To load it together with the rest of the fields (i.e. eagerly), -- Eager loading
		 To load it on-demand (i.e. lazily) when you call the university's getStudents() method.
		 LAZY = fetch when needed
		 EAGER = fetch immediately