package hm3.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import hm3.dto.LessonIncomingDTO;
import hm3.dto.LessonOutgoingDTO;
import hm3.dto.LessonUpdateDTO;
import hm3.exception.NotFoundException;
import hm3.service.LessonService;
import hm3.service.specification.LessonServiceInter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;


@WebServlet(urlPatterns = {"/less/*"})
public class LessonServlet extends HttpServlet {
  private final transient LessonServiceInter lessonService;
  private final ObjectMapper objectMapper;

  public LessonServlet() {
    this.lessonService = LessonService.getInstance();
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
      if("all".equals(pathPart[1])){
        List<LessonOutgoingDTO> allLesson = lessonService.findAll();
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(allLesson);
      }else {
        Integer teacherId = Integer.parseInt(pathPart[1]);
        LessonOutgoingDTO lessonOutgoingDTO = lessonService.findById(teacherId);
        resp.setStatus(HttpServletResponse.SC_OK);
        responseAnswer = objectMapper.writeValueAsString(lessonOutgoingDTO);
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
    setJsonHeader(resp);
    String responseAnswer = "";
    try {
      String[] pathPart = req.getPathInfo().split("/");
      Integer lessonId = Integer.parseInt(pathPart[1]);
      lessonService.delete(lessonId);
      resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
    Optional<LessonIncomingDTO> lessonResponse;
    try {
      lessonResponse = Optional.ofNullable(objectMapper.readValue(json, LessonIncomingDTO.class));
      LessonIncomingDTO lessonIncomingDTO = lessonResponse.orElseThrow(IllegalArgumentException::new);
      responseAnswer = objectMapper.writeValueAsString(lessonService.save(lessonIncomingDTO));
    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect lesson Object.";
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
    Optional<LessonUpdateDTO> lessonResponse;
    try {
      lessonResponse = Optional.ofNullable(objectMapper.readValue(json, LessonUpdateDTO.class));
      LessonUpdateDTO lessonUpdateDTO = lessonResponse.orElseThrow(IllegalArgumentException::new);
      lessonService.update(lessonUpdateDTO);
    } catch (NotFoundException e) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      responseAnswer = e.getMessage();
    }catch (Exception e){
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      responseAnswer = "Incorrect lesson Object.";
    }
    PrintWriter printWriter = resp.getWriter();
    printWriter.write(responseAnswer);
    printWriter.flush();
  }
}
