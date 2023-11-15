# Test Plan: Deposit with Venmo (iOS and Android)
#### Pala Interactive - Internal documentation
#### author: Dan Shields
#### created: 2022-09-29

# Preparing test devices

Paypal provides a test app 'venmoPaySandbox' which is required to be installed on all test devices.  This allows transactions to be automatically approved without charging real money and allows the simulation of failed transactions.   The installation and configuration of the venmoPaySandbox is below.

# Test Device Setup

**Pre-install requirement**:  Ensure you do not have any Venmo apps (such as the production Venmo app) installed on your test device.

## Download Venmo Sandbox (a.k.a venmoPaySandbox)

To request authorization to access Venmo's appcenter, see the Pala-Venmo Teams chat: [teams chat link](https://teams.microsoft.com/l/channel/19%3a3fusk_IUPw3ZReehLvi3RMBIjIEhArJpEXRhWAnNcuA1%40thread.tacv2/General?groupId=9cd2470f-f119-4f0d-94d0-32ab554f80a9&tenantId=dcb393d3-29ce-4405-8cd6-8f960ecafebd)
  
- Once authorized, download Venmo Sandbox from the links below:
  - Android: [https://install.appcenter.ms/orgs/venmo-android/apps/Android-Venmo-Sandbox/releases](https://install.appcenter.ms/orgs/venmo-android/apps/Android-Venmo-Sandbox/releases)

    required minimum version: ```Version 9.29.0-internal-venmoPaySandbox (3400)```

  - iOS: [https://install.appcenter.ms/orgs/venmo-ios/apps/Venmo-Alpha-Pay-with-Venmo/releases](https://install.appcenter.ms/orgs/venmo-ios/apps/Venmo-Alpha-Pay-with-Venmo/releases)

    required minimum version: ```Version 9.29.0 (3594)```

  ### Additional install instructions for iOS:

  For iOS, after the app is installed the Venmo Inc. certificate needs to be trusted for access from the test device.   So additional steps in the iOS settings are required:

  - Open Settings -> General -> VPN & Device Management
  - Select 'Venmo Inc.'
  - Select 'Trust "Venmo Inc."'
  
The app will appear on your device as the 'Pay With Venmo' app.   This app is intended to simulate the production Venmo app in a sandboxed environment for testing purposes only. 

## Venmo Sandbox Configuration Steps:
- Open the 'Pay with Venmo' app.    A 'bug' icon should be visible on one of the screen edges.
- Click the 'bug' icon.
- Under the 'Channel' settings, select 'Choose Channel', then select 'Mobile Web'.
- Close the 'Pay with Venmo' app.

# Test Execution Steps
- Launch the build of Unibet-PA you are testing
- On the environment selector screen select Test environment.
- Log into a test account
- From the main lobby click 'DEPOSIT'
- Scroll down, under 'Select your Payment Method', select 'Venmo'
- Scroll down again, under 'Venmo' sesion, choose any amount to deposit and click 'Deposit'
- Page will refresh to 'Switching to the Venmo App' with a 'Launch Venmo App' button.
- Click 'Launch Venmo App'.
- The Venmo app may ask you to select a bank account to use as backup for the transaction, if so, select any bank account displayed.
- Click 'Agree'.
- The Venmo app switches back to our lobby deposit screen momentarily while the transaction is processed.  
- A successful deposit should display the screen 'Payment Successful' within a few seconds.
