export interface Track {
    id?: string;
    title: string;
    artist: string;
    album?: string;
    duration: number;
    coverUrl?: string;
    description?: string;
    addedDate?: Date;
    audioUrl: string;
    category: string;
}

export enum MusicCategory {
    POP = 'pop',
    ROCK = 'rock',
    RAP = 'rap',
    CHAABI = 'cha3bi',
    JAZZ = 'jazz',
    CLASSIQUE = 'classique',
    RAI = 'rai',
    OTHER = 'other',
}
  