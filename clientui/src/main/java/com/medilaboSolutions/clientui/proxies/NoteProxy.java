package com.medilaboSolutions.clientui.proxies;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medilaboSolutions.clientui.beans.NoteBean;

@ComponentScan
@FeignClient(name = "microservice-notemanager", url = "http://localhost:9003")
public interface NoteProxy {
  @GetMapping(value = "/note/patientid/{patientid}")
  List<NoteBean> getAllNote(@PathVariable("patientid") int patientid);

  @GetMapping(value = "/note/{id}")
  Optional<NoteBean> getNoteById(@PathVariable("id") String id);

  @PostMapping(value = "/note")
  @ResponseStatus(HttpStatus.CREATED)
  NoteBean saveNote(@RequestBody NoteBean note);

  @DeleteMapping(value = "/note/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void deletePatient(@PathVariable("id") String noteId);
}
