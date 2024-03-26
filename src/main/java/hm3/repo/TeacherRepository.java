package hm3.repo;

import hm3.db.ConnectionManager;
import hm3.db.ConnectionManagerImpl;
import hm3.entity.Teacher;
import hm3.exception.RepositoryException;
import hm3.repo.specification.TeacherRepo;
import hm3.repo.sql.TeacherSqlQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeacherRepository implements TeacherRepo {

  private static TeacherRepo instance;
  private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

  public TeacherRepository() {
  }

  public static synchronized TeacherRepo getInstance() {
    if (instance == null) {
      instance = new TeacherRepository();
    }
    return instance;
  }

  private static Teacher createTeacher(ResultSet resultSet) throws SQLException {
    Teacher teacher;
    teacher = new Teacher(resultSet.getInt("id"),
        resultSet.getString("name"));
    return teacher;
  }


  @Override
  public Teacher save(Teacher teacher) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.SAVE_SQL,
            Statement.RETURN_GENERATED_KEYS)) {
        preparedStatement.setString(1,teacher.getName());
        preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if(resultSet.next()){
        teacher = new Teacher(
            resultSet.getInt("id"),
            resultSet.getString("name")
        );
      }
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return teacher;
  }

  @Override
  public void update(Teacher teacher) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.UPDATE_SQL)) {
      preparedStatement.setString(1,teacher.getName());
      preparedStatement.setInt(2,teacher.getId());

      preparedStatement.executeUpdate();

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.DELETE_SQL)) {
      preparedStatement.setInt(1, id);

      deleteResult = preparedStatement.executeUpdate() > 0;
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return deleteResult;
  }

  @Override
  public Optional<Teacher> findById(Integer id) {
    Teacher teacher = null;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.FIND_BY_ID_SQL)) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        teacher = createTeacher(resultSet);
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return Optional.ofNullable(teacher);
  }

  @Override
  public List<Teacher> findAll() {
    List<Teacher> teacherList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.FIND_ALL_SQL)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()){
        teacherList.add(createTeacher(resultSet));
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }

    return teacherList;
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(TeacherSqlQuery.EXIST_BY_ID_SQL)) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        isExist = resultSet.getBoolean(1);
      }
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
      return isExist;
  }
}
