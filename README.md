# NewsAPIDemo

NewsAPIDemo uses Dagger-Hilt, Coroutines, Flow, Jetpack libraries (Room, ViewModel, LiveData) based on MVVM architecture.

##### Best Practices used in code;
- GitHub actions (CI/CD, automated integration) 🤩
- Kotlin language (main development language)
- MVVM architecture
- View-binding
- Navigation Components (Transitions between fragments)
- Coroutines (Concurrency and non-blocking executions)
- Room library (Storing data locally in android phone)
- Retroıfit library (Connecting application to back-end services (REST and JSON))
- Dagger-Hilt (Dependency injection)
- Paging 3 (Pagination for downloading more articles)
- News API (Providing JSON data based on API_KEY)
- Navigation Args (Passing parcelable objects between fragments)
- Higher order, inline, suspend and extension functions

------------



#### Components that used in App;
- **Fragment**: Reusable UI component which display data and handles user interactions.

- **ViewModel**: Gets data from Repository and holds data for fragments. ViewModels are not destroyed if its owner is destroyed for a configuration change (e.g. rotation). The new owner instance just re-connects to the existing model.
 - ***HiltViewModel*** means retrieved by default in an Activity or Fragment annotated with AndroidEntryPoint. The HiltViewModel containing a constructor annotated with Inject will have its dependencies defined in the constructor parameters injected by Dagger's Hilt.
 - ***LiveData***: Special data holder which is lifecycle aware. UI components can subscribe data from LiveData and since LiveData is lifecycle aware, if UI is not visible then LiveData stops dispatching data for partical view. This way memory leaks and data loses are handled.
 -  ***Flow***: Flow in Kotlin is a better way to handle the stream of data asynchronously that executes sequentially.

- **Repository**: Abstraction layer which is a mediator and responsible to retrieve data from different data sources and provides data for ViewModel. For example, different data sources can be Room database for caching or Retrofit for remote networking data.
 - Annotating with @Inject means this constructor is injectable and accept zero or more dependencies as arguments. @Inject can apply to at most one constructor per class.
 - Notice: In application, "Constructor Injection" used instead of "Field Injection". Because when field injection used, later on it cannot be clear what objects injected in Repository and classes will be tightly coupled. 
 For more drawbacks of field Injection: https://stackoverflow.com/a/39892204/4308897 
 - After making the repository as @Singleton and inject it in ViewModels, any ViewModel now has access to that specific repository.

- **Data Classes:**  Data class holds and stores article related data. "data" keyword make sure that equals, hashcode, copy and toString methods will be generated under the hood. So no need to create these methods once again to compare objects.
 - Annotating with @Entity means this class will be stored locally in room library and a mapping SQLite table in the database will be created. Each entity must have at least 1 field annotated with PrimaryKey.
 - Annotating with @Parcelize instructs the Kotlin compiler to generate writeToParcel(),  describeContents() methods which are android.os.Parcelable methods, as well as a CREATOR factory class automatically. Notice, only primary constructor fields will be serialized.

- **companion object** is used in order to create static variables or functions for associated to classes.


------------

#### Flow vs LiveData 
For retrievingand handling data, Kotlin Flow API is used in this project.

**Flow**: An asynchronous data stream that sequentially emits values and completes
normally or with an exception. Execution of the flow is also called collecting the data
which is always performed in a suspending manner without actual blocking. 

Flow API in Kotlin is a better way to handle the stream of data asynchronously
that executes sequentially.

#####   Key Difference between LiveData & Kotlin Flow
> LiveData is used to observe data without having any hazel to handle lifecycle problems.Whereas Kotlin flow is used for continuous data integration and it also simplified the asynchronous programming.Take Room Library as an example. First, it used LiveData to transmit data from the database to UI. It solved most of the existing problems. But when there are any future changes in the database, LiveData is helpless in this situation. After a while, the room used Kotlin flow to solve this problem. With Flow as return-type, room created a new possibility of seamless data integration across the app between database and UI without writing any extra code.

For more details [this medium article](https://medium.com/android-dev-hacks/exploring-livedata-and-kotlin-flow-7c8d8e706324  "this medium article") can be visited.

***Take away,*** as it is recommended to keep flow in the repository level, and make the LiveData a bridge between the UI and the repository, in this app Flow will be used for interactions with Room; and LiveData (which is lifecycle aware) will be used inside ViewModel and UI related components for observing data changes.

#### LiveData vs StateFlow
1. StateFlow needs to have an initial-value, LiveData doesn't (it's an optional)
2. StateFlow will remain active when your app in the background, but with a little setup you can make LiveData as StateFlow
3. With Stateflow, you can use Flow operators, and that is something you can't do with LiveData if you want to use LiveData with Flow you have to do it with a little bit more

------------

#### UsageUsage
###### To Get Api Key
- Go to https://newsapi.org/account
- Create an account
- Get an API Key
- Add this key in local.properties file as
`NEWS_API_KEY="Your API key here"`

###### To Import project from GitHub
- Start Android Studio -> Get from Version Control 
- Paste below link
`https://github.com/ercanduman/NewsAPIDemo.git `
- Rebuild project

------------

### Resources:
News API: https://newsapi.org/docs

------------

#### TODO:
Add Application Unit, UI and Instrumentation tests
Add Test Coverage result to readme file.
