import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Video} from "../entity/video.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService
{

  constructor(private httpClient: HttpClient)
  {
  }

  getVideos(): Observable<Video[]>
  {
    return this.httpClient.get<Video[]>('api/videos')
  }
}
