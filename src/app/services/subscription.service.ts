import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Subscriber} from "../entity/Subscriber.model";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private http: HttpClient)
  {
  }

  sendSubscription(sub: Subscriber)
  {
    return this.http.post('api/subscribe', sub);
  }


  getSubscription(code: string): Observable<Subscriber>
  {
    console.log('Sending code to verify' + code);
    return this.http.get<Subscriber>("api/subscribe/verify/"+code)
      .pipe(map(value => new Subscriber().deserialize(value)));
  }

}
