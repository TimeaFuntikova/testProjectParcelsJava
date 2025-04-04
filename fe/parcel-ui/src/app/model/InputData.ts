export class InputData {
  parcelId: string;
  address: Address;

  constructor(parcelId: string, address: Address) {
    this.parcelId = parcelId;
    this.address = address;
  }
}

export class Address {
  name: string;
  street: string;
  number: string;
  city: string;
  postcode: string;

  constructor(
    name: string,
    street: string,
    number: string,
    city: string,
    postcode: string
  ) {
    this.name = name;
    this.street = street;
    this.number = number;
    this.city = city;
    this.postcode = postcode;
  }
}
