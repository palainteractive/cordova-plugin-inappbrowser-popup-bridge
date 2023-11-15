# cordova-plugin-inappbrowser-popup-bridge

Looking for Venmo testing instructions?    [Look here](./VENMO-TESTPLAN.md)

This is a fork of [cordova-plugin-inappbrowser](https://github.com/apache/cordova-plugin-inappbrowser) which adds support for Braintree's PopupBridge libraries for [Android](https://github.com/braintree/popup-bridge-android) and [iOS](https://github.com/braintree/popup-bridge-ios) to support PayPal payments within the context of the InappBrowser element of a Cordova-based app.

The purpose of PopupBridge is to allow Webviews to open emulated popup windows in a browser and send data back to the parent page in the Webview. This is essential for Web-based PayPal checkout flows which use the [Braintree JS SDK](https://github.com/braintree/braintree-web) which supports popup emulation via PopupBridge.

See the [example app project](https://github.com/dpa99c/cordova-plugin-inappbrowser-popup-bridge-test) which demonstrates usage of `cordova-plugin-inappbrowser-popup-bridge`.

## iOS 16.4 and inspectable webviews:

As of iOS 16.4 Apple has decided that webviews should no longer be inspectable by default, which makes debugging a pain.   So we have addressed this issue, by reversing the default to the inspectable=YES setting, in the 'inspectable' branch of this project.   'master' will continue for now to have the normal defaults.

To get inspectable webviews with this plugin use this branch: `https://github.com/palainteractive/cordova-plugin-inappbrowser-popup-bridge#inspectable`

## Installation

    cordova plugin add cordova-plugin-inappbrowser-popup-bridge
    
iOS:
- PopupBridge requires [WKWebView](https://developer.apple.com/documentation/webkit/wkwebview).
- Therefore, this plugin depends on [cordova-plugin-wkwebview-engine](https://github.com/apache/cordova-plugin-wkwebview-engine) which adds WKWebView support to Cordova.
    - You need to add this manually: `cordova plugin add cordova-plugin-wkwebview-engine`
    - Or you can use the Ionic variant: `cordova plugin add cordova-plugin-ionic-webview`
- This means the main Cordova app Webview will also use WKWebView on iOS 9+.
    
# Supported platform versions
The following supported platform versions are based on the versions suppored by the Braintree PopupBridge libraries.

## Android
- Requires `cordova-android@10+`
- Requires Gradle v7.1.1 or above
- Supports Android 5.0 / API 21 and above
- Since v5, requires the Cordova activity to run as `singleTask`
    - Add `<preference name="AndroidLaunchMode" value="singleTask" />` to `<platform name="android">` in `config.xml`

## iOS
- Requires `cordova-ios@6+`
- Supports iOS 9.0+

### iOS Quirks

Since the introduction of iPadOS 13, iPads try to adapt their content mode / user agent for the optimal browsing experience. This may result in iPads having their user agent set to Macintosh, making it hard to detect them as mobile devices using user agent string sniffing. You can change this with the `PreferredContentMode` preference in `config.xml`.
    
# Example app
[cordova-plugin-inappbrowser-popup-bridge-test](https://github.com/dpa99c/cordova-plugin-inappbrowser-popup-bridge-test) contains a Cordova project which builds a test app for Android and iOS to illustrate usage of this plugin.
