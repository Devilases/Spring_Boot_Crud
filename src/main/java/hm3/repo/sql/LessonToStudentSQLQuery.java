package hm3.repo.sql;

public class LessonToStudentSQLQuery {

  public LessonToStudentSQLQuery() {
  }

  public static final String SAVE_SQL = """
      INSERT INTO lessontostudent (lesson_id, student_id) VALUES(?,?) ;
      """;

  public static final String UPDATE_SQL = """
      UPDATE lessontostudent SET lesson_id = ?, student_id =? WHERE id = ?;
      """;

  public static final String DELETE_SQL = """
      DELETE FROM lessontostudent WHERE id = ?;
      """;

  public static final String FIND_BY_ID_SQL = """
      SELECT id, lesson_id, student_id FROM lessontostudent WHERE id = ?;
      """;

  public static final String FIND_ALL_SQL = """
      SELECT id, lesson_id, student_id FROM lessontostudent;
      """;

  public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM lessontostudent
                        WHERE id = ?
                        LIMIT 1);
            """;

  public static final String FIND_ALL_BY_STUDENT_ID_SQL = """
      SELECT id, lesson_id, student_id FROM lessontostudent WHERE student_id = ?;
      """;

  public static final String FIND_ALL_BY_LESSON_ID_SQL = """
      SELECT id, lesson_id, student_id FROM lessontostudent WHERE lesson_id = ?;
      """;

  public static final String FIND_BY_LESSON_ID_AND_STUDENT_ID_SQL = """
      SELECT id, lesson_id, student_id FROM lessontostudent WHERE lesson_id = ? AND student_id = ? LIMIT 1;;
      """;

  public static final String DELETE_BY_STUDENT_ID_SQL = """
      DELETE FROM lessontostudent WHERE student_id = ?;
      """;
  public static final String DELETE_BY_LESSON_ID_SQL = """
      DELETE FROM lessontostudent WHERE lesson_id = ?;
      """;
}
