package hm3.repo.sql;

public class TeacherSqlQuery {

  public TeacherSqlQuery() {
  }

  public static final String SAVE_SQL = """
      INSERT INTO teacher (name) VALUES(?);
      """;

  public static final String UPDATE_SQL = """
      UPDATE teacher SET name = ? WHERE id = ?;
      """;

  public static final String DELETE_SQL = """
      DELETE FROM teacher WHERE id = ?;
      """;

  public static final String FIND_BY_ID_SQL = """
      SELECT id, name FROM teacher WHERE id = ?;
      """;

  public static final String FIND_ALL_SQL = """
      SELECT id, name FROM teacher;
      """;

  public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM teacher
                        WHERE id = ?
                        LIMIT 1);
            """;
}
