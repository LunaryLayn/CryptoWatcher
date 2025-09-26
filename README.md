# CryptoWatcher

ENG: This project is purely a showcase for recruiters to check my approach to different projects. The app is designed to retrieve and display a list of cryptocurrency trading pairs, all of which are always against USD, using the Bitfinex API. The app is built using modern Android development tools, following the **MVVM** pattern with **Clean Architecture**, and includes unit tests for the key business logic.

## Features

- **Real-time Price Updates**: The list of trading pairs is updated automatically every 5 seconds to reflect the latest prices.
- **Search Filter**: Users can filter the trading pairs using a search bar for quick access to specific pairs.
- **Connectivity Awareness**: The app reacts to connectivity changes, ensuring the user knows that data may not be up-to-date when there are network issues.
- **Dark/Light Mode Support**: The UI supports both dark and light modes, providing an adaptive visual experience in different themes.
- **Unit Testing**: Unit tests are included for the `getTickersUseCase`, as it contains the core business logic of the app.

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

## Architecture

This project follows the **MVVM** (Model-View-ViewModel) pattern combined with **Clean Architecture** principles. The architecture ensures separation between the business logic, data management, and UI components:

- **Domain Module**: Contains the core business logic.
- **Data Module**: Responsible for managing data sources and communicating with the Bitfinex API via Retrofit.
- **Presentation Layer**: Built using Jetpack Compose, this layer displays the data retrieved by the ViewModel, reacting to changes in the state.

IMAGES AT THE END OF THIS PRESENTATION.

ESP: # CryptoWatcher

Este proyecto es únicamente una muestra para que los reclutadores puedan revisar mi enfoque en diferentes proyectos. La aplicación está diseñada para obtener y mostrar una lista de pares de criptomonedas, todos siempre contra USD, utilizando la API de Bitfinex. La app está construida con herramientas modernas de desarrollo Android, siguiendo el patrón **MVVM** con **Clean Architecture**, e incluye pruebas unitarias para la lógica de negocio principal.

## Funcionalidades

- **Actualización en tiempo real**: La lista de pares de trading se actualiza automáticamente cada 5 segundos para reflejar los precios más recientes.
- **Filtro de búsqueda**: Los usuarios pueden filtrar los pares mediante una barra de búsqueda para acceder rápidamente a pares específicos.
- **Conciencia de conectividad**: La aplicación reacciona a los cambios de conexión, asegurando que el usuario sepa cuando los datos pueden no estar actualizados en caso de problemas de red.
- **Soporte para modo oscuro/claro**: La interfaz soporta tanto modo oscuro como claro, ofreciendo una experiencia visual adaptable a distintos temas.
- **Pruebas unitarias**: Se incluyen pruebas unitarias para el `getTickersUseCase`, ya que contiene la lógica de negocio central de la aplicación.

## Stack Tecnológico

El proyecto utiliza las siguientes tecnologías y librerías:

### Tecnologías principales

- **Kotlin**: Lenguaje principal para el desarrollo en Android.
- **Jetpack Compose**: Para construir la interfaz de usuario de manera declarativa.
- **MVVM con Clean Architecture**: Asegurando separación de responsabilidades y mantenibilidad.
- **Coroutines**: Para manejar operaciones asíncronas y tareas en segundo plano.
- **Hilt**: Para la inyección de dependencias.
- **Retrofit**: Para las solicitudes de red a la API de Bitfinex.
- **Mockito**: Para simular dependencias en las pruebas unitarias.
- **JUnit**: Para las pruebas unitarias de la lógica de negocio.

## Arquitectura

Este proyecto sigue el patrón **MVVM** (Model-View-ViewModel) combinado con los principios de **Clean Architecture**. La arquitectura garantiza la separación entre la lógica de negocio, la gestión de datos y los componentes de la interfaz de usuario:

- **Módulo de Dominio**: Contiene la lógica de negocio central.
- **Módulo de Datos**: Responsable de gestionar las fuentes de datos y comunicarse con la API de Bitfinex a través de Retrofit.
- **Capa de Presentación**: Construida con Jetpack Compose, esta capa muestra los datos obtenidos por el ViewModel, reaccionando a los cambios en el estado.

## Images

![6012425624641193228](https://github.com/user-attachments/assets/59daee4c-b8dd-4842-8b31-c734c6092c9a)

![6012425624641193227](https://github.com/user-attachments/assets/1eb20374-65d9-4adb-8240-1dd961baa264)

![6012425624641193226](https://github.com/user-attachments/assets/7b5597ba-729b-487f-9fea-fd271b4a1b01)

![6012425624641193225](https://github.com/user-attachments/assets/2d855d42-a88f-486e-9af5-7b5820e0c335)

![6017217094451577165](https://github.com/user-attachments/assets/9f7b2ba8-15f8-4d17-8ad1-bb95762fefb0)
