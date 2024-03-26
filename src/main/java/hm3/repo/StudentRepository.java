package hm3.repo;

import hm3.db.ConnectionManager;
import hm3.db.ConnectionManagerImpl;
import hm3.entity.Student;
import hm3.exception.RepositoryException;
import hm3.repo.specification.StudentRepo;
import hm3.repo.sql.StudentSQLQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements StudentRepo {

  private static StudentRepo instance;
  private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

  public StudentRepository() {
  }

  public static synchronized StudentRepo getInstance() {
    if (instance == null) {
      instance = new StudentRepository();
    }
    return instance;
  }

  private static Student createStudent(ResultSet resultSet) throws SQLException {
    Student student;
    student = new Student(resultSet.getInt("id"),
        resultSet.getString("name"),
        null);
    return student;
  }


  @Override
  public Student save(Student student) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.SAVE_SQL,
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1,student.getName());
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if(resultSet.next()){
        student = new Student(
            resultSet.getInt("id"),
            student.getName(),
            null
        );
      }
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return student;
  }

  @Override
  public void update(Student student) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.UPDATE_SQL)) {
      preparedStatement.setString(1,student.getName());
      preparedStatement.setInt(2,student.getId());

      preparedStatement.executeUpdate();

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.DELETE_SQL)) {
      preparedStatement.setInt(1, id);

      deleteResult = preparedStatement.executeUpdate() > 0;
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return deleteResult;
  }

  @Override
  public Optional<Student> findById(Integer id) {
    Student student = null;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.FIND_BY_ID_SQL)) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        student = createStudent(resultSet);
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return Optional.ofNullable(student);
  }

  @Override
  public List<Student> findAll() {
    List<Student> studentList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.FIND_ALL_SQL)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()){
        studentList.add(createStudent(resultSet));
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }

    return studentList;
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(StudentSQLQuery.EXIST_BY_ID_SQL)) {
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
