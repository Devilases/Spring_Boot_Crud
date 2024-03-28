package hm3.service;

import hm3.dto.TeacherIncomingDTO;
import hm3.dto.TeacherOutgoingDTO;
import hm3.dto.TeacherUpdateDTO;
import hm3.entity.Teacher;
import hm3.exception.NotFoundException;
import hm3.mapper.TeacherDtoMapperIml;
import hm3.mapper.specification.TeacherDtoMapper;
import hm3.repo.hiber.TeacherRepoHibernateImpl;
import hm3.repo.specification.TeacherRepo;
import hm3.service.specification.TeacherServiceInter;
import java.util.List;

public class TeacherService implements TeacherServiceInter {

  private TeacherRepo teacherRepo = TeacherRepoHibernateImpl.getInstance();
  private static TeacherServiceInter instance;
  private final TeacherDtoMapper teacherDtoMapper = TeacherDtoMapperIml.getInstance();


  private TeacherService() {
  }

  public static synchronized TeacherServiceInter getInstance() {
    if (instance == null) {
      instance = new TeacherService();
    }
    return instance;
  }

  private void checkTeacherExist(Integer teacherId) throws NotFoundException {
    if (!teacherRepo.exitsById(teacherId)) {
      throw new NotFoundException("Teacher not found.");
    }
  }


  @Override
  public TeacherOutgoingDTO save(TeacherIncomingDTO teacherDto) {
    Teacher teacher = teacherDtoMapper.map(teacherDto);
    teacher = teacherRepo.save(teacher);
    return teacherDtoMapper.map(teacher);
  }

  @Override
  public void update(TeacherUpdateDTO teacherDto) throws NotFoundException {
    checkTeacherExist(teacherDto.getId());
    Teacher teacher = teacherDtoMapper.map(teacherDto);
    teacherRepo.update(teacher);
  }

  @Override
  public TeacherOutgoingDTO findById(Integer teacherId) throws NotFoundException {
    checkTeacherExist(teacherId);
    Teacher teacher = teacherRepo.findById(teacherId)
        .orElseThrow(()-> new NotFoundException("Teacher not found."));

    return teacherDtoMapper.map(teacher);
  }

  @Override
  public List<TeacherOutgoingDTO> findAll() {
    List<Teacher> teacherList = teacherRepo.findAll();
    return teacherDtoMapper.map(teacherList);
  }

  @Override
  public boolean delete(Integer teacherId) throws NotFoundException {
    checkTeacherExist(teacherId);
    return teacherRepo.deleteById(teacherId);
  }
}
