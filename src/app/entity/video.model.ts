import {Deserializable} from "./common/Deserializable.model";

export class Video implements Deserializable
{
  id: number;
  name: string;
  subject: string;
  date: string;

  deserialize(input: any): this
  {
    Object.assign(this, input);
    return this;
  }
}
