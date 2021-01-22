package by.epam.data.analysis.crime.service;

import by.epam.data.analysis.crime.entity.Crime;
import by.epam.data.analysis.crime.entity.CrimeLocation;
import org.json.JSONObject;

public interface JsonConverter {
    Crime jsonToCrime(JSONObject jsonObject);
    CrimeLocation jsonToCrimeLocation(JSONObject jsonObject);
}
