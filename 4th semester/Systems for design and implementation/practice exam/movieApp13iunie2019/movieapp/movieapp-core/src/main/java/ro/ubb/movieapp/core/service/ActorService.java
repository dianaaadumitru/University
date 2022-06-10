package ro.ubb.movieapp.core.service;

import ro.ubb.movieapp.core.model.Actor;

import java.util.List;

public interface ActorService {
    List<Actor> getAllActors();
    List<Actor> getAllAvailableActors(); //returns all actors who are not currently distributed in a movie
}
