# RN-Zendesk

A simple React Native wrapper around the Zendesk SDK for quick and painless customer support

## What it is

- A simple wrapper to launch basic zendesk support in React Native

## What it is not

- A point-to-point mapping to the Zendesk SDK

## Minimum Requirements

- iOS 11
- Android API 16

## Installation

1. Add the package via yarn or npm

```
yarn add rn-zendesk
npm install --save rn-zendesk
```

2. Linking

- On RN >= 60, autolink should take care of everything
- On RN < v60, run the manual linking via
  ```
  react-native link rn-zendesk
  ```

3. For iOS, run `pod install` in the `ios` folder to install the native dependencies

## Getting Started

### Initialize Zendesk on App Start

```
import * as RNZendesk from "rn-zendesk";

...

useEffect(() => {
  RNZendesk.initialize({
    appId: ZENDESK_APP_ID,
    zendeskUrl: ZENDESK_URL,
    clientId: ZENDESK_CLIENT_ID
  });
}, [])
```

### Set the Identify of the User

```
import * as RNZendesk from "rn-zendesk";

RNZendesk.identifyAnonymous(user.name, user.email);
```

### Launch the Helpcenter

```
import * as RNZendesk from "rn-zendesk";

RNZendesk.showHelpCenter({
  subject: "Title for any new ticket created by the user inside helpcenter",
  tags: ["tag1", "tag2", "tag3"]
});
```
