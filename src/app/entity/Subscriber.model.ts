import {Deserializable} from "./common/Deserializable.model";


export class Subscriber implements Deserializable
{
    name: string;
    email: string;
    code: string;
    verified: boolean;

    deserialize(input: any): this
    {
        Object.assign(this, input);
        return this;
    }
}
