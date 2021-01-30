package by.epam.data.analysis.crime.service.impl;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.entity.CrimeLocation;
import by.epam.data.analysis.crime.entity.OutcomeStatus;
import by.epam.data.analysis.crime.entity.Street;
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
        crime.setMonth(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_MONTH));
        crime.setPersistentId(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_PERSISTENT_ID));
        crime.setLocationType(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_LOCATION_TYPE));
        crime.setLocationSubtype(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_SUBTYPE));
        crime.setCrimeLocation(jsonToCrimeLocation(jsonObject
                .getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_LOCATION)));
        if (jsonObject.has(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS)
                && !jsonObject.isNull(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS)) {
            crime.setOutcomeStatus(jsonToOutcomeStatus(jsonObject
                    .getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_CRIME_OUTCOME_STATUS)));
        }
        return crime;
    }

    @Override
    public CrimeLocation jsonToCrimeLocation(JSONObject jsonObject) {
        CrimeLocation crimeLocation = new CrimeLocation();
        crimeLocation.setLatitude(jsonObject.getDouble(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_LATITUDE));
        crimeLocation.setLongitude(jsonObject.getDouble(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_LONGITUDE));
        crimeLocation.setStreet(jsonToStreet(jsonObject
                .getJSONObject(StreetLevelCrimesJsonKey.JSON_KEY_LOCATION_STREET)));
        return crimeLocation;
    }

    @Override
    public Street jsonToStreet(JSONObject jsonObject) {
        Street street = new Street();
        street.setId(jsonObject.getLong(StreetLevelCrimesJsonKey.JSON_KEY_STREET_ID));
        street.setName(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_STREET_NAME));
        return street;
    }

    @Override
    public OutcomeStatus jsonToOutcomeStatus(JSONObject jsonObject) {
        OutcomeStatus outcomeStatus = new OutcomeStatus();
        outcomeStatus.setCategory(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_OUTCOME_STATUS_CATEGORY));
        outcomeStatus.setDate(jsonObject.getString(StreetLevelCrimesJsonKey.JSON_KEY_OUTCOME_STATUS_DATE));
        return outcomeStatus;
    }
}
