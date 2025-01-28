import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { loadTracks } from '../../store/actions/track.actions';
import { selectAllTracks } from '../../store/selectors/track.selectors';
import { Track } from '../../models/track.model';
import { Observable, map, BehaviorSubject, switchMap } from 'rxjs';
import { TrackService } from '../../services/track.service';
import Swal from 'sweetalert2';
import { MatIconModule } from '@angular/material/icon';
import { TrackCardComponent } from '../track-card/track-card.component';
import { SongService } from '../../services/song.service';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-library',
  templateUrl: './library.component.html',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, TrackCardComponent]
})
export class LibraryComponent implements OnInit {
  songs$ = new BehaviorSubject<any[]>([]);
  searchTerm: string = '';
  selectedCategory: string = 'all';
  environment = environment;
  isAdmin$ = this.authService.getCurrentUser().pipe(
    map(user => user?.role === 'ADMIN')
  );

  constructor(
    private store: Store,
    private trackService: TrackService,
    private songService: SongService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.loadSongs();
  }

  loadSongs() {
    this.songService.getAllSongs().subscribe({
      next: (response) => {
        console.log('Chansons reçues:', response);
        this.songs$.next(response.content || response);
      },
      error: (error) => {
        console.error('Erreur lors du chargement des chansons:', error);
      }
    });
  }

  onSearchChange(event: Event) {
    const searchTerm = (event.target as HTMLInputElement).value;
    if (searchTerm.trim()) {
      this.songService.searchSongs(searchTerm).subscribe({
        next: (response) => {
          this.songs$.next(response.content);
        },
        error: (error) => {
          console.error('Erreur lors de la recherche:', error);
        }
      });
    } else {
      this.loadSongs();
    }
  }

  onCategoryChange(category: string) {
    this.selectedCategory = category;
    // Implémenter le filtrage par catégorie si nécessaire
  }

  async confirmDelete(trackId: string) {
    const result = await Swal.fire({
      title: 'Êtes-vous sûr?',
      text: "Vous ne pourrez pas revenir en arrière!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Oui, supprimez-le!'
    });

    if (result.isConfirmed) {
      this.deleteTrack(trackId);
    }
  }

  async deleteTrack(trackId: string) {
    try {
      await this.trackService.deleteTrack(trackId);
      this.store.dispatch(loadTracks());
      Swal.fire('Supprimé!', 'La piste a été supprimée.', 'success');
    } catch (error) {
      console.error('Erreur lors de la suppression:', error);
      Swal.fire('Erreur!', 'Une erreur est survenue lors de la suppression de la piste', 'error');
    }
  }

  getCoverUrl(coverUrl: any): string {
    if (coverUrl instanceof File) {
      return URL.createObjectURL(coverUrl);
    }
    return coverUrl || 'assets/default-cover.png';
  }
}
