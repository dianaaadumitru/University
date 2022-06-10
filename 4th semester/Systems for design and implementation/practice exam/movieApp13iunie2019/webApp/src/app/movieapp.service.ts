import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Actor } from './actor.model';
import { Movie } from './movie.model';
import { MovieWithActors } from './movieWithActors.model';

@Injectable({
  providedIn: 'root'
})
export class MovieappService {
  url = "http://localhost:8080/home/"

  constructor(private http: HttpClient) { }

  getMovies():Observable<Movie[]> {
    return this.http.get<Array<Movie>>(this.url + 'movies').pipe(map(res => { return res['movies']}))
  }

  getMovie(id: number): Observable<MovieWithActors> {
    return this.http.get<MovieWithActors>(this.url + "movieWithActors/" + id)
    // return this.getMovies().pipe(map(movies => movies.find(movie => movie.id === id)))
  }

  getAvailableAcors(): Observable<Actor[]> {
    return this.http.get<Actor[]>(this.url + "availableActors").pipe(map(res => { return res['actors']}))
  }

  addActor(movieId: number, actorId: number) {
    return this.http.get(this.url + "addActor/" + movieId + "/" + actorId)
  }
}

