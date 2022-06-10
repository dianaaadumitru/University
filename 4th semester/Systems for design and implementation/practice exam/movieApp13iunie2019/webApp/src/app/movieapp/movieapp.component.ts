import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from '../movie.model';
import { MovieappService } from '../movieapp.service';

@Component({
  selector: 'app-movieapp',
  templateUrl: './movieapp.component.html',
  styleUrls: ['./movieapp.component.css']
})
export class MovieappComponent implements OnInit {
  movies: Array<Movie> = []
  selectedMovie: Movie
  movieId: number = 0

  constructor(private service: MovieappService,
    private router: Router) { }

  ngOnInit(): void {
  }

  searchByTitle(title: any) {
    if (title == '') {
      alert("title cannot be empty!")
      return
    }
    this.getMovies(title)
  }

  getMovies(title: any) {
    this.service.getMovies().subscribe(
      res => {
        this.movies = res.filter(res => res.title.includes(title))
      }
    )
  }

  onSelect(movie: Movie) {
    this.selectedMovie = movie
    localStorage.setItem('movieId', this.selectedMovie.id.toString())
    this.movieId = movie.id
  }

  gotoDetail() {
    this.router.navigate(["/movieapp/movies/details/" + this.movieId])
  }

}
