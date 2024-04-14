// Authorization token that must have been created previously. See : https://developer.spotify.com/documentation/web-api/concepts/authorization
const token = 'BQAKgJSapiBbmTJVS7ntjsfByZBB25WvVXJoCuJdPFF7lnrjnXZQ0xHiISUUFUpFqyO_UWZQIcc6UeEVm_oGwCaUVHg2CDTFoUgGu1yNlZd2DcbbfFRosArgh_cgS6Q9leJhoBHxoX05lv5CZ2dAOQQYHPyBuCqFxk2JWUUh2faIOTsdg3kHLK8d_aXMpYGuV4Wbi9Mo05mQYZ36953gwR7QAA7HydRwJFNRklo3r4jWH5y2PGqC3fdwUuqBkZEi5AP3hY15X4qLGiV6sDwrv3BGVUke';
async function fetchWebApi(endpoint, method, body) {
  const res = await fetch(`https://api.spotify.com/${endpoint}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    method,
    body:JSON.stringify(body)
  });
  return await res.json();
}

async function getTopTracks(){
  // Endpoint reference : https://developer.spotify.com/documentation/web-api/reference/get-users-top-artists-and-tracks
  return (await fetchWebApi(
    'v1/me/top/tracks?time_range=long_term&limit=5', 'GET'
  )).items;
}

const topTracks = await getTopTracks();
console.log(
  topTracks?.map(
    ({name, artists}) =>
      `${name} by ${artists.map(artist => artist.name).join(', ')}`
  )
);