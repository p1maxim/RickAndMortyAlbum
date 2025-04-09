This progect contains two pages (screenshots are attached)
First screen fetched a list of characters from [The Rick and Morty API](https://rickandmortyapi.com)
The first screen contains logic to  search the list by character name.
The second screen fetched and show the details of a character when users clicks on it in the first screen. Details of a character should include:  "name", "status", "gender" and "location.name".
All requests are cacheable in a local database which is implemented using Room. The fetching logic also configurable to get the data straight from the remote source instead of the cached one. This switch is placed on the first screen.
Tech-stack/Arch: MVVM, Kotlin, Coroutines, Flow, Jetpack compose, Room, Retrofit, REST,  Data Storage
