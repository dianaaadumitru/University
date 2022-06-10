import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { MovieApp } from './movieapp.model';
import { MovieWithActors } from './movieWithActors.model';

@Injectable({
  providedIn: 'root'
})
export class MovieappService {
  private url = "http://localhost:8080/home/movies"

  constructor(private http: HttpClient) { }

  getMovies(year: number, lessThan: boolean): Observable<MovieApp[]> {
    return this.http.get<Array<MovieApp>>(this.url + "/" + year + "/" + lessThan).pipe(map(res => {return res['movies']}))
  }

  getMoviesWithActors(year: number, lessThan: boolean): Observable<MovieWithActors[]> {
    return this.http.get<Array<MovieWithActors>>("http://localhost:8080/home/moviesWithActors/" + year + "/" + lessThan).pipe(map(res => {return res['moviesWithActors']}))

  }

  deleteActor(movieID: number, actorID: number): Observable<any> {
    return this.http.delete("http://localhost:8080/home/delete/" + movieID + "/" + actorID)
  }
}
