import {Component, OnInit} from '@angular/core';
import {Video} from "../entity/video.model";
import {NgwWowService} from "ngx-wow";
import {VideoService} from "../services/video.service";

@Component({
  selector: 'app-videos',
  templateUrl: './videos.component.html',
  styleUrls: ['./videos.component.css']
})
export class VideosComponent implements OnInit
{
  videos: Video[] = [];
  p: number = 1;

  constructor(private wowService: NgwWowService, private videoService: VideoService)
  {
    this.videoService.getVideos()
      .subscribe(videos =>
      {
        this.videos = videos;
      });
  }

  ngOnInit(): void
  {
  }

}
