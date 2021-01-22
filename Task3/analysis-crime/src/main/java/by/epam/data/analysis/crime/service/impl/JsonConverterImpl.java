package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.entity.CrimeLocation;
import by.epam.data.analysis.crime.service.JsonConverter;
import by.epam.data.analysis.crime.service.StreetLevelCrimesJsonKey;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class JsonConverterImpl implements JsonConverter {
    @Override
    public Crime jsonToCrime(JSONObject jsonObject) {
        Crime crime = new Crime();
        crime.setId(jsonObject.getLong(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_ID));
        crime.setCategory(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_CATEGORY));
        crime.setContext(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_CONTEXT));
        crime.setDate(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_MONTH));
        crime.setPersistentId(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_PERSISTENT_ID));
        crime.setOutcomeStatusCategory(jsonObject.optJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS) != null ?
                jsonObject.getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS)
                        .getString(StreetLevelCrimesJsonKey.JSON_KEY_OUTCOME_STATUS_CATEGORY) : null);
        crime.setOutcomeStatusDate(jsonObject.optJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS) != null ?
                jsonObject.getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS)
                        .getString(StreetLevelCrimesJsonKey.JSON_KEY_OUTCOME_STATUS_DATE) : null);
        crime.setCrimeLocation(jsonToCrimeLocation(jsonObject));
        return crime;
    }

    @Override
    public CrimeLocation jsonToCrimeLocation(JSONObject jsonObject) {
        CrimeLocation crimeLocation = new CrimeLocation();
        JSONObject location = jsonObject.getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_LOCATION);
        JSONObject street = location.getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_STREET);
        crimeLocation.setStreetId(street.getLong(StreetLevelCrimesJsonKey.JSON_KEY_STREET_ID));
        crimeLocation.setStreetName(street.getString(StreetLevelCrimesJsonKey.JSON_KEY_STREET_NAME));
        crimeLocation.setLocationSubtype(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_SUBTYPE));
        crimeLocation.setLocationType(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_LOCATION_TYPE));
        crimeLocation.setLatitude(location.getDouble(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_LATITUDE));
        crimeLocation.setLongitude(location.getDouble(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_LONGITUDE));
        return crimeLocation;
    }
}
