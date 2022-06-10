package ro.ubb.movieapp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.movieapp.core.model.Actor;
import ro.ubb.movieapp.core.repository.ActorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService{
    @Autowired
    ActorRepository actorRepository;

    @Override
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public List<Actor> getAllAvailableActors() {
        return actorRepository.findAll().stream().filter(
                actor -> actor.getMovieId() == 0
        ).collect(Collectors.toList());
    }
}
