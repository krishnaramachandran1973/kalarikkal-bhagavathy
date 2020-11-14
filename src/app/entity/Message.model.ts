import {Deserializable} from "./common/Deserializable.model";


export class Message implements Deserializable
{
    name: string;
    email: string;
    phone: string;
    status: string;
    message: string;

    deserialize(input: any): this
    {
        Object.assign(this, input);
        return this;
    }
}
