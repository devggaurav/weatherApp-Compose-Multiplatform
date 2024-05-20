import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
       
       let mainVC = MainViewControllerKt.MainViewController()
        return mainVC
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        
        print("I am print")
    }
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



