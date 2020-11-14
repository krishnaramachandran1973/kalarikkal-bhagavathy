import {Component, OnInit} from '@angular/core';
import {NgwWowService} from "ngx-wow";

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit
{

  constructor(private wowService: NgwWowService)
  {
  }

  ngOnInit(): void
  {
    this.wowService.init();
  }

}
