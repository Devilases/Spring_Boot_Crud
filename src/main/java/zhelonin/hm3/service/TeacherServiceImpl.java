package zhelonin.hm3.service;

import lombok.extern.slf4j.Slf4j;
import zhelonin.hm3.dto.TeacherIncomingDTO;
import zhelonin.hm3.dto.TeacherOutgoingDTO;
import zhelonin.hm3.dto.TeacherUpdateDTO;
import zhelonin.hm3.entity.Teacher;
import zhelonin.hm3.exception.NotFoundException;
import zhelonin.hm3.mapper.specification.TeacherDtoMapper;
import zhelonin.hm3.repo.boot.TeacherRepository;
import zhelonin.hm3.service.specification.TeacherService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

  private final TeacherRepository teacherRepo;
  private final TeacherDtoMapper teacherDtoMapper;


  public TeacherServiceImpl(TeacherRepository teacherRepo, TeacherDtoMapper teacherDtoMapper) {
    this.teacherRepo = teacherRepo;
    this.teacherDtoMapper = teacherDtoMapper;
  }

  private void checkTeacherExist(Integer teacherId) throws NotFoundException {
    if (!teacherRepo.existsById(teacherId)) {
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
    teacherRepo.saveAndFlush(teacher);
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
    log.info("findAll return value");
    return teacherDtoMapper.map(teacherList);
  }

  @Override
  public boolean delete(Integer teacherId) throws NotFoundException {
    checkTeacherExist(teacherId);
    teacherRepo.deleteById(teacherId);
     return true;
  }
}
