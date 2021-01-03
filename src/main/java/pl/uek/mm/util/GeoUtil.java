package pl.uek.mm.util;

public class GeoUtil {

	private static final int R = 6371; // Radius of the earth

	/**
	 * haversine formula
	 * calculate distance between two points in latitude and longitude
	 * @return distance in Meters
	 */
	public static double distance(double lat1, double lat2, double lon1, double lon2) {

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c * 1000;
	}
}
