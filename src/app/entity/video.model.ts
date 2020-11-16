import {Deserializable} from "./common/Deserializable.model";

export class Video implements Deserializable
{
  name: string;
  url: string;
  date: string


  deserialize(input: any): this
  {
    Object.assign(this, input);
    return this;
  }
}
