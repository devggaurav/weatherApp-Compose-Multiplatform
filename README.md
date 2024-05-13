This is a Kotlin Multiplatform Weather Application targeting Android, iOS. 

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.




![iPhone 15 Pro]
<img src="https://github.com/devggaurav/weatherAppCmm/assets/42926809/6bf64eaf-3324-43ae-b4ca-d2abe9d84d26" height="500px"> 
![Android]
<img src="https://github.com/devggaurav/weatherAppCmm/assets/42926809/d8a4fe3e-02a0-433c-a7ae-a3c136c34e16" height="500px">
