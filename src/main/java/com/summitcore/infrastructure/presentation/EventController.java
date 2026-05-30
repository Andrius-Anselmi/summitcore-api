package com.summitcore.infrastructure.presentation;

import com.summitcore.core.entities.Event;
import com.summitcore.core.useCases.*;
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
@RequestMapping("/api/events")
@RequiredArgsConstructor

public class EventController {

    private final CreateEventUseCase createEventUseCase;
    private final FindAllEventUseCase findAllEventUseCase;
    private final FindByIdEventUseCase findByIdEventUseCase;
    private final FilterEventUseCase filterEventUseCase;
    private final DeleteEventByIdUseCase deleteEventByIdUseCase;
    private final UpdateEventUseCase updateEventUseCase;

    @PostMapping()
    public ResponseEntity<ApiResponse<EventResponse>>create(@RequestBody EventRequest request){
        Event newEvent = createEventUseCase.execute(EventMapper.toEvent(request));
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ApiResponse<>("Event registered successfully",EventMapper.toEventResponse(newEvent)));
    }
    @GetMapping()
    public ResponseEntity<List<EventResponse>> getAll(){
        return ResponseEntity.ok().body(findAllEventUseCase.execute()
            .stream().map(EventMapper::toEventResponse)
            .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(EventMapper.toEventResponse(findByIdEventUseCase.execute(id)));

    }

    @GetMapping("/filter/{identifier}")
    public ResponseEntity<EventResponse> filterByIdentifier(@PathVariable String identifier){
        return ResponseEntity.ok().body(EventMapper.toEventResponse(filterEventUseCase.execute(identifier)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        deleteEventByIdUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EventResponse> updateById(@PathVariable Long id, @RequestBody EventRequest request){
        return ResponseEntity.ok().
                body(EventMapper.toEventResponse(updateEventUseCase.execute(id, EventMapper.toEvent(request))));
    }

}
