import ComposeApp
import SwiftUI

@main
struct iOSApp: App {
    init() {
        SetupSentryKt.initializeSentry()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
