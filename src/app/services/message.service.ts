import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Message} from "../entity/Message.model";

@Injectable({
    providedIn: 'root'
})
export class MessageService
{

    constructor(private http: HttpClient)
    {
    }

    sendMessage(message: Message)
    {
        return this.http.post('api/message', message);
    }

}
