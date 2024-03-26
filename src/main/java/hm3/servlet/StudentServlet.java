package hm3.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hm3.dto.StudentIncomingDTO;
import hm3.dto.StudentOutgoingDTO;
import hm3.dto.StudentUpdateDTO;
import hm3.exception.NotFoundException;
import hm3.service.StudentService;
import hm3.service.specification.StudentServiceInter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns ={"/stud/*"})
public class StudentServlet extends HttpServlet {

  private final transient StudentServiceInter studentService;
  private final ObjectMapper objectMapper;

  public StudentServlet() {
    this.studentService = StudentService.getInstance();
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
    setJsonHeader(resp);
    String responseAnswer = "";
    try {
      String[] pathPart = req.getPathInfo().split("/");

      if ("all".equals(pathPart[1])) {
        List<StudentOutgoingDTO> studentOutgoingDTOS = studentService.findAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(studentOutgoingDTOS);
      } else {
        Integer studId = Integer.parseInt(pathPart[1]);
        StudentOutgoingDTO studentOutgoingDTO = studentService.findById(studId);
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(studentOutgoingDTO);
      }
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Bad request.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    setJsonHeader(resp);
    String responseAnswer = "";
    try {
      String[] pathPart = req.getPathInfo().split("/");
      Integer studentId = Integer.parseInt(pathPart[1]);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
      if (req.getPathInfo().contains("/deleteLesson/")) {
        if ("deleteLesson".equals(pathPart[2])) {
          Integer lessId = Integer.parseInt(pathPart[3]);
          studentService.deleteLessonFromStudent(studentId, lessId);
        }
      } else {
        studentService.delete(studentId);
      }
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Bad request. ";
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
    Optional<StudentIncomingDTO> studResponse;
    try {
      studResponse = Optional.ofNullable(objectMapper.readValue(json, StudentIncomingDTO.class));
      StudentIncomingDTO student = studResponse.orElseThrow(IllegalArgumentException::new);
      responseAnswer = objectMapper.writeValueAsString(studentService.save(student));
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect student Object.";
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
    Optional<StudentUpdateDTO> studentResponse;
    try {
      if (req.getPathInfo().contains("/addLesson/")) {
        String[] pathPart = req.getPathInfo().split("/");
        if (pathPart.length > 3 && "addLesson".equals(pathPart[2])) {
          Integer studId = Integer.parseInt(pathPart[1]);
          resp.setStatus(HttpServletResponse.SC_OK);
          Integer lessId = Integer.parseInt(pathPart[3]);
          studentService.addLessonToStudent(studId, lessId);
        }
      } else {
        studentResponse = Optional.ofNullable(objectMapper.readValue(json, StudentUpdateDTO.class));
        StudentUpdateDTO studentUpdateDTO = studentResponse.orElseThrow(IllegalArgumentException::new);
        studentService.update(studentUpdateDTO);
      }
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    } catch (Exception e) {
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect project Object.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }


}
