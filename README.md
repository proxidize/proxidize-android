# Proxidize Android: Create 5G/4G Mobile Proxy Farms on Android Phones

Proxidize Android Legacy is an Android application that enables anyone to make 4G or 5G mobile proxy farms using their Android phones without the need of anything else. Just download the app, hit connect, and your mobile proxy will be automatically generated.

Proxidize created Proxidize Android as a proof of concept for Proxidize MPM (Mobile Proxy Maker). The app accomplished its purpose, but was eventually taken down from the Google Play Store for reasons mentioned below.

Proxidize Android Legacy is the predecessor of the upcoming Proxidize Portable application which will be a drastic improvement on this app.

Read this page in other languages: [русский](https://github.com/proxidize/proxidize-android/blob/main/README.ru.md)

![Overview](https://i.imgur.com/gsRoRBt.png)

<div align="center"> </br><a href="https://github.com/proxidize/proxidize-android/releases/download/v2.1.4/Proxidize.Android.Legacy.v2.1.4.apk">
    <img src="https://i.imgur.com/HkPj7Fx.png" height="auto"/>
  </a>
</br></br></br></div>

</div>


## What Is Proxidize:

Proxidize is a multi-national effort started by a team of engineers to democratize access to web data & automation. Read the Proxidize manifesto: https://proxidize.com/manifesto/


<div align="center"> <a href="https://proxidize.com/">
    <img src="https://i.imgur.com/3FEWrk5.png" height="auto"/>
  </a>
</div>

<div align="center">
  <h2>Proxidize</h2>
  <a href="#how-to-create-a-5g-or-4g-mobile-proxy-on-android-phones-turn-your-phone-into-a-mobile-proxy">Start Making Mobile Proxies</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="#rotationchanging-the-ip-how-to-change-mobile-proxy-android-ip-address-using-airplane-mode">IP Rotation</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/">Website</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/android">Docs</a>
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

## Features of Proxidize Android Legacy

![image](https://user-images.githubusercontent.com/107770894/190168239-2084da54-9b5a-4ed6-9ab8-3bd21671adf5.png)


- Create A Mobile or Residential HTTP(S) or SOCKS5 proxy on Android, MacOS or Windows devices.
- Rotate/Change IP manually using a button, automatically using a specific rotation interval.
- API for rotating/changing the IP which can be used as a link/URL.
- Connect to mobile data while using the app to generate a mobile proxy.
- Connect to Wi-Fi while using the app to generate a residential proxy.
- Super quick load balancing managed by global servers.
- Add your own custom tunneling server for higher security and speeds.
- Experimental: Change OS fingerprint for improved opsec.
- Experimental: Split connection to WIFI backend to get better speeds.



---

## How It Works and Architecture

Proxidize Android Legacy works by establishing a connection to a tunneling server via reverse proxies and then launching a local HTTP proxy server. This makes the proxy accessible from anywhere on the web, as the tunneling server handles the port forwarding and routing.

<div align="center">
    <img src="https://i.imgur.com/9UAAcx3.png" height="auto"/>
</div>


The application will select a random port between ```10000``` and ```60000```, use it connect to the client and then create a proxy server based on the random port along with a randomly generated username and password.


---

## Table of Contents

- [Proxidize Android - Create 5G/4G Mobile Proxies on Android Phones](#proxidize-android-create-5g4g-mobile-proxy-farms-on-android-phones)
  * [What Is Proxidize?](#what-is-proxidize)
  * [Features of Proxidize Android Legacy](#features-of-proxidize-android-legacy)
  * [How It Works & Architecture](#how-it-works-and-architecture)
  * [Proxidize Android Legacy vs Proxidize Mobile Proxy Maker](#proxidize-android-legacy-vs-proxidize-mobile-proxy-maker)
  * [How to create a 5G/4G mobile proxy on android phones: (Turn your phone into a mobile proxy)](#how-to-create-a-5g-or-4g-mobile-proxy-on-android-phones-turn-your-phone-into-a-mobile-proxy)
    + [How to Use on Windows MacOS (Create 5G or 4G Mobile Proxies on WindowsMacOS)](#rotationchanging-the-ip-how-to-change-mobile-proxy-android-ip-address-using-airplane-mode)
  * [Using the Proxy](#using-the-proxy)
  * [Rotation/Changing the IP (How to Change Mobile Proxy Android IP Address Using Airplane Mode)](#rotation-changing-the-ip--how-to-change-mobile-proxy-android-ip-address-using-airplane-mode)
    + [Automatically Changing the IP Address](#automatically-changing-the-ip-address)
    + [Changing the IP Manually](#changing-the-ip-manually)
    + [Changing the IP via URL/API](#changing-the-ip-via-url-api)
  * [Supported Android Versions & Devices](#supported-android-versions--devices)
  * [Deploying Your Own Server](#deploying-your-own-server)
    + [Example](#example)
  * [Using the App Without Connecting to the Tunneling Server First](#using-the-app-without-connecting-to-the-tunneling-server-first)
  * [Reporting Issues](#reporting-issues)
    + [Types of Issues That You Should Report](#types-of-issues-that-you-should-report)
    + [How to Report the Issue](#how-to-report-the-issue)
    + [Any Issues Unrelated to the App Will Be Closed, Such As](#any-issues-unrelated-to-the-app-will-be-closed--such-as)
  * [Updates](#updates)
  * [FAQ:](#faq)
    + [Why is the app marked as harmful app/malware by Google?](#why-is-the-app-marked-as-harmful-appmalware-by-google)
    + [My proxy isn't working with ```Proxy Refusing Connection``` error?](#my-proxy-isnt-working-with-proxy-refusing-connection-error)
    + [My proxy stopped working after it used to work, can you help?](#my-proxy-stopped-working-after-it-used-to-work-can-you-help)
    + [Why is my proxy slow?](#why-is-my-proxy-slow)
    + [Where will this app work?](#where-will-this-app-work)
    + [I keep getting a ```407 Error``` or the proxy keeps asking for authentication?](#i-keep-getting-a-407-error-or-the-proxy-keeps-asking-for-authentication)
  * [Proxidize Portable](#proxidize-portable)


---

## Proxidize Android Legacy vs Proxidize Mobile Proxy Maker

This app is not a replacement for Proxidize Mobile Proxy Maker, but a proof of concept. You can use this app at a small scale for personal projects, but once you need a commercial-grade solution, you'll need Proxidize MPM for the following reasons:

- Such apps will always be naturally unreliable due to underlying infrastructure being designed mainly for IoT devices and not proxies.
- Low speeds. Since both incoming and outgoing connections are passing through the same network interface, the speed you get will be a fifth of the mobile speed.
- Difficult to manage at scale. It takes a 10 minutes to set up a 20-modem kit from Proxidize, but setting up 20 phones will take a full day if not more.

---

## How to Create a 5G or 4G Mobile Proxy on Android Phones: (Turn Your Phone Into a Mobile Proxy)

<div align="center">
    <img src="https://i.imgur.com/ASSDAe2.png" height="auto"/>
</div>


- Download Proxidize Android Legacy APK File
- Install the APK on your device
- Open the app and press "Connect".
- Copy the proxy and you can use it anywhere.

And now you have created your very own 5G/4G mobile proxy!

### How to Use on Windows MacOS (Create 5G or 4G Mobile Proxies on WindowsMacOS)

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
## Rotation/Changing the IP (How to Change Mobile Proxy Android IP Address Using Airplane Mode)

Proxidize Android Legacy has built-in rotation. To set it up, you need to set the app as the default assistant in your settings.


### Automatically Changing the IP Address:

<div align="center">
    <img src="https://i.gyazo.com/8c923c64c2996452c78993003e023d94.png" height="auto"/>
</div>

Proxidize Android Legacy allows you to set a rotation/IP change interval. To use it, you need to:
- Press "AUTO CHANGE IP" button on the home page.
- Select the rotation interval you wish to use.
- Select a time in minutes. Anything less than 30 minutes will harm your phone.
- Press "SET" and your settings will be applied.



### Changing the IP Manually:

<div align="center">
    <img src="https://i.gyazo.com/ad6fa87d163262908253e99cf2f436ab.png" height="auto"/>
</div>


To change the IP address manually, you simple need to press the change IP button.



### Changing the IP via URL/API:

<div align="center">
    <img src="https://i.gyazo.com/ded5e4abaf6f19ca75ff5045889dced4.png" height="auto"/>
</div>

Proxidize Android Legacy generates an IP change link/URL/API that you can use anywhere to change the IP.

To change the IP using the rotation link, you need to:
- Copy the change IP URL under "IP Change Link/API" by pressing the "COPY" button.
- Use the link anywhere or send a GET request to it.

A success response is:

```{"response":"success"}```


---
## Supported Android Versions & Devices

Proxidize Android Legacy supports all ```armeabi-v7a``` running ```Android 6.0``` to ```Android 12```

Supported Android API from ```API 23``` to ```API 31```.

Tested devices:

```
All Android 6.0+ phones
Samsung A Series
Samsung S Series
Samsung M Series
Samsung Note Series
Google Pixel
OnePlus
```
---

## Deploying Your Own Server

<div align="center">
    <img src="https://i.gyazo.com/cc24b11d87379469f06ac8f15257dcbe.png" height="auto"/>
</div>

Proxidize Android Legacy allows you to deploy your own tunneling server to avoid using shared/congested servers. To do that, you need to:
- Create a new server on any host. Make sure you're  on a public network with all the ports publicly accessible.
- Edit configuration file to add your server information.
- Edit ```CUSTOM SERVER``` fields to add your new server.

### Example:

- Server IP = ```5.5.5.5```

- Make sure the server is ```x86-64``` or ```AMD64``` running ```Ubuntu 20.04```

- SSH into your server

``` ssh username@5.5.5.5```

- Clone this repo

``` git clone https://github.com/proxidize/proxidize-android.git  ```

- Enter the repo directory

``` cd ./proxidize-android  ```

- Edit the server.ini file to add an authentication token

``` vi``` or ```nano ./server/server.ini ```

- Add the following info, replacing ```PORT``` and ```TOKEN``` with your own values. Keep the port value as ```2000``` unless you have a reason to change it.

```
[common]
bind_port = PORT
authentication_method=token
token = TOKEN
```

```TOKEN``` is used to authenticate which clients are allowed to connect to this server. It can be any random set of characters such as ```12345678```.

- Fix the permissions of the folder and files for server
```
sudo chmod -R 755 ./server
```

- Start the server

``` setsid ./server/server -c ./server/server.ini &```

```setsid``` is used to keep the process alive after you close the terminal.

- Add the new server information to your application by going to menu > Change Server > Custom.

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


### Any Issues Unrelated to the App Will Be Closed, Such As::

- I sent 1,000 threads to scrape Amazon and now the IP is banned.
- I'm using vanilla puppeteer or Chrome and I keep getting blocked or my proxy is detected.
- Any form of 407/authentication error. This means you're not using the right credentials. Refer to format section.
- 502 or 504 if you're using rotation. This happens when you're connecting in the middle of a rotation.
- Any situation where you're using your own server. (Unless you can replicate the issue when using the default servers as well.)


---
## Updates:

This app is no longer maintained by Proxidize, but I (Abed) will be working on it in my free time.

Edit: I ended up finishing most of the planned features before schedule. So chances are I won't be making any updates to this app for a while.

Things I'll be adding:

- [x] Supporting Android 12
- [x] Add Android wake lock to keep the proxy alive.
- [x] Custom server from application.
- [x] Save ports between sessions.
- [x] In-app IP rotation button.
- [x] Automated IP rotation.
- [x] IP rotation API
- [x] Supporting more devices such as Asus, Alcatel, etc.
- [x] Auto-detect nearest tunneling server.
- [x] SOCKS proxies.
- [x] Prevent duplicate ports on server.
- [ ] Showing the public IP on the app interface.
- [ ] Change proxy format.

If you're updating to v2.0.0 from v1.0.0 make sure to delete v1.0.0 first.

---


## FAQ:

### Why is the app marked as harmful app/malware by Google?

A few months after publishing the app, Google marked it as harmful/PUP/malware app. I suspect it's because there's some Google watchdog that sniffed the traffic and found something harmful that was being transmitted by some of the users. Or it's possible that the behavior of the traffic being unencrypted and routed to a single server was similar to typical harmful app behavior that come across Google in the Play Store.

There are also a few AVs that have marked the tunneling client fast revers proxy, as a PUP, and it's possible Google Play Store did the same.

I've made some mitigations against this by changing the binaries to change the hash, but I suspect Google will still mark it as harmful by reading the strings, so you will need to disable Play Protect, otherwise, the app will likely get automatically deleted.

### My proxy isn't working with ```Proxy Refusing Connection``` error?

Make sure you're using the correct port value, then exit the app and start it again. There's a very small chance you used an already used port.

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

## Proxidize Portable

We are currently working on a new application called "Proxidize MPM-Cloud Portable" or Proxidize Portable for short. The new app will address all the deficiencies of this one and will have the following features:

1. 5-10x higher speeds than Proxidize Android Legacy
2. Custom OS Fingerprint
3. Send & Receive SMS via interface/API
4. Manage all devices from web interface
5. Manage unlimited phones via grouping, categories and more.
6. Use any server from dozens of countries.
7. Custom DNS
8. Get 99.99% uptime
9. Dual-stacking IPV4/IPV6 support
10. Load-balancing between multiple phones.
11. Setting multi-phone IP rotation/load balancing pools.
12. And much more!

