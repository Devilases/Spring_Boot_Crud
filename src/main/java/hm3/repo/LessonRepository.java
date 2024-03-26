package hm3.repo;

import hm3.db.ConnectionManager;
import hm3.db.ConnectionManagerImpl;
import hm3.entity.Lesson;
import hm3.entity.LessonToStudentReservation;
import hm3.entity.Student;
import hm3.entity.Teacher;
import hm3.exception.RepositoryException;
import hm3.repo.specification.LessonRepo;
import hm3.repo.specification.LessonToStudentRepo;
import hm3.repo.specification.StudentRepo;
import hm3.repo.specification.TeacherRepo;
import hm3.repo.sql.LessonSQLQuery;
import hm3.repo.sql.StudentSQLQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LessonRepository implements LessonRepo {
  private final ConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
  private final TeacherRepo teacherRepo = TeacherRepository.getInstance();
  private final StudentRepo studentRepo = StudentRepository.getInstance();
  private final LessonToStudentRepo lessonToStudentRepo = LessonToStudentRepository.getInstance();

  private static LessonRepo instance;


  public LessonRepository() {
  }

  public static synchronized LessonRepo getInstance() {
    if (instance == null) {
      instance = new LessonRepository();
    }
    return instance;
  }

  @Override
  public Lesson save(Lesson lesson) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.SAVE_SQL,
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1,lesson.getName());
      preparedStatement.setInt(2, lesson.getTeacher().getId());
      preparedStatement.executeUpdate();

      ResultSet resultSet = preparedStatement.getGeneratedKeys();
      if(resultSet.next()){
        lesson = new Lesson(
            resultSet.getInt("id"),
            lesson.getName(),
            lesson.getTeacher(),
            null
        );
      }
      saveStudentList(lesson);
      lesson.getStudents();
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return lesson;
  }

  public void saveStudentList(Lesson lesson){
    if(lesson.getStudents() != null && !lesson.getStudents().isEmpty()){
      List<Integer> studentIdList = new ArrayList<>(
          lesson.getStudents()
              .stream()
              .map(Student::getId)
              .toList()
      );
      List<LessonToStudentReservation> existsStudentList = lessonToStudentRepo.findAllByLessonId(lesson.getId());
      for (LessonToStudentReservation lessonToStudentReservation : existsStudentList){
        if(!studentIdList.contains(lessonToStudentReservation.getStudentId())){
          lessonToStudentRepo.deleteById(lessonToStudentReservation.getId());
        }
        studentIdList.remove(lessonToStudentReservation.getStudentId());
      }
      for (Integer studentId : studentIdList){
        if(studentRepo.exitsById(studentId)){
          LessonToStudentReservation lessonToStudentReservation = new LessonToStudentReservation(
              null,
              lesson.getId(),
              studentId
          );
          lessonToStudentRepo.save(lessonToStudentReservation);
        }
      }
    }else {
      lessonToStudentRepo.deleteByLessonId(lesson.getId());
    }
  }


  @Override
  public void update(Lesson lesson) {
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.UPDATE_SQL)) {

      preparedStatement.setString(1,lesson.getName());
      preparedStatement.setInt(2, lesson.getTeacher().getId());
      preparedStatement.setInt(3,lesson.getId());

      preparedStatement.executeUpdate();

      saveStudentList(lesson);

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    boolean deleteResult;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.DELETE_SQL)) {
      preparedStatement.setInt(1, id);

      deleteResult = preparedStatement.executeUpdate() > 0;
    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return deleteResult;
  }

  @Override
  public Optional<Lesson> findById(Integer id) {
    Lesson lesson = null;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.FIND_BY_ID_SQL)) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();
      if(resultSet.next()){
        lesson = createLesson(resultSet);
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }
    return Optional.ofNullable(lesson);
  }

  private Lesson createLesson(ResultSet resultSet) throws SQLException {
    Integer lessId = resultSet.getInt("id");
    Teacher teacher = teacherRepo.findById(resultSet.getInt("teacher_id")).orElse(null);
    return new Lesson(lessId, resultSet.getString("name"), teacher, null);
  }

  @Override
  public List<Lesson> findAll() {
    List<Lesson> lessonList = new ArrayList<>();
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.FIND_ALL_SQL)) {
      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()){
        lessonList.add(createLesson(resultSet));
      }

    }catch (SQLException e){
      throw new RepositoryException(e.getMessage());
    }

    return lessonList;
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExists = false;
    try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(LessonSQLQuery.EXIST_BY_ID_SQL)) {

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
