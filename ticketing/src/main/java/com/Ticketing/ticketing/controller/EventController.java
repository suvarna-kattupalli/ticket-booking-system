package com.Ticketing.ticketing.controller;

import com.Ticketing.ticketing.entity.Event;
import com.Ticketing.ticketing.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {

        return eventService.getEventById(id);
    }

    @GetMapping
    public List<Event> getEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }
}

