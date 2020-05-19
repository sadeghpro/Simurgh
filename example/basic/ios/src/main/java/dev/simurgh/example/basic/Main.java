package dev.simurgh.example.basic;

import dev.simurgh.core.ios.SimurghViewController;
import dev.simurgh.example.basic.core.ControllerMain;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.*;

public class Main extends UIApplicationDelegateAdapter {


    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {

        SimurghViewController viewController = new SimurghViewController(new ControllerMain());
        UIWindow window = new UIWindow(UIScreen.getMainScreen().getBounds());
        window.setRootViewController(viewController);
        window.makeKeyAndVisible();

        return true;
    }

    public static void main(String[] args) {
        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            UIApplication.main(args, null, Main.class);
        }
    }
}
