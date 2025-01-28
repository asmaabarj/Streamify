import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { Track } from '../../models/track.model';

@Component({
  selector: 'app-track-card',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule],
  template: `
    <div class="bg-gray-800 rounded-lg p-3 md:p-4 hover:bg-gray-700 group transform hover:scale-105 md:hover:scale-110 transition-transform duration-500 shadow-xl">
      <div class="relative aspect-square mb-4 overflow-hidden rounded-md">
        <img [src]="getCoverUrl(track.coverUrl)" 
             [alt]="track.title"
             class="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110">
        <button class="absolute right-2 bottom-2 w-12 h-12 bg-spotify-green rounded-full shadow-lg opacity-0 group-hover:opacity-100 transition-opacity duration-300 transform group-hover:translate-y-1"
                [routerLink]="['/track', track.id]">
          <mat-icon class="text-black">play_arrow</mat-icon>
        </button>
      </div>
      
      <h3 class="text-white font-bold truncate">{{ track.title }}</h3>
      <p class="text-gray-400 text-sm truncate">{{ track.artist }}</p>
      
      <div class="mt-3 flex justify-between items-center">
        <div class="flex gap-3">
          <button [routerLink]="['/edit-track', track.id]"
                  class="text-gray-400 hover:text-white transition-colors duration-300 transform hover:scale-110">
            <mat-icon>edit</mat-icon>
          </button>
          <button (click)="onDelete(track.id)"
                  class="text-gray-400 hover:text-red-500 transition-colors duration-300 transform hover:scale-110">
            <mat-icon>delete</mat-icon>
          </button>
        </div>
      </div>
    </div>
  `
})
export class TrackCardComponent {
  @Input() track!: Track;
  @Output() deleteTrack = new EventEmitter<string>();

  onDelete(trackId: string | undefined) {
    if (trackId) {
      this.deleteTrack.emit(trackId);
    }
  }

  getCoverUrl(coverUrl: any): string {
    if (coverUrl instanceof File) {
      return URL.createObjectURL(coverUrl);
    }
    return coverUrl || 'assets/default-cover.png';
  }
} 