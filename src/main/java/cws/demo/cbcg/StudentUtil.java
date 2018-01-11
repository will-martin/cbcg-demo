package cws.demo.cbcg;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

// This class is provided in response to a request from a CompuGain opportunity.
// Its utility is not represented as guaranteed or supported.


public class StudentUtil {
	
	private final LocalDate schoolYearStart = LocalDate.parse ("2000-09-01");
	private final LocalDate schoolYearEnd = LocalDate.parse("2001-06-30");
	
	@FunctionalInterface
	interface Calculator {
		int calculate (LocalDate o1, LocalDate o2);
	}

	public StudentUtil () {
	}

	private boolean preCondition1 (LocalDate begin, LocalDate end) {

		if (begin.compareTo (end) > 0) {
			return false;
		}
		return true;
	}
	
	public int calcGradeForDateRange (String begin, String end) 
			throws IllegalArgumentException {
		LocalDate now = LocalDate.now();
		LocalDate studentStartSchool;
		LocalDate studentEndSchool;
		int dayOfYearStart = schoolYearStart.getDayOfYear();
		int dayOfYearEnd = schoolYearEnd.getDayOfYear();		
		
		/*try { */
			 studentStartSchool = ( begin == null) ? LocalDate.now() :LocalDate.parse(begin);
			 studentEndSchool = ( end == null) ? LocalDate.now() : LocalDate.parse(end);
/*		} catch (DateTimeParseException de) {
			throw new IllegalArgumentException (de.getMessage());
		}
*/		
		if (!preCondition1(studentStartSchool, studentEndSchool)) {
			throw new IllegalArgumentException ("Dates in wrong order or equal");
		}
		
		boolean inSchool = (now.getDayOfYear() <= dayOfYearEnd) || (now.getDayOfYear() >= dayOfYearStart);

		Calculator calc = (dbegin, dend) -> {
			int grade = 0;
			
			int yearDiff = dend.getYear() - dbegin.getYear();
			
			if (yearDiff > 12 && dend.compareTo(now) > 0) {
				grade = -1;
			} else if (yearDiff <= 12 && dend.compareTo(now) > 0) {
				grade = now.getYear() - dbegin.getYear();
			} else if (yearDiff <= 12 && dend.compareTo(now) <= 0) {
				grade = yearDiff;
			} if (yearDiff > 12 && dend.compareTo(now) <= 0) {
				grade = 99;
			}
			
			if (grade == 0 && inSchool) {
				grade = 1;
			}else if (grade < 12 && grade > 0 && !inSchool) {
				grade += 1;
			}
			else if (grade == 12 && !inSchool /* && dend.compareTo(now) <= 0 */) {
					grade = 99;
			}
			
			
			return grade;
		};
		
		int rv = calc.calculate(studentStartSchool, studentEndSchool);
		
		return rv;
	}
};