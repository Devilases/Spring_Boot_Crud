package hm3.repo;

import hm3.db.ConnectionManager;
import hm3.db.ConnectionManagerImpl;
import hm3.entity.Lesson;
import hm3.entity.LessonToStudentReservation;
import hm3.entity.Student;
import hm3.exception.RepositoryException;
import hm3.repo.specification.LessonRepo;
import hm3.repo.specification.LessonToStudentRepo;
import hm3.repo.specification.StudentRepo;
import hm3.repo.sql.LessonToStudentSQLQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonToStudentRepository implements LessonToStudentRepo {

  private static final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
  private static final StudentRepo studentRepo = StudentRepository.getInstance();
  private static final LessonRepo lessonRepo = LessonRepository.getInstance();

  private static LessonToStudentRepo instance;

  public LessonToStudentRepository() {
  }

  public static synchronized LessonToStudentRepo getInstance() {
    if (instance == null) {
      instance = new LessonToStudentRepository();
    }
    return instance;
  }

  @Override
  public boolean deleteByStudentId(Integer stdId) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.DELETE_BY_STUDENT_ID_SQL);) {

      preparedStatement.setInt(1, stdId);

      deleteResult = preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }

    return deleteResult;
  }

  @Override
  public boolean deleteByLessonId(Integer lessonId) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.DELETE_BY_LESSON_ID_SQL);) {

      preparedStatement.setInt(1, lessonId);

      deleteResult = preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }

    return deleteResult;
  }

  @Override
  public List<LessonToStudentReservation> findAllByStudentId(Integer stdId) {
    List<LessonToStudentReservation> lessonToStudentReservationList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_ALL_BY_STUDENT_ID_SQL)) {

      preparedStatement.setLong(1, stdId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        lessonToStudentReservationList.add(createLessonToStudent(resultSet));
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonToStudentReservationList;
  }

  @Override
  public List<Lesson> findLessonByStudentId(Integer stdId) {
    List<Lesson> lessonList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_ALL_BY_STUDENT_ID_SQL)) {

      preparedStatement.setLong(1, stdId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int empId = resultSet.getInt("lesson_id");
        Optional<Lesson> optionalEmp = lessonRepo.findById(empId);
        if (optionalEmp.isPresent()) {
          lessonList.add(optionalEmp.get());
        }
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonList;
  }

  @Override
  public List<LessonToStudentReservation> findAllByLessonId(Integer lessonId) {
    List<LessonToStudentReservation> lessonToStudentReservationList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_ALL_BY_LESSON_ID_SQL)) {

      preparedStatement.setInt(1, lessonId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        lessonToStudentReservationList.add(createLessonToStudent(resultSet));
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonToStudentReservationList;
  }

  @Override
  public List<Student> findStudentByLessonId(Integer lessonId) {
    List<Student> studentList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_ALL_BY_LESSON_ID_SQL)) {

      preparedStatement.setLong(1, lessonId);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        int empId = resultSet.getInt("student_id");
        Optional<Student> optionalEmp = studentRepo.findById(empId);
        if (optionalEmp.isPresent()) {
          studentList.add(optionalEmp.get());
        }
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return studentList;
  }

  @Override
  public Optional<LessonToStudentReservation> findByStudentIdAndLessonId(Integer stdId, Integer lessonId) {
    Optional<LessonToStudentReservation> lessonToStudentReservation = Optional.empty();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_BY_LESSON_ID_AND_STUDENT_ID_SQL)) {

      preparedStatement.setInt(1, lessonId);
      preparedStatement.setInt(2, stdId);
      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        lessonToStudentReservation = Optional.of(createLessonToStudent(resultSet));
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonToStudentReservation;
  }

  @Override
  public LessonToStudentReservation save(LessonToStudentReservation lessonToStudentReservation) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            LessonToStudentSQLQuery.SAVE_SQL,
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setInt(1, lessonToStudentReservation.getLessonId());
      preparedStatement.setInt(2, lessonToStudentReservation.getStudentId());
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if (resultSet.next()) {
        lessonToStudentReservation = new LessonToStudentReservation(
            resultSet.getInt("id"),
            lessonToStudentReservation.getLessonId(),
            lessonToStudentReservation.getStudentId()
        );
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonToStudentReservation;
  }

  @Override
  public void update(LessonToStudentReservation lessonToStudentReservation) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
            LessonToStudentSQLQuery.UPDATE_SQL)) {
      preparedStatement.setInt(1, lessonToStudentReservation.getLessonId());
      preparedStatement.setInt(2, lessonToStudentReservation.getStudentId());
      preparedStatement.setInt(3, lessonToStudentReservation.getId());

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());

    }


  }

  @Override
  public boolean deleteById(Integer id) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.DELETE_SQL);) {

      preparedStatement.setInt(1, id);

      deleteResult = preparedStatement.executeUpdate() > 0;
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }

    return deleteResult;
  }

  @Override
  public Optional<LessonToStudentReservation> findById(Integer id) {
    LessonToStudentReservation lessonToStudentReservation = null;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_BY_ID_SQL)) {

      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        lessonToStudentReservation = createLessonToStudent(resultSet);
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return Optional.ofNullable(lessonToStudentReservation);
  }

  private LessonToStudentReservation createLessonToStudent(ResultSet resultSet) throws SQLException {
    LessonToStudentReservation lessonToStudentReservation;
    lessonToStudentReservation = new LessonToStudentReservation(
        resultSet.getInt("id"),
        resultSet.getInt("lesson_id"),
        resultSet.getInt("student_id")
    );
    return lessonToStudentReservation;
  }

  @Override
  public List<LessonToStudentReservation> findAll() {
    List<LessonToStudentReservation> lessonToStudentReservationList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.FIND_ALL_SQL)) {

      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        lessonToStudentReservationList.add(createLessonToStudent(resultSet));
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return lessonToStudentReservationList;
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExists = false;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonToStudentSQLQuery.EXIST_BY_ID_SQL)) {

      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        isExists = resultSet.getBoolean(1);
      }
    } catch (SQLException e) {
      throw new RepositoryException(e.getMessage());
    }
    return isExists;
  }
}
