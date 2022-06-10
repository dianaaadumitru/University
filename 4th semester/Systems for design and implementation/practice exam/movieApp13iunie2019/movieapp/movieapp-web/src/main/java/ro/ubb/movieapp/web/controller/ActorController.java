package ro.ubb.movieapp.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.core.service.ActorService;
import ro.ubb.movieapp.web.converter.ActorConverter;
import ro.ubb.movieapp.web.dto.ActorsDTO;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Slf4j
public class ActorController {
    @Autowired
    ActorService actorService;
    @Autowired
    ActorConverter actorConverter;

    @RequestMapping(value = "/availableActors", method = RequestMethod.GET)
    ActorsDTO getAvailableActors() {
        List<Actor> actors = actorService.getAllAvailableActors();
        ActorsDTO dtos = new ActorsDTO(actorConverter.convertModelsToDtos(actors));
        return dtos;
    }
}
