package cbcg;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import cws.demo.cbcg.StudentUtil;
class StudentUtilTest {

	StudentUtil calculator;

	@Test
	void isCtordWithNew () {
		new StudentUtil();
	}
			
	@Nested
	class WhenNew {
		
		@BeforeEach
		void createStudentUtil () {
			calculator = new StudentUtil();
		}

		@AfterEach
		void gcCalculator () {
			calculator = null;
			
		}
		
		@Test
		void checkValidDateSame () {
			assertEquals(1, calculator.calcGradeForDateRange("2012-06-02", "2012-06-02"));
		}
		
		@Test
		void checkValidPostGrade () {
			assertEquals(99, calculator.calcGradeForDateRange("2004-09-01", "2017-06-30"));
		}

		@Test
		void checkValidDateToToday () {
			assertEquals(6,calculator.calcGradeForDateRange("2012-09-01", null));
		}

		@Test
		void checkValidDateFirstYear () {
			assertEquals(1, calculator.calcGradeForDateRange("2012-06-02", "2012-06-02"));
		}

		@Test
		void checkValidDateGrade12 () {
			assertEquals(12, calculator.calcGradeForDateRange("2000-09-01", "2012-06-02"));
		}

		@Nested
		@DisplayName ("Negative tests")
		class InvalidCalculations {
			@Test
			void failInvalidDateOrder () {
				assertThrows(IllegalArgumentException.class, () -> calculator.calcGradeForDateRange("2017-06-30", "2012-09-01") );
			}
			
			@Test
			void failInvalidDateStringFormat () {
				assertThrows(DateTimeParseException.class, () -> calculator.calcGradeForDateRange("9-13-02", null));
			}
		}
		
	}

}
