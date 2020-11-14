import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {NgwWowService} from "ngx-wow";
import {SubscriptionService} from "../services/subscription.service";
import {Subscriber} from "../entity/Subscriber.model";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit
{
  subscribeForm: FormGroup;
  name: AbstractControl;
  email: AbstractControl;
  subscriptionSaved = false;
  subscriptionFailed = false;
  buttonFlag = true;
  pleaseWait = false;

  constructor(private wowService: NgwWowService, fb: FormBuilder, private subscriptionService: SubscriptionService)
  {
    this.subscribeForm = fb.group({
      name: ['', [Validators.required, FooterComponent.noWhitespaceValidator]],
      email: ['', [Validators.required, FooterComponent.noWhitespaceValidator]],
    });
  }

  ngOnInit(): void
  {
    this.wowService.init();
  }

  onSubmit(value: Subscriber)
  {
    this.buttonFlag = false;
    this.pleaseWait = true;
    this.subscriptionService.sendSubscription(value)
      .subscribe(() =>
        {
          this.subscriptionSaved = true;
          this.pleaseWait = false;
          this.subscribeForm.reset();
          this.buttonFlag = true;
          setTimeout(() => this.subscriptionSaved = false, 3500);
        },
        () =>
        {
          this.subscriptionSaved = false;
          this.subscriptionFailed = true;
          this.pleaseWait = false;
          this.buttonFlag = true;
          this.subscribeForm.reset();
          setTimeout(() => this.subscriptionFailed = false, 3500);
        });
  }

  private static noWhitespaceValidator(control: FormControl)
  {
    const isWhitespace = (control.value || '').trim().length === 0;
    const isValid = !isWhitespace;
    return isValid ? null : {whitespace: true};
  }

}
