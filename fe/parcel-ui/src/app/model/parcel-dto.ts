export interface ParcelDTO {
  parcelId: string;
  address: {
    name: string;
    street: string;
    number: string;
    city: string;
    postcode: string;
  };
}
