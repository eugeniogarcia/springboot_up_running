package com.thehecklers.planefinder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Aircraft {
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	public String getSquawk() {
		return squawk;
	}

	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getFlightno() {
		return flightno;
	}

	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getVertRate() {
		return vertRate;
	}

	public void setVertRate(int vertRate) {
		this.vertRate = vertRate;
	}

	public int getSelectedAltitude() {
		return selectedAltitude;
	}

	public void setSelectedAltitude(int selectedAltitude) {
		this.selectedAltitude = selectedAltitude;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getBarometer() {
		return barometer;
	}

	public void setBarometer(double barometer) {
		this.barometer = barometer;
	}

	public double getPolarDistance() {
		return polarDistance;
	}

	public void setPolarDistance(double polarDistance) {
		this.polarDistance = polarDistance;
	}

	public double getPolarBearing() {
		return polarBearing;
	}

	public void setPolarBearing(double polarBearing) {
		this.polarBearing = polarBearing;
	}

	public boolean isADSB() {
		return isADSB;
	}

	public void setADSB(boolean isADSB) {
		this.isADSB = isADSB;
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public void setOnGround(boolean isOnGround) {
		this.isOnGround = isOnGround;
	}

	public Instant getLastSeenTime() {
		return lastSeenTime;
	}

	public void setLastSeenTime(Instant lastSeenTime) {
		this.lastSeenTime = lastSeenTime;
	}

	public Instant getPosUpdateTime() {
		return posUpdateTime;
	}

	public void setPosUpdateTime(Instant posUpdateTime) {
		this.posUpdateTime = posUpdateTime;
	}

	public Instant getBds40SeenTime() {
		return bds40SeenTime;
	}

	public void setBds40SeenTime(Instant bds40SeenTime) {
		this.bds40SeenTime = bds40SeenTime;
	}

	public Aircraft() {
		super();
	}

	@Id
    @GeneratedValue
    private Long id;
    private String callsign, squawk, reg, flightno, route, type, category;

    public Aircraft(Long id, String callsign, String squawk, String reg, String flightno, String route, String type,
			String category, int altitude, int heading, int speed, int vertRate, int selectedAltitude, double lat,
			double lon, double barometer, double polarDistance, double polarBearing, boolean isADSB, boolean isOnGround,
			Instant lastSeenTime, Instant posUpdateTime, Instant bds40SeenTime) {
		super();
		this.id = id;
		this.callsign = callsign;
		this.squawk = squawk;
		this.reg = reg;
		this.flightno = flightno;
		this.route = route;
		this.type = type;
		this.category = category;
		this.altitude = altitude;
		this.heading = heading;
		this.speed = speed;
		this.vertRate = vertRate;
		this.selectedAltitude = selectedAltitude;
		this.lat = lat;
		this.lon = lon;
		this.barometer = barometer;
		this.polarDistance = polarDistance;
		this.polarBearing = polarBearing;
		this.isADSB = isADSB;
		this.isOnGround = isOnGround;
		this.lastSeenTime = lastSeenTime;
		this.posUpdateTime = posUpdateTime;
		this.bds40SeenTime = bds40SeenTime;
	}

	private int altitude, heading, speed;
    @JsonProperty("vert_rate")
    private int vertRate;
    @JsonProperty("selected_altitude")
    private int selectedAltitude;

    private double lat, lon, barometer;
    @JsonProperty("polar_distance")
    private double polarDistance;
    @JsonProperty("polar_bearing")
    private double polarBearing;

    @JsonProperty("is_adsb")
    private boolean isADSB;
    @JsonProperty("is_on_ground")
    private boolean isOnGround;

    @JsonProperty("last_seen_time")
    private Instant lastSeenTime;
    @JsonProperty("pos_update_time")
    private Instant posUpdateTime;
    @JsonProperty("bds40_seen_time")
    private Instant bds40SeenTime;

    public Aircraft(String callsign, String reg, String flightno, String type,
                    int altitude, int heading, int speed,
                    double lat, double lon) {

        this(null, callsign, "sqwk", reg, flightno, "route", type, "ct",
                altitude, heading, speed, 0, 0,
                lat, lon, 0D, 0D, 0D,
                false, true,
                Instant.now(), Instant.now(), Instant.now());
    }

    public void setLastSeenTime(long lastSeenTime) {
        this.lastSeenTime = Instant.ofEpochSecond(lastSeenTime);
    }

    public void setPosUpdateTime(long posUpdateTime) {
        this.posUpdateTime = Instant.ofEpochSecond(posUpdateTime);
    }

    public void setBds40SeenTime(long bds40SeenTime) {
        this.bds40SeenTime = Instant.ofEpochSecond(bds40SeenTime);
    }
}
