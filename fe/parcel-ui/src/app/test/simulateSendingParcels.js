const axios = require('axios');

const cities = [
  { city: "Bratislava", postcode: "81101" },
  { city: "Košice", postcode: "04001" },
  { city: "Prešov", postcode: "08001" },
  { city: "Žilina", postcode: "01001" },
  { city: "Nitra", postcode: "94901" }
];

const generateParcel = (i) => {
  const city = cities[i % cities.length];
  return {
    parcelId: (100000000000 + i).toString(),
    address: {
      name: `User ${i}`,
      street: `Street ${i}`,
      number: `${i + 1}`,
      city: city.city,
      postcode: city.postcode
    }

  };
};

(async () => {
  for (let i = 0; i < 1000; i++) {
    const parcel = generateParcel(i);
    try {
      const res = await axios.post('http://localhost:8080/api/parcels/add', parcel);
      console.log(`✔️ [${i + 1}] Sent to ${parcel.address.city} — Status: ${res.status}`);
    } catch (err) {
      console.error(`❌ [${i + 1}] Failed:`, err.message);
    }
  }
})();
