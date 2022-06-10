import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Actor } from '../actor.model';
import { Movie } from '../movie.model';
import { MovieappService } from '../movieapp.service';
import { MovieWithActors } from '../movieWithActors.model';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  movie: MovieWithActors
  movieId: number = 0
  buttonClicked: boolean = false
  actors: Array<Actor> = []
  actorId: number = 0
  selectedActor: Actor
  indexActor: number = 0

  constructor(private router: Router,
    private service: MovieappService) { }

  ngOnInit(): void {
    this.movieId = Number(localStorage.getItem("movieId"))
    this.getMovie()
  }

  getMovie() {
    this.service.getMovie(this.movieId).subscribe(
      res => {
        this.movie = res
      }
    )
  }

  addActorsButton() {
    this.buttonClicked = true
    this.getActors()
  }

  getActors() {
    this.service.getAvailableAcors().subscribe(
      res => {
        console.log("actors", res)
        this.actors = res
      }
    )
  }

  getActorIndex(index: number) {
    this.actorId = this.actors[index].id
    this.selectedActor = this.actors[index]
    this.indexActor = index
  }

  addActor() {
    this.movie.actors.push(this.selectedActor)
    delete this.actors[this.indexActor]
    this.service.addActor(this.movieId, this.actorId).subscribe(
      res => console.log("actor added")
    )
  }

  goBack() {
    this.router.navigate(["/movieapp/movies"])
  }

}
