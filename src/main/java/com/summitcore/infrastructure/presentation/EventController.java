package com.summitcore.infrastructure.presentation;

import com.summitcore.core.entities.Event;
import com.summitcore.core.useCases.CreateEventCase;
import com.summitcore.core.useCases.FindEventCase;
import com.summitcore.core.useCases.FindAllEventCase;
import com.summitcore.core.useCases.FindByIdEventCase;
import com.summitcore.infrastructure.mapper.EventMapper;
import com.summitcore.infrastructure.request.EventRequest;
import com.summitcore.infrastructure.response.ApiResponse;
import com.summitcore.infrastructure.response.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor

public class EventController {

    private final CreateEventCase createEventCase;
    private final FindAllEventCase findAllEventCase;
    private final FindByIdEventCase findByIdEventCase;
    private final FindEventCase findEventCase;

    @PostMapping()
    public ResponseEntity<ApiResponse<EventResponse>>create(@RequestBody EventRequest request){
        Event newEvent = createEventCase.execute(EventMapper.toEvent(request));
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>("Event registered successfully",EventMapper.toEventResponse(newEvent)));
    }
    @GetMapping()
    public ResponseEntity<List<EventResponse>> getAll(){
        return ResponseEntity.ok().body(findAllEventCase.execute()
            .stream().map(EventMapper::toEventResponse)
            .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id){
        return findByIdEventCase.execute(id)
            .map((event -> ResponseEntity.ok()
            .body(EventMapper.toEventResponse(event))))
            .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/filter/{identify}")
    public ResponseEntity<EventResponse> findByIdentify(@PathVariable String identify){
        return findEventCase.execute(identify).map((eventFilter -> ResponseEntity.ok()
            .body(EventMapper.toEventResponse(eventFilter))))
            .orElse(ResponseEntity.notFound().build());
    }

}
