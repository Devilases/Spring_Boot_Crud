package hm3.repo.sql;

public class LessonSQLQuery {

  public LessonSQLQuery() {
  }

  public static final String SAVE_SQL = """
      INSERT INTO lesson (name, teacher_id) VALUES(?, ?) ;
      """;

  public static final String UPDATE_SQL = """
      UPDATE lesson SET name = ?, teacher_id = ? WHERE id = ?;
      """;

  public static final String DELETE_SQL = """
      DELETE FROM lesson WHERE id = ?;
      """;

  public static final String FIND_BY_ID_SQL = """
      SELECT id, name, teacher_id FROM lesson WHERE id = ?;
      """;

  public static final String FIND_ALL_SQL = """
      SELECT id, name, teacher_id FROM lesson;
      """;

  public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM lesson
                        WHERE id = ?
                        LIMIT 1);
            """;
}
