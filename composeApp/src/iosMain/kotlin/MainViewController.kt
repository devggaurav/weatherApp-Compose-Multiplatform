import androidx.compose.ui.window.ComposeUIViewController
import util.KoinInitializer

fun MainViewController() = ComposeUIViewController (
    configure = {
        KoinInitializer().initialize()
    }
){ App() }