**NewsData app**
Login and browse through latest news from around the world. News articles are available in English and Czech language. 
Did you like the article? Share the link with others!
Displayed articles are obtained from: https://newsdata.io/ (free-plan version).

| Login Screen | Articles Screen | Detail Screen |
| ------------ | ------------ | ------------ |
| ![Login Screen](https://github.com/user-attachments/assets/d46e4910-a233-4e5c-9b28-f4e0de695f6a) | ![Articles Screen](https://github.com/user-attachments/assets/1bb3a1b0-98f2-4928-b32e-270633509063) | ![NewsDataGoogle_Detail](https://github.com/user-attachments/assets/e66d8b04-caa5-46c2-8935-b583366a9ff1) | 



**NewsData app functionality**
- Login screen | News screen | News detail | Profile screen
- User can authenticate with **predefined** username and password which are statically checked and stored or by login with **GoogleAuthentication**-> if valid -login
- News data are displayed only if user is authenticated
- Remote data can be fully -refetch by clicking on the update button or by "Pull down gesture"
- If user scrolls to the bottom of the list - more data will be fetched
- Basic fetch error data handling
- Share url of received data
- Logout

**NewsData doesn't include**
- Displayed data are not stored, and every time when app is cold-started must be fetched again
- Dark mode
- Super cool design :-)

**Code structure**
- Application is divided into 4 main packages(features): **auth**, **core**, **news**, **profile** and **app**. 
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
 - Credential Manager
 - Material library
 - MVI architecture for presentation layer

**Support links**
 - https://coil-kt.github.io/coil/compose/
 - https://ktor.io/docs/client-create-new-application.html
 - https://insert-koin.io/docs/quickstart/android-compose/
 - https://developer.android.com/reference/androidx/security/crypto/EncryptedSharedPreferences
 - https://developer.android.com/identity/sign-in/credential-manager-siwg
 - https://newsdata.io/

**TODO**
- Automatic testing
- Possible errors fix

  
