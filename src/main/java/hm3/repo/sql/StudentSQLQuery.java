package hm3.repo.sql;

public class StudentSQLQuery {

  public StudentSQLQuery() {
  }

  public static final String SAVE_SQL = """
      INSERT INTO student (name) VALUES(?) ;
      """;

  public static final String UPDATE_SQL = """
      UPDATE student SET name = ? WHERE id = ?;
      """;

  public static final String DELETE_SQL = """
      DELETE FROM student WHERE id = ?;
      """;

  public static final String FIND_BY_ID_SQL = """
      SELECT id, name FROM student WHERE id = ?;
      """;

  public static final String FIND_ALL_SQL = """
      SELECT id, name FROM student;
      """;

  public static final String EXIST_BY_ID_SQL = """
                SELECT exists (
                SELECT 1
                    FROM student
                        WHERE id = ?
                        LIMIT 1);
            """;
}
