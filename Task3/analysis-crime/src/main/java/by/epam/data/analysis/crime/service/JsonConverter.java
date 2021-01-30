package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.entity.CrimeLocation;
import by.epam.data.analysis.crime.entity.OutcomeStatus;
import by.epam.data.analysis.crime.entity.Street;
import org.json.JSONObject;

public interface JsonConverter {
    Crime jsonToCrime(JSONObject jsonObject);

    CrimeLocation jsonToCrimeLocation(JSONObject jsonObject);

    Street jsonToStreet(JSONObject jsonObject);

    OutcomeStatus jsonToOutcomeStatus(JSONObject jsonObject);
}
