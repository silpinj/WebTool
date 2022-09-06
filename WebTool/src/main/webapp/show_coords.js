/**
 * 
 */
function formatCoordinate(coordinate) {
	var array = coordinate.split(",");
	var lon = array[0];
	var lat = array[1];
	return `
    <table bgcolor='white' bordercolor="black">
      <tbody>
        <tr><th>Lon: </th><td>${lon}</td></tr>
        <tr><th>Lat: </th><td>${lat}</td></tr>
      </tbody>
    </table>`;
}