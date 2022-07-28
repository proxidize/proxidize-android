# Proxidize Android - Create 5G/4G Mobile Proxies on Android Phones

Proxidize Android Legacy is an Android application that enables anyone to make 4G or 5G mobile proxies using their Android phones without the need of anything else. Just download the app, hit connect, and your mobile proxy will be automatically generated.

Proxidize created Proxidize Android as a proof of concept for Proxidize MPM (Mobile Proxy Maker). The app accomplished its purpose, but was eventually taken down from the Google Play Store for reasons mentioned below.

With the rise of Russian & Chinese Proxidize MPM copycat apps, and after seeing the damage these can cause to the community of proxy users with the recent scandal, we decided to open source this app to protect the users from falling prey to any botnet apps. Further read: [Proxidize copycats, a story of greed & international crim syndicates](https://proxidize.com/blog/proxidize-copycats-a-story-of-greed-international-crime-syndicates/)

Proxidize Android Legacy is the predecessor of the upcoming Proxidize Portable application which will solve all the shortcomings of this app.


<div align="center"> </br><a href="https://proxidize.com/">
    <img src="https://i.imgur.com/HkPj7Fx.png" height="auto"/>
  </a>
</br></br></br></div>

<div align="center">

  <a href="#contributing"><img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" /></a>
  <a href="#license"><img src="https://img.shields.io/badge/license-Apache%202-blue" /></a>
</div>


---

## What Is Proxidize?

Proxidize is a multi-national effort started by a team of engineers to democratize access to web data & automation. We are currently in phase #1 which is building the proxy infrastructure required to build the full project.

Read the Proxidize manifesto: https://proxidize.com/manifesto/


<div align="center"> <a href="https://proxidize.com/">
    <img src="https://i.imgur.com/3FEWrk5.png" height="auto"/>
  </a>
</div>

<div align="center">
  <h2>Proxidize</h2>
  <a href="#installing-proxidize-android-legacy">Create Mobile Proxies</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="#rotationchanging-the-ip-how-to-change-mobile-proxy-ip-address-using-airplane-mode">IP Rotation</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/">Website</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/docs">Docs</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/blog">Blog</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://twitter.com/proxidizehq">Twitter</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="#proxidize-portable">Proxidize Portable</a>
  <br />
  <hr />
</div>



---
## Table of Content

- [Proxidize Android - Create 5G/4G Mobile Proxies on Android Phones](#proxidize-android---create-5g4g-mobile-proxies-on-android-phones)
  * [What Is Proxidize?](#what-is-proxidize)
  * [Features of Proxidize Android Legacy](#features-of-proxidize-android-legacy)
  * [How It Works & Architecture](#how-it-works--architecture)
  * [Proxidize Android Legacy vs Proxidize Mobile Proxy Maker](#proxidize-android-legacy-vs-proxidize-mobile-proxy-maker)
  * [Installing Proxidize Android Legacy](#installing-proxidize-android-legacy)
  * [Using the Proxy](#using-the-proxy)
  * [Rotation/Changing the IP (How to Change Mobile Proxy IP Address Using Airplane Mode)](#rotationchanging-the-ip-how-to-change-mobile-proxy-ip-address-using-airplane-mode)
  * [Supported Android Versions & Devices](#supported-android-versions--devices)
  * [Using the App Without Connecting to the Tunneling Server First](#using-the-app-without-connecting-to-the-tunneling-server-first)
  * [Reporting Issues](#reporting-issues)
  * [Contributing](#contributing)
  * [Deploying Your Own Server](#deploying-your-own-server)
  * [FAQ](#faq)
  * [Credits](#credits)
  * [Proxidize Portable](#proxidize-portable)
  * [License](#license)



---

## Features of Proxidize Android Legacy

- Create A Mobile or Residential HTTP proxy on Android, MacOS or Windows devices.
- Connect to mobile data while using the app to generate a mobile proxy.
- Connect to WiFi while using the app to generate a residential proxy.
- Rotate the IP manually or automatically via 3rd parties
- Add custom tunneling servers

---

## How It Works & Architecture

Proxidize Android Legacy works by establishing a connection to a tunneling server via reverse proxies and then launching a local HTTP proxy server. This makes the proxy accessible from anywhere on the web, as the tunneling server handles the port forwarding and routing.

![architecture](https://i.imgur.com/8oxuPpe.png)

The application will select a random port between ```10000``` and ```60000```, use it connect to the client and then create a proxy server based on the random port along with a randomly generated username and password.


---
## Proxidize Android Legacy vs Proxidize Mobile Proxy Maker

This app is not a replacement for Proxidize Mobile Proxy Maker, but a proof of concept. You can use this app at a small scale for personal projects, but once you need a commercial-grade solution, you'll need Proxidize MPM for the following reasons:

- Such apps will always be naturally unreliable due to underlying infrastructure being deisgned mainly for IoT devices and not proxies.
- Low speeds. Since both incoming and outgoing connections are passing through the same network interface, the speed you get will be a fifth of the mobile speed.
- Difficult to manage at scale. It takes a 10 minutes to set up a 20-modem kit from Proxidize, but setting up 20 phones will take a full day if not more.

---

## Installing Proxidize Android Legacy

![example](https://i.imgur.com/SOkvQjQ.jpg)

### How to use on Android: (Create 5G or 4G Mobile Proxies on Android)

- Download Proxidize Android Legacy APK File
- Install the APK on your device
- Open the app and press "Connect".
- Copy the proxy and you can use it anywhere.


### How to Use on Windows MacOS (Create 5G or 4G Mobile Proxies on Windows)

- Download any Android emulator such as BlueStacks
- Download Proxidize Android Legacy APK file inside the emulator (Open this page from the emulator and download the APK)
- Install the APK on your device
- Open the app and press "Connect".
- Copy the proxy and you can use it anywhere.

---

## Using the Proxy

Format:
```
IP:Port:Username:Password
```

Example:
```
1.1.1.1:1565:abc:xyz
```

Explained:
```
IP or Hostanme: 1.1.1.1
Port: 1565
Userrname: abc
Password: xyz
```
---
## Rotation/Changing the IP (How to Change Mobile Proxy IP Address Using Airplane Mode)

You can rotate the IP by having any macro app automatically toggle airplane mode on and off.

### Automatically Changing the IP Address:

![macrodroid](https://i.imgur.com/osaP2c6.png)

To automatically change the IP address, you can use a macros app such as MacroDroid which we'll use here.

-  Download MacroDroid from the Google Play Store https://play.google.com/store/apps/details?id=com.arlosoft.macrodroid
- Create a new macro and name it "Proxidize Android IP Change"
- Go to Settings > Assistant Settings > Default assistant app > Set "MacroDroid" to be the default assistant app
- Add trigger "Regular Interval" and set it to whatever interval you want the IP to change over.
- Add Actions:
	- Toggle airplane mode on
	- Wait 3 seconds
	- Toggle airplane mode off
- Save the macro (edited) 

And now your IP will automatically change every set amount of time as you specified in the interval.


### Changing the IP Manually:

- Make sure you are connected to mobile data
- Toggle airplane mode on and off
- You will now have a new public IP address

---
## Supported Android Versions & Devices

Proxidize Android Legacy supports all ```armeabi-v7a``` running ```Android 6.0``` to ```Android 12```

Supported Android API from ```API 23``` to ```API 31```.

Tested devices:
```
Samsung A Series
Samsung S Series
Samsung M Series
Samsung Note Series
Google Pixel
OnePlus
```
---

## Deploying Your Own Server

- Create a new server on any host. Make sure you're  on a public network with all the ports publicly accessible.
- Edit configuration file to add your server information.
- Edit ```CUSTOM SERVER``` button fields to add your new server.

### Example:

- Server IP = ```5.5.5.5```

- Make sure the server is ```x86-64``` or ```AMD64``` running ```Ubuntu 20.04```

- SSH into your server

``` ssh username@5.5.5.5```

- Clone this repo

``` git clone THISREPO.git ```

- Edit the server.ini file to add an authentication token

``` vi``` or ```nano ./server.ini ```

- Add the following info, replacing ```PORT``` and ```TOKEN``` with your own values. Keep the port value as ```2000``` unless you have a reason to change it.

```
[common]
bind_port = PORT
authentication_method=token
token = TOKEN
```

```TOKEN``` is used to authenticate which clients are allowed to connect to this server. It can be any random set of characters such as ```12345678```.

- Start the server

``` setsid ./server -c ./server.ini &```

```setsid``` is used to keep the process alive after you close the terminal.

- Add the new server information to your application by using the "CUSTOM SERVER" button.

```HOST``` = The new server public IP address. In this example it's ```5.5.5.5```.

```Binding Port``` = The port your selected.

```Token``` = The token you selected.

- Save the details, exit the app and open it again and hit connect. You will now connect to your new tunneling server.


---
## Using the App Without Connecting to the Tunneling Server First

In some cases, you might be able to connect directly to the phone without needing to connect to the tunneling server. The advantage of this is that you won't have to connect to the tunneling server first, which will offer 5-10% higher speeds.

- Make sure your carrier can give you a dedicated v4 IP. This is very rare and you will need to confirm with the carrier.
- Call your carrier and request they forward the ports for you.
- Get your public IP address by searching for what's my IP address.
- The app is listening on 0.0.0.0 so once you forward the proxy port, just connect to it using your public IP.
- You can also do that if you're connected to WiFi, but you'll need to forward the ports on your router.


---

## Reporting Issues

### Types of Issues That You Should Report:

- App keeps crashing on a specific device/Android version.
- Bypass battery optimization not working on a specific device/Android version.
- App stops working after a while on any
- Proxy Refusing Connection web error, even though the port and host are correct.
- If you see any of the following errors: 12020, 12033, or 12165.

### How to Report the Issue:

- Full description of the issue including screenshots and any error codes
- Full device manufacturer & model name. E.g. Samsung SM-A105L
- Include a screenshot of the "Software information" page
- Full instructions to replicate the issue.


### Any issues unrelated to the app will be closed, such as:

- I sent 1,000 threads to scrape Amazon and now the IP is banned.
- I'm using vanilla puppeteer or Chrome and I keep getting blocked or my proxy is detected.
- Any form of 407/authentication error. This means you're not using the right credentials. Refer to format section.
- 502 or 504 if you're using rotation. This happens when you're connecting in the middle of a rotation.
- Any situation where you're using your own server. (Unless you can replicate the issue when using the default server as well.)


---
## Contributing

This app is no longer maintained by Proxidize, but I (Abed) will be working on it in my free time.

Things I'll be adding:

- [x] Supporting Android 12
- [x] Add Android wake lock to keep the proxy alive if the screen if off.
- [x] Custom server from application.
- [ ] Prevent duplicate ports on server.
- [ ] Supporting more devices such as Asus, Alcatel, etc.
- [ ] Showing the public IP on the app interface.
- [ ] Change proxy format.
- [ ] IP authentication via ACLs.
- [ ] Traffic counters.
- [ ] SOCKS proxies.

If you want to add a new feature, please create an issue first to describe the new feature, as well as the implementation approach. Once a proposal is accepted, create an implementation of the new features and submit it as a pull request. The Go binaries are compiled with ```gomobile``` using FRP.

---


## FAQ:

### Why is the app marked as harmful app/malware by Google?

A few months after publishing the app, Google marked it as harmful/PUP/malware app. I suspect it's because there's some Google watchdog that sniffed the traffic and found something harmful that was being transmitted by some of the users. Or it's possible that the behavior of the traffic being unencrypted and routed to a single server was similar to typical harmful app behavior that come across Google in the Play Store.

There are also a few AVs that have marked the tunneling client, frp, as a PUP, and it's possible Google Play Store did the same.

I've made some mitigations against this by changing the binaries to change the hash, but I suspect Google will still mark it as harmful by reading the strings, so you will need to disable Play Protect, otherwise, it'll get automatically deleted.

### My proxy isn't working with ```Proxy Refusing Connection``` error?

Please exit the app and start it again. There's a very small chance you used an already used port.

### My proxy stopped working after it used to work, can you help?

Exit the app and start it again. If it still doesn't work, make sure the app is still running on your device. Then please tweet [@Proxidizehq](https://twitter.com/proxidizehq) and we'll take a look.

### Why is my proxy slow?

The app uses reverse proxies created via websockets route to forwarding facing proxies. This technology is slow, unreliable and there's nothing I can do about it with the limited time that I have to work on this project. Apps based on this specific tunneling technology were created for simple IoT use cases, and not for pushing full bandwidth or proxies.

The Proxidize team is working on an entirely new app called **Proxidize Portable**, which will address all the short comings of this app using proprietary technology.

Another thing is such apps send both incoming and outgoing traffic from the same device, which means you will always get half the speed that you would normally get when testing the speed directly on your phone. If speed is important, you should use the full Proxidize MPM-OP: https://proxidize.com/

### Where will this app work?

This app will work everywhere, unless:
- You are in countries that have ISP-level firewalls that block any proxied connections via DPI.
- You are behind a corporate firewall that blocks unknown ports.

### I keep getting a ```407 Error``` or the proxy keeps asking for authentication?

Make sure you're not mixing the small ```l``` with a capital ```I```.

---

## Credits

Proxidize Android Legacy Credits
- Unni from the Proxidize team for creating the Android tunneling client
- Muhammad from the Proxidize team for the interface & battery optimization
- Frp Tunneling Server
- The Squid Foundation

---

## Proxidize Portable

We are currently working on a new application called "Proxidize MPM-Cloud Portable" or Proxidize Portable for short. The new app will address all the deficiencies of this one and will have the following features:

1. 5-10x higher speeds than Proxidize Android Legacy
2. HTTP or SOCKS proxies
3. Custom OS Fingerprint
4. Send & Receive SMS via interface/API
5. Manage all devices from web interface
6. Rotate IP manually, every X interval, or via API link.
7. Manage unlimited phones via grouping, categories and more.
8. Use any server from dozens of countries.
9. Custom DNS
10. Get 99.99% uptime
11. Dual-stacking IPV6/IPV6 support
12. Load-balancing between multiple phones.
13. Setting multi-phone IP rotation pools.
14. And more. Feel free to request anything else.

---

## License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
