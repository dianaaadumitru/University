import { Component, OnInit } from '@angular/core';
import { Actor } from '../actor.model';
import { MovieApp } from '../movieapp.model';
import { MovieappService } from '../movieapp.service';
import { MovieWithActors } from '../movieWithActors.model';

@Component({
  selector: 'app-movieapp',
  templateUrl: './movieapp.component.html',
  styleUrls: ['./movieapp.component.css']
})
export class MovieappComponent implements OnInit {

  constructor(private movieAppService: MovieappService) { }

  type: any = ""
  year: number = 0
  movies: Array<MovieApp> =[]
  moviesWithActors: Array<MovieWithActors> = []

  ngOnInit(): void {
  }

  searchByYear(year: any) {
    // console.log(this.type)
    if (year == "" || !Number(year)){
      alert("you must provide a year!")
      return
    }
    this.year = year

    if (this.type =="") {
      alert("you must choose a type!")
      return
    }

    this.getMovies(year, this.type)
  }

  changeValue(e) {
    this.type = e.target.value
  }

  getMovies(year: number, lessThan: boolean) {
    this.movieAppService.getMovies(year, lessThan).subscribe(
      res => {
        this.movies = res
        // console.log(this.movies)
      }
    )
  }

  showCast() {
    // console.log(this.year, this.type)
    this.getMoviesWithActors(this.year, this.type)
  }

  getMoviesWithActors(year: number, lessThan: boolean) {
    this.movieAppService.getMoviesWithActors(year, lessThan).subscribe(
      res => {
        this.moviesWithActors = res
        // console.log(this.moviesWithActors)
      }
    )
  }

  deleteActor(actor: Actor) {
    this.movieAppService.deleteActor(actor.movieId, actor.id).subscribe(
      _ => {
        console.log("actor deleted")
        for (let i = 0 ; i < this.moviesWithActors.length; i++) {
          if (this.moviesWithActors[i].id == actor.movieId) {
            
            this.moviesWithActors[i].actors = this.moviesWithActors[i].actors.filter(
              a => a.id !== actor.id
            )
          }
        }
      }
    )
  }
}
