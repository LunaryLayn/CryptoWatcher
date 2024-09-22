# CryptoWatcher - SwissBorg Technical Challenge

This project is a submission for the **SwissBorg Mobile Tech Challenge**. The app is designed to retrieve and display a list of cryptocurrency trading pairs with their current prices, using the Bitfinex API. The app is built using modern Android development tools, following the **MVVM** pattern with **Clean Architecture**, and includes unit tests for the key business logic.

## Features

- **Real-time Price Updates**: The list of trading pairs is updated automatically every 5 seconds to reflect the latest prices.
- **Search and Filter**: Users can filter the trading pairs using a search bar for quick access to specific pairs.
- **Connectivity Awareness**: The app reacts to connectivity changes, ensuring letting the user know that data may not be up-dated when there are network issues.
- **Dark/Light Mode Support**: The UI supports both dark and light modes, providing an addaptative visual experience in different themes.
- **Unit Testing**: Unit tests are included for the `getTickersUseCase`, as it contains the core business logic of the app.

![6012425624641193228](https://github.com/user-attachments/assets/59daee4c-b8dd-4842-8b31-c734c6092c9a)
![6012425624641193227](https://github.com/user-attachments/assets/1eb20374-65d9-4adb-8240-1dd961baa264)
![6012425624641193226](https://github.com/user-attachments/assets/7b5597ba-729b-487f-9fea-fd271b4a1b01)
![6012425624641193225](https://github.com/user-attachments/assets/2d855d42-a88f-486e-9af5-7b5820e0c335)
![6017217094451577165](https://github.com/user-attachments/assets/9f7b2ba8-15f8-4d17-8ad1-bb95762fefb0)

## Tech Stack

The project utilizes the following technologies and libraries:

### Core Technologies

- **Kotlin**: The primary language for Android development.
- **Jetpack Compose**: For building the UI in a declarative way.
- **MVVM with Clean Architecture**: Ensuring separation of concerns and maintainability.
- **Coroutines**: For handling asynchronous operations and background tasks.
- **Hilt**: For dependency injection.
- **Retrofit**: For network requests to the Bitfinex API.
- **Mockito**: For mocking dependencies in unit tests.
- **JUnit**: For unit testing the business logic.
- **AndroidX**: Core libraries for Android development.
  
## Architecture

This project follows the **MVVM** (Model-View-ViewModel) pattern combined with **Clean Architecture** principles. The architecture ensures separation between the business logic, data management, and UI components:

- **Domain Module**: Contains the core business logic.
- **Data Module**: Responsible for managing data sources and communicating with the Bitfinex API via Retrofit.
- **Presentation Layer**: Built using Jetpack Compose, this layer displays the data retrieved by the ViewModel, reacting to changes in the state.
