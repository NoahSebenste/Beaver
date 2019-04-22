import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Thiss class handles data extraction from the excel workbooks.
 */
public class Utils {

    public static final String TEST_SCORES_PATH = "Test Scores.xlsx";
    public static final String STUDENT_INFO_PATH = "Student Info.xlsx";
    public static final String TEST_RETAKE_PATH = "Test Retake Scores.xlsx";

    /**
     * Returns an array list of students containing the data from the workbooks as well as their highest score.
     * @return
     */
    public static ArrayList<Student> generateStudentList() {

        ArrayList<Student> students = new ArrayList<>();

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(new File(STUDENT_INFO_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();


        //initialize students from the student info table.
        for (Row row: sheet) {

            //skip over the title row
            if(row.getRowNum()==0)
                continue;

            //extracts information from the three cells in the student info workbook
            int cell0 = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0)));
            String cell1 = dataFormatter.formatCellValue(row.getCell(1));
            String cell2 = dataFormatter.formatCellValue(row.getCell(2));

            students.add(new Student(cell0, cell1, cell2,  0));
        }

        //populate students with their initial test scores.
        try {
            workbook = WorkbookFactory.create(new File(TEST_SCORES_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);

        for(Row row : sheet) {

            //skip over the title row
            if(row.getRowNum()==0)
                continue;

            int id = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0)));

            for(Student student : students) {
                if(student.getId() == id) {
                    student.setScore(Integer.parseInt(dataFormatter.formatCellValue(row.getCell(1))));
                }
            }

        }

        //Check to see if grades need to be updated
        try {
            workbook = WorkbookFactory.create(new File(TEST_RETAKE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        sheet = workbook.getSheetAt(0);

        for(Row row : sheet) {

            //skip over the title row
            if(row.getRowNum()==0)
                continue;

            int id = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(0)));
            int score = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(1)));

            for(Student student : students) {
                if(student.getId() == id) {
                    if(score > student.getScore())
                        student.setScore(score);
                }
            }

        }

        return students;
    }
}
