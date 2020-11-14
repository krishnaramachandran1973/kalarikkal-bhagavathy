import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Message} from "../entity/Message.model";
import {NgwWowService} from "ngx-wow";
import {MessageService} from "../services/message.service";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})

export class ContactComponent implements OnInit
{
  contactForm: FormGroup
  name: AbstractControl;
  email: AbstractControl;
  phone: AbstractControl;
  status: AbstractControl;
  message: AbstractControl;

  options = ['India', 'Abroad'];

  messageReceived: boolean = false;
  pleaseWait: boolean = false;
  buttonFlag = true;

  constructor(private wowService: NgwWowService, fb: FormBuilder, private messageService: MessageService)
  {
    this.contactForm = fb.group({
      'name': ['', [Validators.required, this.noWhitespaceValidator]],
      'email': ['', [Validators.email, this.noWhitespaceValidator]],
      'phone': ['', [Validators.required, this.noWhitespaceValidator]],
      'status': ['', [Validators.required, this.noWhitespaceValidator]],
      'message': ['', [Validators.required, this.noWhitespaceValidator]]
    });

    this.name = this.contactForm.controls['name'];
    this.email = this.contactForm.controls['email'];
    this.phone = this.contactForm.controls['phone'];
    this.status = this.contactForm.controls['status'];
    this.message = this.contactForm.controls['message'];
  }

  ngOnInit(): void
  {
    this.wowService.init();
  }

  onSubmit(message: Message)
  {
    this.messageService.sendMessage(message)
      .subscribe(() =>
        {
          this.messageReceived = true;
          this.pleaseWait = false;
          this.contactForm.reset();
          this.buttonFlag = true;
          setTimeout(() => this.messageReceived = false, 3500);
        },
        error =>
        {
          this.messageReceived = false;
          this.pleaseWait = false;
          this.buttonFlag = true;
          this.contactForm.reset();
          setTimeout(() => this.messageReceived = false, 3500);
        });

  }

  private noWhitespaceValidator(control: FormControl)
  {
    const isWhitespace = (control.value || '').trim().length === 0;
    const isValid = !isWhitespace;
    return isValid ? null : {whitespace: true};
  }

}
