import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {SubscriptionService} from "../services/subscription.service";
import {Subscriber} from "../entity/Subscriber.model";

@Component({
  selector: 'app-verify',
  templateUrl: './verify.component.html',
  styleUrls: ['./verify.component.css']
})
export class VerifyComponent implements OnInit
{
  code: string;
  subscriber: Subscriber = new Subscriber()

  constructor(private route: ActivatedRoute, private subscriptionService: SubscriptionService, private router: Router)
  {
    route.params.subscribe(params =>
    {
      this.code = params['code'];
    });
  }

  ngOnInit(): void
  {
    this.subscriptionService.getSubscription(this.code)
      .subscribe(subscriber => this.subscriber = subscriber)
  }

  goHome()
  {
    this.router.navigateByUrl("")
  }
}
