package com.blogging.application.api;

import com.blogging.application.service.GraphqlService;
import graphql.ExecutionResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rest/books")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

  @NonNull private final GraphqlService graphqlService;

  @GetMapping("/hello")
  public String helloMsg() {
    log.trace("A TRACE Message");
    log.debug("A DEBUG Message");
    log.info("An INFO Message");
    log.warn("A WARN Message");
    log.error("An ERROR Message");
    return graphqlService.helloMsg();
  }

  @PostMapping
  public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
    ExecutionResult execute = graphqlService.getGraphQL().execute(query);

    return new ResponseEntity<>(execute, HttpStatus.OK);
  }
}
