package by.epam.data.analysis.crime.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreetLevelCrimesJsonKey {
    public static final String JSON_KEY_CRIME_CATEGORY = "category";
    public static final String JSON_KEY_CRIME_LOCATION_TYPE = "location_type";
    public static final String JSON_KEY_CRIME_LOCATION = "location";
    public static final String JSON_KEY_CRIME_CONTEXT = "context";
    public static final String JSON_KEY_CRIME_OUTCOME_STATUS = "outcome_status";
    public static final String JSON_KEY_PERSISTENT_ID = "persistent_id";
    public static final String JSON_KEY_CRIME_ID = "id";
    public static final String JSON_KEY_LOCATION_SUBTYPE = "location_subtype";
    public static final String JSON_KEY_CRIME_MONTH = "month";

    public static final String JSON_KEY_LOCATION_LATITUDE = "latitude";
    public static final String JSON_KEY_LOCATION_STREET = "street";
    public static final String JSON_KEY_LOCATION_LONGITUDE = "latitude";

    public static final String JSON_KEY_STREET_ID = "id";
    public static final String JSON_KEY_STREET_NAME = "name";

    public static final String JSON_KEY_OUTCOME_STATUS_CATEGORY = "category";
    public static final String JSON_KEY_OUTCOME_STATUS_DATE = "date";
}
