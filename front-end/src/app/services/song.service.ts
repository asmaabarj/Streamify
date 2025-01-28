import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SongService {
  private apiUrl = `${environment.apiUrl}/api/admin/songs`;

  constructor(private http: HttpClient) {}

  getAllSongs(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  searchSongs(titre: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/search/titre/${titre}`);
  }

  getSongById(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  addSong(formData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  uploadAudio(songId: string, audioFile: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', audioFile);
    return this.http.post(`${this.apiUrl}/${songId}/audio`, formData);
  }

  uploadCover(songId: string, coverFile: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', coverFile);
    return this.http.post(`${this.apiUrl}/${songId}/cover`, formData);
  }
} 