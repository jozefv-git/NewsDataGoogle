**NewsData app**
Login and browse through latest news from around the world. News articles are available English and Czech language. 
Did you like the article? Share article link with others!
Displayed articles are obtained from: https://newsdata.io/ (free-plan version).

| Login Screen | Articles Screen | Detail Screen |
| ------------ | ------------ | ------------ |
| ![Login Screen](https://github.com/user-attachments/assets/c21daf3d-c843-4a96-b3ad-a6f9df517d25) | ![Articles Screen](https://github.com/user-attachments/assets/fa430b21-82d7-4599-814f-bbde2268837e) |  ![Detail Screen](https://github.com/user-attachments/assets/fa032a6f-be03-4349-82b3-b465ae803b5f) | 



**NewsData app functionality**
- Login screen | News screen | News detail
- User can authenticate with **predefined** username and password which are statically checked and stored -> if valid -login
- Data are accessible if user is authenticated
- Remote data can be fully -refetch by clicking on the update button or by "Pull down gesture"
- If user scrolls to the bottom of the list - more data will be fetched
- Basic fetch error data handling
- Share url of received data
- Logout

**NewsData doesn't include**
- Displayed data are not stored, and every time when app is cold-started must be fetched again
- Dark mode

**Code structure**
- Application is divided into 4 main packages(features): **auth**, **core**, **news** and **app**. 
- **core** can be accessed by every feature on the **same layer** and **app** has access to all features. 
- Other features should not be able to communicate with each other.

Each package is further divided based on the main layers: **data**, **domain** and **presentation**. 
- **domain** can be accessed by **data** and **presentation**. 
- **domain** should be free of any external dependencies.
- **data** and **presentation** should not communicate between each other.

The idea was to structure **NewsData app** in a clear separation patterns, which should help 
with possible migration for multimodule architecture and easier maintenance. 

**Example of data communication**
- **news-data** can access **core-data** (as it is core and same layer)
- **auth-presentation** can access **core-domain** (as it is auth and core (domain layer) - core-domain can be accessed by every feature layer)
- **auth-presentation** cannot access **core-data** (as it is auth and core but layers are not matching)
- **news-data** can acess **core-data** (as it is news and core and layers are matching)
- **news** cannot access **auth**
- **app** can access **news**

**Example of package(feature) structure**
- news
    - data
    - domain
    - presentation

**Used**
 - Kotlin
 - Kotlin coroutines & Flow
 - Jetpack architecture components
 - Coil
 - Ktor
 - Koin
 - Material library
 - MVI architecture for presentation layer

**Support links**
 - https://coil-kt.github.io/coil/compose/
 - https://ktor.io/docs/client-create-new-application.html
 - https://insert-koin.io/docs/quickstart/android-compose/
 - https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
 - https://newsdata.io/

**TODO**
- Automatic testing
- Possible errors fix

  
