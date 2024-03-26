package hm3.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hm3.dto.TeacherIncomingDTO;
import hm3.dto.TeacherOutgoingDTO;
import hm3.dto.TeacherUpdateDTO;
import hm3.exception.NotFoundException;
import hm3.service.TeacherService;
import hm3.service.specification.TeacherServiceInter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebServlet(urlPatterns = {"/teacher/*"})
public class TeacherServlet extends HttpServlet {

  private final transient TeacherServiceInter teacherService;
  private final ObjectMapper objectMapper;

  public TeacherServlet() {
    log.info(getClass().getName()+" init");
    this.teacherService = TeacherService.getInstance();
    this.objectMapper = new ObjectMapper();
  }

  private static void setJsonHeader(HttpServletResponse resp) {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");

  }

  private static String getJson(HttpServletRequest req) throws IOException {
    StringBuilder sb = new StringBuilder();
    BufferedReader postData = req.getReader();
    String line;
    while ((line = postData.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    log.info(getClass().getName()+" Begin doGet");
    setJsonHeader(resp);
    String responseAnswer = "";
    try {
      String[] pathPart = req.getPathInfo().split("/");
      if("all".equals(pathPart[1])){
        log.info("all");
        List<TeacherOutgoingDTO> allTeacher = teacherService.findAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(allTeacher);
      }else {
        Integer teacherId = Integer.parseInt(pathPart[1]);
        log.info(String.valueOf(teacherId));
        TeacherOutgoingDTO teacherOutgoingDTObyId = teacherService.findById(teacherId);
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(teacherOutgoingDTObyId);
      }
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();

    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = e.getMessage();
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    log.info(getClass().getName()+" Begin delete");
    setJsonHeader(resp);
    String responseAnswer = "";
    try {
      String[] pathPart = req.getPathInfo().split("/");
      Integer teacherId = Integer.parseInt(pathPart[1]);
      if(teacherService.delete(teacherId)){
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
      }
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = e.getMessage();
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    String json = getJson(req);
    String responseAnswer = null;
    Optional<TeacherIncomingDTO> teacherResponse;
    try {
      teacherResponse = Optional.ofNullable(objectMapper.readValue(json, TeacherIncomingDTO.class));
      TeacherIncomingDTO teacherIncomingDTO = teacherResponse.orElseThrow(IllegalArgumentException::new);
      responseAnswer = objectMapper.writeValueAsString(teacherService.save(teacherIncomingDTO));
    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect teacher Object.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    String json = getJson(req);
    String responseAnswer = "";
    Optional<TeacherUpdateDTO> teacherResponse;
    try {
      teacherResponse = Optional.ofNullable(objectMapper.readValue(json, TeacherUpdateDTO.class));
      TeacherUpdateDTO teacherIncomingDTO = teacherResponse.orElseThrow(IllegalArgumentException::new);
      teacherService.update(teacherIncomingDTO);
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect teacher Object.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }
}
